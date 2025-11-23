package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeIcon
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.valentinilk.shimmer.shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun IntroductionEditScreen(
    state: IntroductionEditState,
    onAction: (IntroductionEditActions) -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            IntroductionPostTopBar(
                onBackClick = { onAction(IntroductionEditActions.OnBackClick) },
                canSave = !state.isLoading && !state.isSaving,
                onSaveClick = { onAction(IntroductionEditActions.OnSaveClick) },
            )
        },
    ) { innerPadding ->
        if (state.isLoading) {
            IntroductionPostLoading(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize(),
            )
        } else {
            IntroductionEditContent(innerPadding)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IntroductionPostTopBar(
    onBackClick: () -> Unit,
    canSave: Boolean,
    onSaveClick: () -> Unit,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    SoomjaeIcon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                },
            )
        },
        actions = {
            IconButton(
                onClick = onSaveClick,
                enabled = canSave,
                content = {
                    SoomjaeIcon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = "Save",
                        tint = if (canSave) {
                            SoomjaeTheme.colorScheme.iconColored
                        } else {
                            SoomjaeTheme.colorScheme.iconDisabled
                        },
                    )
                },
            )
        },
    )
}

@Composable
private fun IntroductionPostLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .shimmer(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(
                    color = SoomjaeTheme.colorScheme.background3,
                    shape = RoundedCornerShape(8.dp),
                ),
        )
        Spacer(modifier = Modifier.height(16.dp))

        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(end = (it + 1) * 40.dp)
                    .background(
                        color = SoomjaeTheme.colorScheme.background3,
                        shape = RoundedCornerShape(8.dp),
                    ),
            )
        }
    }
}

@Composable
private fun IntroductionEditContent(innerPadding: PaddingValues) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(innerPadding),
    ) {
        // TODO Add UI content here
    }
}

@Preview(name = "IntroductionEdit")
@Composable
private fun IntroductionEditScreen_LoadingPreview() {
    AppTheme {
        IntroductionEditScreen(
            state = IntroductionEditState(
                isLoading = true,
            ),
            onAction = { },
        )
    }
}

@Preview(name = "IntroductionEdit")
@Composable
private fun IntroductionEditScreen_Preview() {
    AppTheme {
        IntroductionEditScreen(
            state = IntroductionEditState(
                isLoading = false,
            ),
            onAction = { },
        )
    }
}
