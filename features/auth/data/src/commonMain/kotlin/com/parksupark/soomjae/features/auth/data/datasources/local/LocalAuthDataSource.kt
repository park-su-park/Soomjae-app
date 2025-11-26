package com.parksupark.soomjae.features.auth.data.datasources.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import arrow.core.Either
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.data.datastore.SettingDataStoreKey
import com.parksupark.soomjae.core.domain.failures.DataFailure
import kotlinx.coroutines.flow.first

internal class LocalAuthDataSource(
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun saveEmail(email: String): Either<DataFailure.Local, Unit> = try {
        dataStore.edit { preferences ->
            preferences[SettingDataStoreKey.SAVED_EMAIL] = email
        }

        Either.Right(Unit)
    } catch (e: IOException) {
        Logger.e(e, TAG) { "Error saving email to local storage" }

        Either.Left(DataFailure.Local.UNKNOWN) // Indicate a local data storage failure
    }

    suspend fun loadSavedEmail(): Either<DataFailure.Local, String> = try {
        val email = dataStore.data.first()[SettingDataStoreKey.SAVED_EMAIL]

        if (email.isNullOrEmpty()) {
            Logger.d(TAG) { "No email found in local storage" }

            Either.Left(DataFailure.Local.NOT_FOUND)
        } else {
            Either.Right(email)
        }
    } catch (e: IOException) {
        Logger.e(e, TAG) { "Error loading email from local storage" }

        Either.Left(DataFailure.Local.UNKNOWN)
    }

    suspend fun clearSavedEmail(): Either<DataFailure.Local, Unit> = try {
        dataStore.edit { preferences ->
            preferences.remove(SettingDataStoreKey.SAVED_EMAIL)
        }

        Either.Right(Unit)
    } catch (e: IOException) {
        Logger.e(e, TAG) { "Error deleting email from local storage" }

        Either.Left(DataFailure.Local.UNKNOWN)
    }
}

private const val TAG = "LocalAuthDataSource"
