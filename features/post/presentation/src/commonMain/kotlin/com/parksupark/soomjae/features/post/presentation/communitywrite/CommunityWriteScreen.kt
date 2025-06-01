package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.components.WriteInputSection
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.community_write_button_confirm_description
import com.parksupark.soomjae.features.post.presentation.resources.community_write_category_select_placeholder
import com.parksupark.soomjae.features.post.presentation.resources.community_write_navigate_up_description
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CommunityWriteScreen(
    state: CommunityWriteState,
    onAction: (CommunityWriteAction) -> Unit,
    snackbarHost: @Composable () -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            CommunityWriteTopBar(
                onBackClick = { onAction(CommunityWriteAction.OnBackClick) },
                onConfirmClick = { onAction(CommunityWriteAction.OnConfirmClick) },
                canSubmit = state.canSubmit,
            )
        },
        snackbarHost = snackbarHost,
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            WriteInputSection(
                title = state.inputTitle,
                content = state.inputContent,
            )

            AdditionalInfoSelection(
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelect = { categoryId ->
                    onAction(CommunityWriteAction.OnCategorySelected(categoryId))
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityWriteTopBar(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    canSubmit: Boolean,
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
                enabled = canSubmit,
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

@Composable
private fun AdditionalInfoSelection(
    categories: ImmutableList<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelect: (Long) -> Unit,
) {
    var isCategoryVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        OutlinedButton(
            onClick = { isCategoryVisible = categories.isNotEmpty() && true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors().copy(
                containerColor = Color.Transparent,
                contentColor = SoomjaeTheme.colorScheme.text2,
            ),
            border = BorderStroke(1.dp, SoomjaeTheme.colorScheme.text2),
        ) {
            val modifier = Modifier.fillMaxWidth()
            if (selectedCategory == null) {
                Text(
                    text = Res.string.community_write_category_select_placeholder.value,
                    style = LocalTextStyle.current.copy(
                        color = LocalContentColor.current.copy(alpha = 0.4f),
                    ),
                    modifier = modifier,
                )
            } else {
                Text(text = selectedCategory.name, modifier = modifier)
            }
        }

        if (isCategoryVisible) {
            Dialog(onDismissRequest = {}) {
                SoomjaeSurface(elevation = 4.dp) {
                    LazyColumn {
                        items(items = categories, key = { it.id }) { category ->
                            CategoryItem(
                                category = category,
                                onCategorySelect = {
                                    onCategorySelect(category.id)
                                    isCategoryVisible = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryUi,
    onCategorySelect: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(category.name) },
        modifier = Modifier.clickable {
            onCategorySelect()
        },
    )
}
