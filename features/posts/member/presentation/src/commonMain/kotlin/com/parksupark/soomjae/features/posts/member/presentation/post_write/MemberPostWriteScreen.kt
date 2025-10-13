package com.parksupark.soomjae.features.posts.member.presentation.post_write

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker.PhotoPickerScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MemberPostWriteScreen(
    state: MemberPostWriteState,
    onAction: (MemberPostWriteAction) -> Unit,
) {
    when (state.postWriteState.currentScreen) {
        MemberPostWriteScreenType.PHOTO_PICKER -> {
            PhotoPickerScreen(
                state = state.photoPickerState,
                onAction = onAction,
            )
        }

        MemberPostWriteScreenType.POST_COMPOSE -> {
            // TODO: Implement other screen types like PostContent, PostPreview, etc.
        }
    }
}

@Composable
@Preview(name = "MemberPostWrite")
private fun MemberPostWriteScreenPreview() {
    MemberPostWriteScreen(
        state = MemberPostWriteState(),
        onAction = { },
    )
}
