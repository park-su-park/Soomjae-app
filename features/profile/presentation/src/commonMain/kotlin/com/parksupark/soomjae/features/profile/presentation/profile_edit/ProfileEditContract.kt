package com.parksupark.soomjae.features.profile.presentation.profile_edit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult
import com.parksupark.soomjae.features.profile.domain.model.isValid

@Stable
data class ProfileEditState(
    val isProfileLoaded: Boolean = false,

    val inputNickname: TextFieldState = TextFieldState(),
    val nicknameValidationResult: NicknameValidationResult = NicknameValidationResult.Idle,
    val inputBio: TextFieldState = TextFieldState(),
    val profileImageUrl: String? = null,
    val isSubmitting: Boolean = false,

    val originalNickname: String = "",
    val originalBio: String = "",
    val originalProfileImageUrl: String? = null,
) {
    val isNicknameChanged: Boolean
        get() = inputNickname.text.trim() != originalNickname

    val isBioChanged: Boolean
        get() = inputBio.text.trim() != originalBio

    val isProfileImageChanged: Boolean
        get() = profileImageUrl != originalProfileImageUrl

    val canSubmit: Boolean
        get() {
            val isChanged = isNicknameChanged || isBioChanged || isProfileImageChanged
            return isChanged &&
                nicknameValidationResult.isValid &&
                !isSubmitting
        }
}

sealed interface ProfileEditAction {
    data object OnClick : ProfileEditAction

    data object OnSubmitClick : ProfileEditAction
}

sealed interface ProfileEditEvent {
    data object SubmitSuccess : ProfileEditEvent

    data class GetProfileFailed(val message: UiText) : ProfileEditEvent

    data class ShowError(val message: UiText) : ProfileEditEvent
}
