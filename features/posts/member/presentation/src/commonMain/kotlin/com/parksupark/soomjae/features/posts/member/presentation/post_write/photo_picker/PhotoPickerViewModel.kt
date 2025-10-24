package com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker

import androidx.lifecycle.ViewModel
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.path
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhotoPickerViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<PhotoPickerState> =
        MutableStateFlow(PhotoPickerState())
    val stateFlow: StateFlow<PhotoPickerState> = _stateFlow.asStateFlow()

    fun updateSelectedPhotos(photos: List<PlatformFile>) {
        _stateFlow.update { state ->
            val merged = (state.photos + photos)
                .distinctBy { it.path }
                .toImmutableList()

            state.copy(photos = merged)
        }
    }

    fun removePhoto(photo: PlatformFile) {
        _stateFlow.update { state ->
            state.copy(photos = state.photos.filterNot { it.path == photo.path }.toImmutableList())
        }
    }
}
