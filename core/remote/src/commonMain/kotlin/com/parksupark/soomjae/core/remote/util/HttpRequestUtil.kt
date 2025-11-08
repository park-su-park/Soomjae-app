package com.parksupark.soomjae.core.remote.util

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

fun HttpRequestBuilder.addQueryParameters(params: Map<String, Any?>) {
    params.forEach { (key, value) ->
        if (value is Iterable<*>) {
            value.forEach {
                parameter(key, it)
            }
        } else {
            parameter(key, value)
        }
    }
}
