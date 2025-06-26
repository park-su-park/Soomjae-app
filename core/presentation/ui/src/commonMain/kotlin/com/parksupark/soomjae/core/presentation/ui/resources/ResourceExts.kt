package com.parksupark.soomjae.core.presentation.ui.resources

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

val StringResource.value
    @Composable
    get() = stringResource(this)

@Composable
fun StringResource.value(vararg args: Any) = stringResource(this, *args)

val DrawableResource.value
    @Composable
    get() = painterResource(this)
