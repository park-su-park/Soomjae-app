package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeColors
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SoomjaeDatePicker(
    state: DatePickerState,
    modifier: Modifier = Modifier,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    colors: DatePickerColors = SoomjaeDatePickerDefaults.colors(),
    title: (@Composable () -> Unit)? = {
        SoomjaeDatePickerDefaults.DatePickerTitle(
            displayMode = state.displayMode,
            modifier = Modifier.padding(DatePickerTitlePadding),
            contentColor = colors.titleContentColor,
        )
    },
    headline: (@Composable () -> Unit)? = {
        SoomjaeDatePickerDefaults.DatePickerHeadline(
            selectedDateMillis = state.selectedDateMillis,
            displayMode = state.displayMode,
            dateFormatter = dateFormatter,
            modifier = Modifier.padding(DatePickerHeadlinePadding),
            contentColor = colors.headlineContentColor,
        )
    },
    showModeToggle: Boolean = true,
) {
    DatePicker(
        state = state,
        modifier = modifier,
        dateFormatter = dateFormatter,
        colors = colors,
        title = title,
        headline = headline,
        showModeToggle = showModeToggle,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
object SoomjaeDatePickerDefaults {
    internal val SoomjaeColors.defaultDatePickerColors: DatePickerColors
        @Composable get() = this.defaultDatePickerColorsCached ?: DatePickerColors(
            containerColor = SoomjaeTheme.colorScheme.background1,
            weekdayContentColor = SoomjaeTheme.colorScheme.text2,
            subheadContentColor = SoomjaeTheme.colorScheme.text3,
            navigationContentColor = SoomjaeTheme.colorScheme.icon,
            yearContentColor = SoomjaeTheme.colorScheme.text2,
            disabledYearContentColor = SoomjaeTheme.colorScheme.text4,
            currentYearContentColor = SoomjaeTheme.colorScheme.primary,
            selectedYearContentColor = SoomjaeTheme.colorScheme.cta,
            disabledSelectedYearContentColor = SoomjaeTheme.colorScheme.ctaDisabled,
            selectedYearContainerColor = SoomjaeTheme.colorScheme.ctaSecondary,
            disabledSelectedYearContainerColor = SoomjaeTheme.colorScheme.ctaDisabled,
            dayContentColor = SoomjaeTheme.colorScheme.text1,
            disabledDayContentColor = SoomjaeTheme.colorScheme.text4,
            selectedDayContentColor = SoomjaeTheme.colorScheme.cta,
            disabledSelectedDayContentColor = SoomjaeTheme.colorScheme.ctaDisabled,
            selectedDayContainerColor = SoomjaeTheme.colorScheme.ctaSecondary,
            disabledSelectedDayContainerColor = SoomjaeTheme.colorScheme.ctaDisabled,
            todayContentColor = SoomjaeTheme.colorScheme.info,
            todayDateBorderColor = SoomjaeTheme.colorScheme.info,
            dayInSelectionRangeContainerColor = SoomjaeTheme.colorScheme.ctaSecondary,
            dayInSelectionRangeContentColor = SoomjaeTheme.colorScheme.ctaSecondaryText,
            dividerColor = SoomjaeTheme.colorScheme.divider1,
            dateTextFieldColors = SoomjaeTextFieldDefaults.defaultOutlinedTextFieldColors,
            titleContentColor = SoomjaeTheme.colorScheme.text1,
            headlineContentColor = SoomjaeTheme.colorScheme.text2,
        )

    @Composable
    fun colors(): DatePickerColors = SoomjaeTheme.colorScheme.defaultDatePickerColors

    @Composable
    fun colors(
        containerColor: Color = Color.Unspecified,
        titleContentColor: Color = Color.Unspecified,
        headlineContentColor: Color = Color.Unspecified,
        weekdayContentColor: Color = Color.Unspecified,
        subheadContentColor: Color = Color.Unspecified,
        navigationContentColor: Color = Color.Unspecified,
        yearContentColor: Color = Color.Unspecified,
        disabledYearContentColor: Color = Color.Unspecified,
        currentYearContentColor: Color = Color.Unspecified,
        selectedYearContentColor: Color = Color.Unspecified,
        disabledSelectedYearContentColor: Color = Color.Unspecified,
        selectedYearContainerColor: Color = Color.Unspecified,
        disabledSelectedYearContainerColor: Color = Color.Unspecified,
        dayContentColor: Color = Color.Unspecified,
        disabledDayContentColor: Color = Color.Unspecified,
        selectedDayContentColor: Color = Color.Unspecified,
        disabledSelectedDayContentColor: Color = Color.Unspecified,
        selectedDayContainerColor: Color = Color.Unspecified,
        disabledSelectedDayContainerColor: Color = Color.Unspecified,
        todayContentColor: Color = Color.Unspecified,
        todayDateBorderColor: Color = Color.Unspecified,
        dayInSelectionRangeContentColor: Color = Color.Unspecified,
        dayInSelectionRangeContainerColor: Color = Color.Unspecified,
        dividerColor: Color = Color.Unspecified,
        dateTextFieldColors: TextFieldColors? = null,
    ): DatePickerColors = SoomjaeTheme.colorScheme.defaultDatePickerColors.copy(
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        headlineContentColor = headlineContentColor,
        weekdayContentColor = weekdayContentColor,
        subheadContentColor = subheadContentColor,
        navigationContentColor = navigationContentColor,
        yearContentColor = yearContentColor,
        disabledYearContentColor = disabledYearContentColor,
        currentYearContentColor = currentYearContentColor,
        selectedYearContentColor = selectedYearContentColor,
        disabledSelectedYearContentColor = disabledSelectedYearContentColor,
        selectedYearContainerColor = selectedYearContainerColor,
        disabledSelectedYearContainerColor = disabledSelectedYearContainerColor,
        dayContentColor = dayContentColor,
        disabledDayContentColor = disabledDayContentColor,
        selectedDayContentColor = selectedDayContentColor,
        disabledSelectedDayContentColor = disabledSelectedDayContentColor,
        selectedDayContainerColor = selectedDayContainerColor,
        disabledSelectedDayContainerColor = disabledSelectedDayContainerColor,
        todayContentColor = todayContentColor,
        todayDateBorderColor = todayDateBorderColor,
        dayInSelectionRangeContentColor = dayInSelectionRangeContentColor,
        dayInSelectionRangeContainerColor = dayInSelectionRangeContainerColor,
        dividerColor = dividerColor,
        dateTextFieldColors = dateTextFieldColors,
    )

    @Composable
    fun DatePickerTitle(
        displayMode: DisplayMode,
        modifier: Modifier = Modifier,
        contentColor: Color = SoomjaeDatePickerDefaults.colors().titleContentColor,
    ) {
        DatePickerDefaults.DatePickerTitle(
            displayMode = displayMode,
            modifier = modifier,
            contentColor = contentColor,
        )
    }

    @Composable
    fun DatePickerHeadline(
        selectedDateMillis: Long?,
        displayMode: DisplayMode,
        dateFormatter: DatePickerFormatter,
        modifier: Modifier = Modifier,
        contentColor: Color = SoomjaeDatePickerDefaults.colors().headlineContentColor,
    ) {
        DatePickerDefaults.DatePickerHeadline(
            selectedDateMillis = selectedDateMillis,
            displayMode = displayMode,
            dateFormatter = dateFormatter,
            modifier = modifier,
            contentColor = contentColor,
        )
    }
}

private val DatePickerTitlePadding = PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp)
private val DatePickerHeadlinePadding = PaddingValues(start = 24.dp, end = 12.dp, bottom = 12.dp)

@Preview
@Composable
private fun SoomjaeDatePickerPreview() {
    AppTheme {
        SoomjaeDatePicker(
            state = rememberDatePickerState(),
        )
    }
}
