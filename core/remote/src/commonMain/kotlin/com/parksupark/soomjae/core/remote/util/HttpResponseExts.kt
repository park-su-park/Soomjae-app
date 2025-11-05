package com.parksupark.soomjae.core.remote.util

import com.parksupark.soomjae.core.remote.networking.HttpCookieKey
import com.parksupark.soomjae.core.remote.networking.HttpHeaderKey
import io.ktor.client.statement.HttpResponse

private const val SET_COOKIE_KEY = HttpHeaderKey.SET_COOKIE
private const val REFRESH_TOKEN_PREFIX = HttpCookieKey.REFRESH_TOKEN

fun HttpResponse.getRefreshTokenFromCookies(): String? = this.headers[SET_COOKIE_KEY]
    ?.split(";")
    ?.map { it.trim() }
    ?.firstOrNull { it.startsWith(REFRESH_TOKEN_PREFIX) }
    ?.substringAfter("${REFRESH_TOKEN_PREFIX}=")
