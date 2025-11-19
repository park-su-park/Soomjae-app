package com.parksupark.soomjae.core.notification.data.service

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.notification.data.dto.request.DeleteDeviceTokenRequest
import com.parksupark.soomjae.core.notification.data.dto.request.RegisterDeviceTokenRequest
import com.parksupark.soomjae.core.notification.domain.service.DeviceTokenService
import com.parksupark.soomjae.core.remote.networking.post
import io.ktor.client.HttpClient

class KtorDeviceTokenService(
    private val httpClient: HttpClient,
) : DeviceTokenService {

    override suspend fun registerToken(
        token: String,
        deviceId: String,
        platform: String,
    ): Either<DataFailure.Network, Unit> = httpClient.post(
        route = "/v1/fcm-token",
        body = RegisterDeviceTokenRequest(
            token = token,
            deviceId = deviceId,
            platform = platform,
        ),
    )

    override suspend fun unregisterToken(deviceId: String): Either<DataFailure.Network, Unit> =
        httpClient.post<DeleteDeviceTokenRequest, Unit>(
            route = "/v1/fcm-token/delete",
            body = DeleteDeviceTokenRequest(
                deviceId = deviceId,
            )
        )
}
