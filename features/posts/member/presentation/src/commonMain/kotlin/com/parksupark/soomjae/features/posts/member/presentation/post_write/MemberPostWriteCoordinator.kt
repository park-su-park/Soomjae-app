package com.parksupark.soomjae.features.posts.member.presentation.post_write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.member.presentation.navigation.MemberNavigator
import com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker.PhotoPickerAction
import com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker.PhotoPickerViewModel
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose.PostComposeAction
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose.PostComposeViewModel
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_write.PostWriteViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn

class MemberPostWriteCoordinator(
    val navigator: MemberNavigator,
    val postWriteViewModel: PostWriteViewModel,
    val photoPickerViewModel: PhotoPickerViewModel,
    val postComposeViewModel: PostComposeViewModel,
) : ViewModel() {
    val screenStateFlow: StateFlow<MemberPostWriteState> = combine(
        postWriteViewModel.stateFlow,
        photoPickerViewModel.stateFlow,
        postComposeViewModel.stateFlow,
    ) { postWriteState, photoPickerState, postComposeState ->
        MemberPostWriteState(
            postWriteState = postWriteState,
            photoPickerState = photoPickerState,
            postComposeState = postComposeState,
        )
    }.stateIn(
        scope = this.viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MemberPostWriteState(),
    )

    val events: Flow<MemberPostWriteEvent> by lazy {
        merge(
            postComposeViewModel.events.map { MemberPostWriteEvent.FromPostComposeEvent(it) },
        )
    }

    fun handle(action: MemberPostWriteAction) {
        when (action) {
            is PhotoPickerAction -> handlePhotoPickerAction(action)
            is PostComposeAction -> handlePostComposeAction(action)
        }
    }

    private fun handlePhotoPickerAction(action: PhotoPickerAction) {
        when (action) {
            PhotoPickerAction.OnBackClick -> navigator.navigateBack()
            PhotoPickerAction.OnNextClick -> {
                val selectedPhotos = photoPickerViewModel.stateFlow.value.photos
                postWriteViewModel.moveToNextScreen()
                postComposeViewModel.uploadImages(selectedPhotos)
            }

            is PhotoPickerAction.OnSelectPhotos -> photoPickerViewModel.updateSelectedPhotos(action.photos)
            is PhotoPickerAction.OnRemovePhoto -> photoPickerViewModel.removePhoto(action.photo)
        }
    }

    private fun handlePostComposeAction(action: PostComposeAction) {
        when (action) {
            PostComposeAction.OnBackClick -> postWriteViewModel.moveToPrevScreen()
            PostComposeAction.OnSubmitClick -> postComposeViewModel.submitPost()
        }
    }
}
