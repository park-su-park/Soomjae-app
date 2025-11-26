package com.parksupark.soomjae.features.home.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeOutlinedTextField
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.features.home.presentation.resources.Res
import com.parksupark.soomjae.features.home.presentation.resources.home_search_hint
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeScreen(
    bottomBar: @Composable () -> Unit,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    SoomjaeScaffold(bottomBar = bottomBar) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SoomjaeOutlinedTextField(
                state = rememberTextFieldState(),
                startIcon = Icons.Default.Search,
                hint = stringResource(Res.string.home_search_hint),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}
