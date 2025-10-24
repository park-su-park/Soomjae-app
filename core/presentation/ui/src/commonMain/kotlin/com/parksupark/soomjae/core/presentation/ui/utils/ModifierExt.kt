package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.ui.Modifier

inline fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier = if (condition) this.then(modifier()) else this

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier,
): Modifier = if (condition) this.then(ifTrue()) else this.then(ifFalse())
