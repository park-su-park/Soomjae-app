package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.common.utils.NanoId
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import com.parksupark.soomjae.core.image.domain.usecase.UploadImageWithProgressUseCase
import com.parksupark.soomjae.core.image.presentation.model.toPhotoUploadItem
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.richtext.insertImageAtCursor
import com.parksupark.soomjae.features.profile.domain.usecase.GetIntroductionUseCase
import com.parksupark.soomjae.features.profile.domain.usecase.SaveIntroductionPostUseCase
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileDestination
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntroductionEditViewModel(
    private val logger: SjLogger,
    private val dispatcher: SoomjaeDispatcher,
    private val soomjaeEventController: SoomjaeEventController,

    private val uploadImageWithProgressUseCase: UploadImageWithProgressUseCase,
    private val getIntroductionUseCase: GetIntroductionUseCase,
    private val saveIntroductionUseCase: SaveIntroductionPostUseCase,

    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val memberId: Long =
        savedStateHandle.toRoute<ProfileDestination.IntroductionEdit>().memberId

    private val _stateFlow: MutableStateFlow<IntroductionEditState> =
        MutableStateFlow(IntroductionEditState())
    val stateFlow: StateFlow<IntroductionEditState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<IntroductionEditEvents>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    init {
        loadIntroduction()
    }

    private fun loadIntroduction() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }

            val result = withContext(dispatcher.io) {
                getIntroductionUseCase(memberId)
            }

            result.fold(
                ifLeft = { failure ->
                    logger.error(TAG, "Failed to load introduction with $failure")
                    eventChannel.send(IntroductionEditEvents.Error(failure.asUiText()))
                    _stateFlow.update {
                        it.copy(
                            isLoading = false,
                            original = null,
                        )
                    }
                },
                ifRight = { introductionPost ->
                    _stateFlow.update { state ->
                        state.copy(
                            isLoading = false,
                            richTextState = introductionPost?.let { post ->
                                state.richTextState.setHtml(post.htmlContent)
                            } ?: state.richTextState.apply { clear() },
                            original = introductionPost,
                        )
                    }
                },
            )
        }
    }

    fun saveIntroduction() {
        val currentState = _stateFlow.value
        if (currentState.isSaving) return

        viewModelScope.launch {
            _stateFlow.update { it.copy(isSaving = true) }

            val htmlContent = currentState.richTextState.toHtml()
            val result = withContext(dispatcher.io) {
                saveIntroductionUseCase(
                    current = _stateFlow.value.original,
                    html = htmlContent,
                )
            }

            result.fold(
                ifLeft = { failure ->
                    if (failure == DataFailure.Validation.UNAUTHORIZED) {
                        soomjaeEventController.sendEvent(SoomjaeEvent.LoginRequest)
                    } else {
                        logger.error(TAG, "Failed to save introduction with $failure")
                        eventChannel.send(IntroductionEditEvents.Error(failure.asUiText()))
                    }
                },
                ifRight = {
                    eventChannel.send(IntroductionEditEvents.SaveIntroductionSuccess)
                },
            )

            _stateFlow.update { it.copy(isSaving = false) }
        }
    }

    fun handleImageSelect(image: PlatformFile) {
        val tempId = NanoId.generate()

        startImageUpload(
            tempId = tempId,
            image = image,
        )
    }

    private fun startImageUpload(
        tempId: String,
        image: PlatformFile,
    ) {
        viewModelScope.launch {
            val imageData = image.toPhotoUploadItem()
            _stateFlow.update { current ->
                current.copy(imageUploads = current.imageUploads + listOf(imageData))
            }

            uploadImageWithProgressUseCase(imageData.localImage)
                .collect { event ->
                    _stateFlow.update { currentState ->
                        currentState.copy(
                            imageUploads = currentState.imageUploads.map { item ->
                                if (item.id == tempId) {
                                    item.copy(uploadProgress = event)
                                } else {
                                    item
                                }
                            },
                        ).also {
                            if (event is UploadProgress.Success) {
                                currentState.apply {
                                    richTextState.insertImageAtCursor(event.result.url)
                                }
                            }
                        }
                    }
                }
        }
    }

    companion object {
        private const val TAG = "IntroductionEditViewMod"
    }
}
