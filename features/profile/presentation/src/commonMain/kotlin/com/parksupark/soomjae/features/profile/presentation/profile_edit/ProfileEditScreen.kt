package com.parksupark.soomjae.features.profile.presentation.profile_edit

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileEditScreen(
    state: ProfileEditState,
    onAction: (ProfileEditAction) -> Unit,
) {
    // TODO UI Rendering
}

@Composable
@Preview(name = "ProfileEdit")
private fun ProfileEditScreenPreview() {
    ProfileEditScreen(
        state = ProfileEditState(),
        onAction = {},
    )
}
