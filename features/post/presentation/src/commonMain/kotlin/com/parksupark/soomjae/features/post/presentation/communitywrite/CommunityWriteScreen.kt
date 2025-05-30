package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.community_write_button_confirm_description
import com.parksupark.soomjae.features.post.presentation.resources.community_write_navigate_up_description

@Composable
internal fun CommunityWriteScreen(
    state: CommunityWriteState,
    onAction: (CommunityWriteAction) -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            CommunityWriteTopBar(
                onBackClick = { onAction(CommunityWriteAction.OnBackClick) },
                onConfirmClick = { onAction(CommunityWriteAction.OnConfirmClick) },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityWriteTopBar(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Res.string.community_write_navigate_up_description.value,
                    )
                },
            )
        },
        actions = {
            IconButton(
                onClick = onConfirmClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = Res.string.community_write_button_confirm_description.value,
                    )
                },
            )
        },
    )
}
