package com.parksupark.soomjae.core.presentation.ui.errors

import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.error_unknown
import com.parksupark.soomjae.core.presentation.ui.utils.UiText

fun DataFailure.asUiText(): UiText = when (this) {
    else -> UiText.StringResource(Res.string.error_unknown)
}
