package com.parksupark.soomjae.features.auth.libs.google.authenticators

import androidx.compose.runtime.Composable

interface GoogleAuthProvider {
    @Composable
    fun getUiProvider(): GoogleAuthUi
}
