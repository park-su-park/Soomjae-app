package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeFilterChip
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import kotlinx.collections.immutable.ImmutableList

interface FilterKey {
    val key: String
}

data class FilterChipItem<out K : FilterKey>(
    val key: K,
    val label: String,
    val selected: Boolean,
)

@Composable
fun <K : FilterKey> FilterChipRow(
    filters: ImmutableList<FilterChipItem<K>>,
    onFilterClick: (K) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
            .background(SoomjaeTheme.colorScheme.background1),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(items = filters, key = { it.key }) { filter ->
            SoomjaeFilterChip(
                selected = filter.selected,
                onClick = { onFilterClick(filter.key) },
                label = { Text(filter.label) },
            )
        }
    }
}
