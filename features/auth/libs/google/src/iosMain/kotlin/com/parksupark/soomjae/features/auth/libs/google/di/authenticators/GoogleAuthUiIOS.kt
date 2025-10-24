package com.parksupark.soomjae.features.auth.libs.google.di.authenticators

import arrow.core.Either
import co.touchlab.kermit.Logger
import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthUi
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
internal class GoogleAuthUiIOS : GoogleAuthUi {
    override suspend fun getUser(scope: List<String>): Either<DataFailure.Credential, GoogleUser> {
        val rootViewController = getRootViewController()
        if (rootViewController == null) {
            Logger.e { "Root view controller is null" }
            return Either.Left(DataFailure.Credential.UNKNOWN)
        }

        return signIn(rootViewController, scope)
    }

    private suspend fun signIn(
        presentingViewController: UIViewController,
        scope: List<String>,
    ): Either<DataFailure.Credential, GoogleUser> = suspendCoroutine { continuation ->
        GIDSignIn.sharedInstance.signInWithPresentingViewController(
            presentingViewController,
            null,
            scope,
        ) { gidSignInResult, nsError ->
            if (nsError != null) {
                Logger.e { "Error While signing: $nsError" }
                continuation.resume(Either.Left(DataFailure.Credential.UNKNOWN))
                return@signInWithPresentingViewController
            }

            if (gidSignInResult == null) {
                continuation.resume(Either.Left(DataFailure.Credential.UNKNOWN))
            } else {
                continuation.resume(mapResultToGoogleUser(gidSignInResult))
            }
        }
    }

    private fun getRootViewController(): UIViewController? =
        UIApplication.sharedApplication.keyWindow?.rootViewController

    private fun mapResultToGoogleUser(
        gidSignInResult: GIDSignInResult,
    ): Either<DataFailure.Credential, GoogleUser> {
        val user = gidSignInResult.user
        val idToken = user.idToken?.tokenString
        val accessToken = user.accessToken.tokenString

        return if (idToken == null) {
            Logger.e { "ID token is null, idToken: $idToken" }
            Either.Left(DataFailure.Credential.UNKNOWN)
        } else {
            val profile = user.profile
            val googleUser = GoogleUser(
                idToken = idToken,
                accessToken = accessToken,
                email = profile?.email,
                displayName = profile?.name ?: "",
                profilePicUrl = profile?.imageURLWithDimension(320u)?.absoluteString,
                serverAuthCode = gidSignInResult.serverAuthCode,
            )
            Either.Right(googleUser)
        }
    }
}
