package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.runtime.Stable
import com.mohamedrejeb.richeditor.model.RichTextState
import com.parksupark.soomjae.core.image.presentation.model.PhotoUploadItem
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost
import io.github.vinceglb.filekit.PlatformFile

@Stable
data class IntroductionEditState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,

    val richTextState: RichTextState = RichTextState(),
    val original: IntroductionPost? = null,

    val imageUploads: List<PhotoUploadItem> = emptyList(),
)

sealed interface IntroductionEditActions {
    data object OnBackClick : IntroductionEditActions

    data object OnSaveClick : IntroductionEditActions

    data class OnImageSelected(val file: PlatformFile) : IntroductionEditActions
}

sealed interface IntroductionEditEvents {
    data object SaveIntroductionSuccess : IntroductionEditEvents

    data class Error(val message: UiText) : IntroductionEditEvents
}
