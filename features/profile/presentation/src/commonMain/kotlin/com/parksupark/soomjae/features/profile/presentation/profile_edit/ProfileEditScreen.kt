package com.parksupark.soomjae.features.profile.presentation.profile_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.image.presentation.components.UploadAwareImage
import com.parksupark.soomjae.core.image.presentation.model.PhotoUploadItem
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeIcon
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeOutlinedTextField
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.ProfileImage
import com.parksupark.soomjae.features.profile.presentation.profile_edit.model.mapper.message
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileEditScreen(
    state: ProfileEditState,
    canSubmit: Boolean,
    onAction: (ProfileEditAction) -> Unit,
) {
    SoomjaeScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ProfileEditTopBar(
                onBackClick = { onAction(ProfileEditAction.OnBackClick) },
                canSubmit = canSubmit,
                onSubmitClick = { onAction(ProfileEditAction.OnSubmitClick) },
            )
        }
    ) { innerPadding ->
        ProfileEditContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp),
            state = state,
            onAction = onAction,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEditTopBar(
    onBackClick: () -> Unit,
    canSubmit: Boolean,
    onSubmitClick: () -> Unit,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(text = "프로필 수정") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                SoomjaeIcon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기",
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSubmitClick,
                enabled = canSubmit
            ) {
                SoomjaeIcon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "저장",
                    tint = if (canSubmit) SoomjaeTheme.colorScheme.primary else SoomjaeTheme.colorScheme.iconDisabled,
                )
            }
        }
    )
}

@Composable
private fun ProfileEditContent(
    state: ProfileEditState,
    onAction: (ProfileEditAction) -> Unit,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileImageSection(
            uploadItem = state.photoUploadItem,
            originalProfileImageUrl = state.originalProfileImageUrl,
            onProfileImageSelect = { onAction(ProfileEditAction.OnProfileImageSelect(it)) },
        )

        NicknameField(
            state = state.inputNickname,
            hint = state.originalNickname,
            error = state.nicknameValidationResult.message,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        BioField(
            state = state.inputBio,
            hint = state.originalBio.ifBlank { "한 줄 소개" },
            error = null,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun ProfileImageSection(
    uploadItem: PhotoUploadItem?,
    originalProfileImageUrl: String?,
    onProfileImageSelect: (PlatformFile) -> Unit,
    size: Dp = 120.dp,
    shape: Shape = RoundedCornerShape(24),
) {
    val imageSelectionLauncher = rememberFilePickerLauncher(
        type = FileKitType.Image
    ) { image ->
        if (image != null) {
            onProfileImageSelect(image)
        }
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .clickable(onClick = { imageSelectionLauncher.launch() }),
        contentAlignment = Alignment.Center,
    ) {
        if (uploadItem != null) {
            UploadAwareImage(
                item = uploadItem,
                contentDescription = "업로드 중인 이미지",
                size = size,
                modifier = Modifier.matchParentSize().clip(shape),
            )
        } else if (originalProfileImageUrl != null) {
            ProfileImage(
                imageUrl = originalProfileImageUrl,
                modifier = Modifier.matchParentSize().clip(shape),
                size = size,
            )
        } else {
            // placeholder
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(SoomjaeTheme.colorScheme.background3),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "이미지 추가",
                    color = SoomjaeTheme.colorScheme.text2,
                    style = SoomjaeTheme.typography.labelL,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun NicknameField(
    state: TextFieldState,
    hint: String,
    error: String?,
    modifier: Modifier = Modifier,
) {
    SoomjaeOutlinedTextField(
        state = state,
        modifier = modifier,
        error = error,
        title = "닉네임",
        hint = hint,
    )
}

@Composable
private fun BioField(
    state: TextFieldState,
    hint: String,
    error: String?,
    modifier: Modifier = Modifier,
) {
    SoomjaeOutlinedTextField(
        state = state,
        modifier = modifier,
        error = error,
        title = "소개",
        hint = hint,
    )
}

@Composable
@Preview(name = "ProfileEdit")
private fun ProfileEditScreenPreview() {
    AppTheme {
        ProfileEditScreen(
            state = ProfileEditState(),
            canSubmit = false,
            onAction = { },
        )
    }
}
