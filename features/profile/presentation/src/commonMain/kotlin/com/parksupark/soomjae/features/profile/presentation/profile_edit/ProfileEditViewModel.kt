package com.parksupark.soomjae.features.profile.presentation.profile_edit

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.common.utils.fold
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.image.domain.ImageRepository
import com.parksupark.soomjae.core.image.domain.models.UploadProgress.Success
import com.parksupark.soomjae.core.image.presentation.model.PhotoUploadItem
import com.parksupark.soomjae.core.image.presentation.model.toPhotoUploadItem
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.mapTextFieldState
import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult
import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult.Invalid.Reason.Unknown
import com.parksupark.soomjae.features.profile.domain.model.Profile
import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository
import com.parksupark.soomjae.features.profile.domain.usecase.ValidateNicknameUseCase
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileDestination
import io.github.vinceglb.filekit.PlatformFile
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val logger: SjLogger,
    private val dispatcher: SoomjaeDispatcher,
    private val profileRepository: ProfileRepository,
    private val imageRepository: ImageRepository,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
) : ViewModel() {
    private val memberId: Long = savedStateHandle.toRoute<ProfileDestination.Edit>().memberId

    private val eventChannel = Channel<ProfileEditEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _stateFlow: MutableStateFlow<ProfileEditState> =
        MutableStateFlow(ProfileEditState())
    val stateFlow: StateFlow<ProfileEditState> = _stateFlow.asStateFlow()

    init {
        getProfile()
        observeNicknameInput()
    }

    private fun getProfile() {
        viewModelScope.launch(dispatcher.io) {
            profileRepository.getProfile(memberId).fold(
                ifLeft = {
                    logger.error(TAG, "Failed to load profile for memberId: $memberId")
                    eventChannel.send(ProfileEditEvent.GetProfileFailed(it.asUiText()))
                },
                ifRight = {
                    _stateFlow.update { state ->
                        state.copy(
                            isProfileLoaded = true,
                            inputNickname = state.inputNickname.apply {
                                setTextAndPlaceCursorAtEnd(it.nickname)
                            },
                            nicknameValidationResult = NicknameValidationResult.Valid,
                            inputBio = state.inputBio.apply {
                                setTextAndPlaceCursorAtEnd(it.bio)
                            },
                            profileImageUrl = it.profileImageUrl,
                            originalNickname = it.nickname,
                            originalBio = it.bio,
                            originalProfileImageUrl = it.profileImageUrl,
                        )
                    }
                },
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeNicknameInput() {
        viewModelScope.launch {
            _stateFlow.mapTextFieldState { it.inputNickname }
                .distinctUntilChanged()
                .debounce(1.seconds)
                .collectLatest { nickname ->
                    _stateFlow.update { state ->
                        state.copy(
                            nicknameValidationResult = NicknameValidationResult.Validating,
                        )
                    }
                    validateNicknameUseCase(nickname.trim().toString()).fold(
                        ifLeft = { failure ->
                            logger.error(TAG, "Failed to validate nickname: $nickname")
                            eventChannel.send(ProfileEditEvent.ShowError(failure.asUiText()))
                            _stateFlow.update { state ->
                                state.copy(
                                    nicknameValidationResult =
                                        NicknameValidationResult.Invalid(Unknown),
                                )
                            }
                        },
                        ifRight = { validationResult ->
                            _stateFlow.update { state ->
                                state.copy(nicknameValidationResult = validationResult)
                            }
                        },
                    )
                }
        }
    }

    fun uploadProfileImage(file: PlatformFile) {
        viewModelScope.launch {
            val photoUploadItem = with(dispatcher.io) { file.toPhotoUploadItem() }

            performImageUpload(photoUploadItem)
        }
    }

    fun retryProfileImageUpload() {
        val photoUploadItem = _stateFlow.value.photoUploadItem ?: return
        viewModelScope.launch {
            performImageUpload(photoUploadItem)
        }
    }

    private suspend fun performImageUpload(item: PhotoUploadItem) {
        imageRepository.uploadWithProgress(item.localImage)
            .flowOn(dispatcher.io)
            .collectLatest { progress ->
                _stateFlow.update { state ->
                    val newUrl = (progress as? Success)?.result?.url

                    state.copy(
                        photoUploadItem = state.photoUploadItem?.copy(uploadProgress = progress),
                        profileImageUrl = newUrl ?: state.profileImageUrl,
                    )
                }
            }
    }

    fun resetProfileImage() {
        _stateFlow.update { state ->
            state.copy(
                profileImageUrl = state.originalProfileImageUrl,
                photoUploadItem = null,
            )
        }
    }

    fun submitProfile() {
        val state = _stateFlow.value
        if (!state.canSubmit) return

        viewModelScope.launch {
            _stateFlow.update { state -> state.copy(isSubmitting = true) }
            val result = withContext(dispatcher.io) {
                profileRepository.updateProfile(
                    newProfile = Profile(
                        memberId = memberId,
                        nickname = _stateFlow.value.inputNickname.text.trim().toString(),
                        bio = _stateFlow.value.inputBio.text.trim().toString(),
                        profileImageUrl = _stateFlow.value.profileImageUrl,
                    ),
                )
            }

            result.fold(
                ifLeft = { failure ->
                    eventChannel.send(ProfileEditEvent.ShowError(failure.asUiText()))
                },
                ifRight = {
                    eventChannel.send(ProfileEditEvent.SubmitSuccess)
                },
                finally = {
                    _stateFlow.update { state -> state.copy(isSubmitting = false) }
                },
            )
        }
    }
}

private const val TAG = "ProfileEditViewModel"
