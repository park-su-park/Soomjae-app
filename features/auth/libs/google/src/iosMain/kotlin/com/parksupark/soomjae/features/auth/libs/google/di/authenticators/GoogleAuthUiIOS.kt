package com.parksupark.soomjae.features.auth.libs.google.di.authenticators

import arrow.core.Either
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthUi
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser
import kotlin.coroutines.resume
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

@OptIn(ExperimentalForeignApi::class)
internal class GoogleAuthUiIOS(
    private val service: GoogleAuthService,
    private val dispatcher: SoomjaeDispatcher,
) : GoogleAuthUi {

    override suspend fun getUser(scope: List<String>): Either<DataFailure.Credential, GoogleUser> =
        withContext(dispatcher.main) {
            suspendCancellableCoroutine { cont ->
                // Swift 래퍼는 (token or error) 콜백 제공
                service.signInWithScope(scope) {
                    idToken: String?,
                    accessToken: String?,
                    email: String?,
                    name: String?,
                    photoUrl: String?,
                    serverAuthCode: String?,
                    error: String?,
                    ->
                    if (error != null || idToken == null) {
                        cont.resume(Either.Left(DataFailure.Credential.UNKNOWN))
                        return@signInWithScope
                    }
                    val user = GoogleUser(
                        idToken = idToken,
                        accessToken = accessToken.orEmpty(),
                        email = email,
                        displayName = name.orEmpty(),
                        profilePicUrl = photoUrl,
                        serverAuthCode = serverAuthCode,
                    )
                    cont.resume(Either.Right(user))
                }
            }
        }
}
