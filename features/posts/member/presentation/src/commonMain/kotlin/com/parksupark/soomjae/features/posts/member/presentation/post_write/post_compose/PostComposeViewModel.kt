package com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.image.domain.ImageRepository
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import com.parksupark.soomjae.features.posts.member.presentation.post_write.models.PhotoUploadItem
import com.parksupark.soomjae.features.posts.member.presentation.post_write.models.toPhotoUploadItem
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

private const val TAG = "PostComposeViewModel"

@OptIn(ExperimentalCoroutinesApi::class)
class PostComposeViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val imageRepository: ImageRepository,
    private val postRepository: MemberPostRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<PostComposeState> =
        MutableStateFlow(PostComposeState())
    val stateFlow: StateFlow<PostComposeState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<PostComposeEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        val photosFlow = stateFlow.map { it.photos }.distinctUntilChanged()
        val submittingFlow = stateFlow.map { it.isSubmitting }.distinctUntilChanged()

        combine(photosFlow, submittingFlow) { photos, isSubmitting ->
            canSubmit(photos, isSubmitting)
        }
            .distinctUntilChanged()
            .onEach { canSubmit ->
                _stateFlow.update { it.copy(canSubmit = canSubmit) }
            }
            .launchIn(viewModelScope)
    }

    fun uploadImages(platformFiles: List<PlatformFile>) {
        viewModelScope.launch(dispatcher.io) {
            val items =
                platformFiles.map { async { it.toPhotoUploadItem() } }.awaitAll().toImmutableList()
            _stateFlow.update { it.copy(photos = items) }

            supervisorScope {
                items.forEach { photo ->
                    launch {
                        imageRepository.uploadWithProgress(photo.localImage).collect { progress ->
                            _stateFlow.update { state ->
                                state.copy(
                                    photos = state.photos.map {
                                        if (it.localImage.id == photo.localImage.id) {
                                            it.copy(uploadProgress = progress)
                                        } else {
                                            it
                                        }
                                    }.toImmutableList(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun submitPost() {
        val state = stateFlow.value
        if (!state.canSubmit) return
        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update { it.copy(isSubmitting = true) }

            val imageUrls = state.photos.mapNotNull { photo ->
                val progress = photo.uploadProgress
                if (progress is UploadProgress.Success) {
                    progress.result.url
                } else {
                    Logger.e(TAG) { "Image upload not completed for image: ${photo.localImage}" }
                    null
                }
            }

            postRepository.createPost(
                content = state.inputContent.text.trim().toString(),
                imageUrls = imageUrls,
            ).fold(
                ifLeft = { failure ->
                    Logger.e(TAG) { "Post upload failed: $failure" }
                    _stateFlow.update { it.copy(isSubmitting = false) }
                },
                ifRight = {
                    _stateFlow.update { it.copy(isSubmitting = false) }
                    eventChannel.send(PostComposeEvent.OnPostUploadSuccess)
                },
            )
        }
    }

    private fun canSubmit(
        photos: List<PhotoUploadItem>,
        isSubmitting: Boolean,
    ): Boolean {
        val allUploaded = photos.all { it.uploadProgress is UploadProgress.Success }
        return allUploaded && !isSubmitting
    }
}
