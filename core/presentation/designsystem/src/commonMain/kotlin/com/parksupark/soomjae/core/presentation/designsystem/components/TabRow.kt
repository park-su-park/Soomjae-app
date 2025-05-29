package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaeTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = SoomjaeTabRowDefaults.primaryContainerColor,
    contentColor: Color = SoomjaeTabRowDefaults.primaryContentColor,
    indicator: @Composable TabIndicatorScope.() -> Unit = {
        SoomjaeTabRowDefaults.PrimaryIndicator(
            modifier = Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
            width = Dp.Unspecified,
        )
    },
    divider: @Composable () -> Unit = @Composable {
        HorizontalDivider(
            color = SoomjaeTabRowDefaults.dividerColor,
        )
    },
    tabs: @Composable () -> Unit,
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs,
    )
}

object SoomjaeTabRowDefaults {
    val primaryContainerColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.background1

    val primaryContentColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.text1

    val dividerColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.divider1

    @Composable
    fun PrimaryIndicator(
        modifier: Modifier = Modifier,
        width: Dp = 24.dp,
        height: Dp = 3.dp,
        color: Color = SoomjaeTheme.colorScheme.text1,
        shape: Shape = RoundedCornerShape(3.dp),
    ) {
        Spacer(
            modifier
                .requiredHeight(height)
                .requiredWidth(width)
                .background(color = color, shape = shape),
        )
    }
}
