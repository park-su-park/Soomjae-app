package com.parksupark.soomjae.features.auth.presentation.failure

import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.auth.domain.failures.VerificationFailure
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.error_invalid_verification_code

fun VerificationFailure.asUiText(): UiText = when (this) {
    is VerificationFailure.DataFailure -> this.error.asUiText()
    VerificationFailure.InvalidCode -> UiText.StringResource(
        Res.string.error_invalid_verification_code,
    )
}
