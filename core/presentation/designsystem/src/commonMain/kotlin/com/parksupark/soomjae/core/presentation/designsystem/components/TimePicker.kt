package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaeTimePicker(
    state: TimePickerState,
    modifier: Modifier = Modifier,
    colors: TimePickerColors = SoomjaeTimePickerDefaults.colors(),
    layoutType: TimePickerLayoutType = SoomjaeTimePickerDefaults.layoutType(),
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            onSurface = SoomjaeTheme.colorScheme.text2,
        ),
    ) {
        TimePicker(
            state = state,
            modifier = modifier,
            colors = colors,
            layoutType = layoutType,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object SoomjaeTimePickerDefaults {
    @Composable
    fun colors(): TimePickerColors = TimePickerColors(
        clockDialColor = SoomjaeTheme.colorScheme.background3,
        selectorColor = SoomjaeTheme.colorScheme.primary,
        containerColor = SoomjaeTheme.colorScheme.background1,
        periodSelectorBorderColor = SoomjaeTheme.colorScheme.text1,
        clockDialSelectedContentColor = SoomjaeTheme.colorScheme.text1W,
        clockDialUnselectedContentColor = SoomjaeTheme.colorScheme.text2,
        periodSelectorSelectedContainerColor = SoomjaeTheme.colorScheme.secondary2,
        periodSelectorUnselectedContainerColor = SoomjaeTheme.colorScheme.background3,
        periodSelectorSelectedContentColor = SoomjaeTheme.colorScheme.text1,
        periodSelectorUnselectedContentColor = SoomjaeTheme.colorScheme.text2,
        timeSelectorSelectedContainerColor = SoomjaeTheme.colorScheme.secondary2,
        timeSelectorUnselectedContainerColor = SoomjaeTheme.colorScheme.background3,
        timeSelectorSelectedContentColor = SoomjaeTheme.colorScheme.text1,
        timeSelectorUnselectedContentColor = SoomjaeTheme.colorScheme.text1,
    )

    @Composable
    fun layoutType(): TimePickerLayoutType = TimePickerDefaults.layoutType()
}
