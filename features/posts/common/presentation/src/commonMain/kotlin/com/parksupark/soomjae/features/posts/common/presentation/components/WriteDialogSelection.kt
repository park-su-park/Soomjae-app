package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeListItem
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeRadioButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> WriteDialogSelection(
    items: ImmutableList<T>,
    label: String,
    placeHolder: String,
    onItemSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    selectedItem: T? = null,
    itemKey: ((item: T) -> Any)? = null,
    itemName: ((item: T) -> String)? = null,
) {
    var showDialog by remember { mutableStateOf(false) }
    val getItemName = itemName ?: { it.toString() }

    WriteSelectionButton(
        label = label,
        onClick = { if (items.isNotEmpty()) showDialog = true },
        enabled = items.isNotEmpty(),
        modifier = modifier,
        buttonText = {
            val textModifier = Modifier.fillMaxWidth()
            if (selectedItem == null) {
                Text(
                    text = placeHolder,
                    style = LocalTextStyle.current.copy(
                        color = SoomjaeTheme.colorScheme.text4,
                    ),
                    modifier = textModifier,
                )
            } else {
                Text(
                    text = getItemName(selectedItem),
                    modifier = textModifier,
                )
            }
        },
    )

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            SoomjaeSurface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
                LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                    items(items = items, key = itemKey) { item ->
                        ListItem(
                            item = item,
                            selected = item == selectedItem,
                            onClick = {
                                onItemSelect(item)
                                showDialog = false
                            },
                            itemName = getItemName,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun <T> ListItem(
    item: T,
    selected: Boolean,
    onClick: () -> Unit,
    itemName: ((item: T) -> String),
    modifier: Modifier = Modifier,
) {
    SoomjaeListItem(
        headlineContent = { Text(itemName(item)) },
        modifier = modifier.clickable {
            onClick()
        },
        leadingContent = {
            SoomjaeRadioButton(
                selected = selected,
                onClick = null,
            )
        },
    )
}
