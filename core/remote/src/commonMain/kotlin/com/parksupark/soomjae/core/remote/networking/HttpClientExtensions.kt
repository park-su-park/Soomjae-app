@file:Suppress("NoUnusedImports", "UnusedImports")

package com.parksupark.soomjae.core.remote.networking

import SoomJae.core.remote.BuildConfig
import arrow.core.Either
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.domain.failures.DataFailure
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.ContentConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): Either<DataFailure.Network, Response> = safeCall {
    get {
        url(constructRoute(route))
        queryParameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
): Either<DataFailure.Network, Response> = safeCall {
    post {
        url(constructRoute(route))
        setBody(body)
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.put(
    route: String,
    body: Request,
): Either<DataFailure.Network, Response> = safeCall {
    put {
        url(constructRoute(route))
        setBody(body)
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): Either<DataFailure.Network, Response> = safeCall {
    delete {
        url(constructRoute(route))
        queryParameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
}

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse,
): Either<DataFailure.Network, T> = try {
    responseToResult(execute())
} catch (e: Exception) {
    Logger.e(e, TAG) { "Network request failed: ${e.message}" }
    when (e) {
        is UnresolvedAddressException -> Either.Left(DataFailure.Network.NO_INTERNET)

        is ContentConvertException,
        is SerializationException,
        -> Either.Left(DataFailure.Network.SERIALIZATION)

        is CancellationException -> throw e

        else -> Either.Left(DataFailure.Network.UNKNOWN)
    }
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse,
): Either<DataFailure.Network, T> = when (response.status.value) {
    in 200..299 -> Either.Right(response.body<T>())
    400 -> Either.Left(DataFailure.Network.BAD_REQUEST)
    401 -> Either.Left(DataFailure.Network.UNAUTHORIZED)
    403 -> Either.Left(DataFailure.Network.FORBIDDEN)
    404 -> Either.Left(DataFailure.Network.NOT_FOUND)
    413 -> Either.Left(DataFailure.Network.PAYLOAD_TOO_LARGE)
    in 500..599 -> Either.Left(DataFailure.Network.SERVER_ERROR)
    else -> Either.Left(DataFailure.Network.UNKNOWN)
}

fun constructRoute(route: String): String = when {
    route.contains(BuildConfig.BASE_URL) -> route
    route.startsWith("/") -> BuildConfig.BASE_URL + route
    else -> BuildConfig.BASE_URL + "/$route"
}

const val TAG = "HttpClientExtensions"
