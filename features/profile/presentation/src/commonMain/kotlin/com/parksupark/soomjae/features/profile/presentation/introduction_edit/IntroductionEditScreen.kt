package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IntroductionEditScreen(
    state: IntroductionEditState,
    onAction: (IntroductionEditActions) -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
        },
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(innerPadding),
        ) {
            // TODO Add UI content here
        }
    }
}

@Preview(name = "IntroductionEdit")
@Composable
private fun IntroductionEditScreenPreview() {
    IntroductionEditScreen(
        state = IntroductionEditState(),
        onAction = { },
    )
}
