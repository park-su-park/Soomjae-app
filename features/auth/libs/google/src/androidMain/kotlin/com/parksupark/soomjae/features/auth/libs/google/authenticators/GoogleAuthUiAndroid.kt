package com.parksupark.soomjae.features.auth.libs.google.authenticators

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import arrow.core.Either
import co.touchlab.kermit.Logger
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleAuthCredential
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser

internal class GoogleAuthUiAndroid(
    private val context: Context,
    private val credentials: GoogleAuthCredential,
    private val credentialManager: CredentialManager,
) : GoogleAuthUi {
    override suspend fun getUser(): Either<DataFailure.Credential, GoogleUser> {
        val googleIdOptions = GetGoogleIdOption.Builder()
            .setServerClientId(credentials.serverId)
            .setAutoSelectEnabled(false)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()

        return try {
            val response = credentialManager.getCredential(
                context = context,
                request = request,
            )

            val credential = response.credential
            val googleUser = getGoogleUserFromCredential(credential)

            if (googleUser != null) {
                Either.Right(googleUser)
            } else {
                Either.Left(DataFailure.Credential.INVALID)
            }
        } catch (e: NoCredentialException) {
            Logger.e(TAG, e) { "No credentials found in device." }

            return requestForLogin()
        } catch (e: GetCredentialException) {
            Logger.e(TAG, e) { "GetCredentialException" }

            Either.Left(DataFailure.Credential.UNKNOWN)
        } catch (e: Exception) {
            Logger.e(TAG, e) { "An unexpected error occurred while getting Google ID token." }

            Either.Left(DataFailure.Credential.UNKNOWN)
        }
    }

    private suspend fun requestForLogin(): Either<DataFailure.Credential, GoogleUser> {
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(serverClientId = credentials.serverId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        return try {
            val response = credentialManager.getCredential(
                context = context,
                request = request,
            )

            val credential = response.credential
            val googleUser = getGoogleUserFromCredential(credential)

            if (googleUser != null) {
                Either.Right(googleUser)
            } else {
                Either.Left(DataFailure.Credential.INVALID)
            }
        } catch (e: GetCredentialException) {
            Logger.e(TAG, e) { "GetCredentialException" }

            Either.Left(DataFailure.Credential.UNKNOWN)
        } catch (e: Exception) {
            Logger.e(TAG, e) { "An unexpected error occurred while getting Google ID token." }

            Either.Left(DataFailure.Credential.UNKNOWN)
        }
    }

    private fun getGoogleUserFromCredential(credential: Credential): GoogleUser? = try {
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            GoogleUser(
                idToken = googleIdTokenCredential.idToken,
                accessToken = null,
                email = googleIdTokenCredential.id,
                displayName = googleIdTokenCredential.displayName ?: "",
                profilePicUrl = googleIdTokenCredential.profilePictureUri?.toString(),
            )
        } else {
            Logger.e(TAG) { "Received credential is not a Google ID token credential. Credential: $credential" }

            null
        }
    } catch (e: GoogleIdTokenParsingException) {
        Logger.e(TAG, e) { "Failed to parse Google ID token credential." }

        null
    }
}

private const val TAG = "GoogleAuthenticator"
