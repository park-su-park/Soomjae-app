package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCheckbox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeFilterChip
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeListItem
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.resources.Res
import com.parksupark.soomjae.features.posts.common.presentation.resources.selection_dialog_cancel_button
import com.parksupark.soomjae.features.posts.common.presentation.resources.selection_dialog_confirm_button
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MultipleSelectionDialog(
    items: ImmutableList<T>,
    itemKey: ((item: T) -> Any)? = null,
    itemName: @Composable (item: T) -> String,
    onCancelClick: () -> Unit,
    onConfirmClick: (ImmutableSet<T>) -> Unit,
    modifier: Modifier = Modifier,
    selectedItems: ImmutableSet<T> = persistentSetOf(),
    onDismissRequest: () -> Unit = { },
) {
    Dialog(onDismissRequest = onDismissRequest) {
        MultipleSelectionDialogContent(
            items = items,
            itemKey = itemKey,
            selectedItems = selectedItems,
            itemName = itemName,
            onCancelClick = onCancelClick,
            onConfirmClick = onConfirmClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun <T> MultipleSelectionDialogContent(
    items: ImmutableList<T>,
    itemKey: ((T) -> Any)?,
    selectedItems: ImmutableSet<T>,
    itemName: @Composable (T) -> String,
    onCancelClick: () -> Unit,
    onConfirmClick: (ImmutableSet<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedItemsCache by remember(selectedItems) {
        mutableStateOf(selectedItems)
    }
    val maxListHeight = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.height.toDp()
    } * 0.5f

    SoomjaeSurface(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(modifier = Modifier.padding(vertical = 24.dp)) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .defaultMinSize(minHeight = 36.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(
                    items = selectedItemsCache.toPersistentList(),
                    key = itemKey,
                ) { item ->
                    SelectedIndicatorChip(
                        label = itemName(item),
                        onClick = {
                            selectedItemsCache = selectedItemsCache.toMutableSet().apply {
                                remove(item)
                            }.toPersistentSet()
                        },
                        modifier = Modifier.animateItem(),
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .heightIn(max = maxListHeight)
                    .animateContentSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(items = items, key = itemKey) { item ->
                    val selected = selectedItemsCache.contains(item)

                    ListItem(
                        item = item,
                        selected = selected,
                        onClick = {
                            selectedItemsCache = selectedItemsCache.toMutableSet().apply {
                                if (selected) remove(item) else add(item)
                            }.toPersistentSet()
                        },
                        itemName = itemName,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            ) {
                SoomjaeTextButton(
                    onClick = {
                        selectedItemsCache = selectedItems
                        onCancelClick()
                    },
                    content = { Text(text = Res.string.selection_dialog_cancel_button.value) },
                )
                SoomjaeTextButton(
                    onClick = {
                        onConfirmClick(selectedItemsCache.toPersistentSet())
                    },
                    content = { Text(text = Res.string.selection_dialog_confirm_button.value) },
                )
            }
        }
    }
}

@Composable
private fun SelectedIndicatorChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeFilterChip(
        selected = false,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = SoomjaeTheme.typography.labelM,
            )
        },
        modifier = modifier,
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.icon,
            )
        },
    )
}

@Composable
private fun <T> ListItem(
    item: T,
    selected: Boolean,
    onClick: () -> Unit,
    itemName: @Composable ((item: T) -> String),
    modifier: Modifier = Modifier,
) {
    SoomjaeListItem(
        headlineContent = { Text(itemName(item)) },
        modifier = modifier.clickable {
            onClick()
        },
        leadingContent = {
            SoomjaeCheckbox(
                checked = selected,
                onCheckedChange = null,
            )
        },
    )
}

@Preview
@Composable
private fun MultipleSelectionDialogPreview() {
    AppTheme {
        Box {
            MultipleSelectionDialogContent(
                items = persistentListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                itemKey = { it },
                itemName = { "Option $it" },
                onCancelClick = { },
                onConfirmClick = { },
                selectedItems = persistentSetOf(2, 4, 6),
            )
        }
    }
}
