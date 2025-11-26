package com.parksupark.soomjae.core.presentation.ui.errors

import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.error_local_conflict
import com.parksupark.soomjae.core.presentation.ui.resources.error_local_not_found
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_bad_request
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_forbidden
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_no_internet
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_not_found
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_parsing
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_payload_too_large
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_server
import com.parksupark.soomjae.core.presentation.ui.resources.error_network_unauthorized
import com.parksupark.soomjae.core.presentation.ui.resources.error_unknown
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.core.domain.failures.DataFailure.Local as LocalFailure
import com.parksupark.soomjae.core.domain.failures.DataFailure.Network as NetworkFailure

fun DataFailure.asUiText(): UiText = when (this) {
    is NetworkFailure -> this.asUiText()
    is LocalFailure -> this.asUiText()
    else -> UiText.StringResource(Res.string.error_unknown)
}

fun NetworkFailure.asUiText(): UiText = when (this) {
    NetworkFailure.BAD_REQUEST -> UiText.StringResource(Res.string.error_network_bad_request)
    NetworkFailure.UNAUTHORIZED -> UiText.StringResource(Res.string.error_network_unauthorized)
    NetworkFailure.FORBIDDEN -> UiText.StringResource(Res.string.error_network_forbidden)
    NetworkFailure.NOT_FOUND -> UiText.StringResource(Res.string.error_network_not_found)
    NetworkFailure.NO_INTERNET -> UiText.StringResource(Res.string.error_network_no_internet)
    NetworkFailure.PAYLOAD_TOO_LARGE -> UiText.StringResource(
        Res.string.error_network_payload_too_large,
    )

    NetworkFailure.SERVER_ERROR -> UiText.StringResource(Res.string.error_network_server)
    NetworkFailure.SERIALIZATION -> UiText.StringResource(Res.string.error_network_parsing)
    NetworkFailure.UNKNOWN -> UiText.StringResource(Res.string.error_unknown)
}

fun LocalFailure.asUiText(): UiText = when (this) {
    LocalFailure.DISK_FULL -> UiText.StringResource(Res.string.error_local_conflict)
    LocalFailure.NOT_FOUND -> UiText.StringResource(Res.string.error_local_not_found)
    LocalFailure.UNKNOWN -> UiText.StringResource(Res.string.error_unknown)
}
