package com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker

import com.parksupark.soomjae.features.posts.member.presentation.post_write.MemberPostWriteAction
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PhotoPickerState(
    val photos: ImmutableList<PlatformFile> = persistentListOf(),
)

sealed interface PhotoPickerAction : MemberPostWriteAction {
    data class OnSelectPhotos(val photos: List<PlatformFile>) : PhotoPickerAction

    data object OnBackClick : PhotoPickerAction

    data object OnNextClick : PhotoPickerAction

    data class OnRemovePhoto(val photo: PlatformFile) : PhotoPickerAction
}
