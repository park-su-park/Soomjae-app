package com.parksupark.soomjae.core.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeNavigationBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeNavigationBarItem
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem
import com.parksupark.soomjae.core.presentation.ui.resources.value
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SoomjaeBottomNavigationBar(
    items: ImmutableList<NavigationBarItem>,
    isSelected: (NavigationBarItem) -> Boolean,
    onClick: (NavigationBarItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeNavigationBar(modifier = modifier) {
        items.forEach { item ->
            val isSelected = isSelected(item)

            SoomjaeNavigationBarItem(
                selected = isSelected,
                onClick = { onClick(item) },
                icon = { NavigationBarIcon(item, isSelected) },
                label = { NavigationBarLabel(item) },
            )
        }
    }
}

@Composable
private fun NavigationBarIcon(
    item: NavigationBarItem,
    selected: Boolean,
) {
    Icon(
        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
        contentDescription = item.label.value,
    )
}

@Composable
private fun NavigationBarLabel(item: NavigationBarItem) {
    Text(text = item.label.value)
}
