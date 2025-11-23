package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.runtime.Stable
import com.mohamedrejeb.richeditor.model.RichTextState
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost

@Stable
data class IntroductionEditState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,

    val richTextState: RichTextState = RichTextState(),
    val original: IntroductionPost? = null,
)

sealed interface IntroductionEditActions {
    data object OnBackClick : IntroductionEditActions
}

sealed interface IntroductionEditEvents {
    data object SaveIntroductionSuccess : IntroductionEditEvents

    data class Error(val message: UiText) : IntroductionEditEvents
}
