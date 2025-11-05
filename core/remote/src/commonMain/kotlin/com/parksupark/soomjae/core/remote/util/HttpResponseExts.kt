package com.parksupark.soomjae.core.remote.util

import com.parksupark.soomjae.core.remote.networking.HttpCookieConstants
import com.parksupark.soomjae.core.remote.networking.HttpHeaderConstants
import io.ktor.client.statement.HttpResponse

private const val SET_COOKIE_KEY = HttpHeaderConstants.SET_COOKIE
private const val REFRESH_TOKEN_PREFIX = HttpCookieConstants.REFRESH_TOKEN

fun HttpResponse.getRefreshTokenFromCookies(): String? = this.headers[SET_COOKIE_KEY]
    ?.split(";")
    ?.map { it.trim() }
    ?.firstOrNull { it.startsWith(REFRESH_TOKEN_PREFIX) }
    ?.removePrefix(REFRESH_TOKEN_PREFIX)
