package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaeDatePicker(
    state: DatePickerState,
    modifier: Modifier = Modifier,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    colors: DatePickerColors = SoomjaeDatePickerDefaults.colors(),
    title: (@Composable () -> Unit)? = {
        DatePickerDefaults.DatePickerTitle(
            displayMode = state.displayMode,
            modifier = Modifier.padding(DatePickerTitlePadding),
        )
    },
    headline: (@Composable () -> Unit)? = {
        DatePickerDefaults.DatePickerHeadline(
            selectedDateMillis = state.selectedDateMillis,
            displayMode = state.displayMode,
            dateFormatter = dateFormatter,
            modifier = Modifier.padding(DatePickerHeadlinePadding),
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
    @Composable
    fun colors(): DatePickerColors = DatePickerDefaults.colors(
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
}

private val DatePickerTitlePadding = PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp)
private val DatePickerHeadlinePadding = PaddingValues(start = 24.dp, end = 12.dp, bottom = 12.dp)
