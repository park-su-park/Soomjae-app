package com.parksupark.soomjae.core.data.auth.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.parksupark.soomjae.core.data.auth.entities.AuthInfoSerializable
import com.parksupark.soomjae.core.data.auth.entities.toAuthInfo
import com.parksupark.soomjae.core.data.auth.entities.toAuthInfoSerializable
import com.parksupark.soomjae.core.data.datastore.SessionDataStoreKey
import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class SessionRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SessionRepository {
    override suspend fun get(): AuthInfo? = dataStore.data.first()[SessionDataStoreKey.SESSION]?.let { json ->
        Json.decodeFromString<AuthInfoSerializable>(json).toAuthInfo()
    }

    override fun getAsFlow(): Flow<AuthInfo?> = dataStore.data.map { session ->
        session[SessionDataStoreKey.SESSION]?.let { json ->
            Json.decodeFromString<AuthInfoSerializable>(json).toAuthInfo()
        }
    }

    override suspend fun set(info: AuthInfo?) {
        dataStore.edit { session ->
            if (info == null) {
                session.remove(SessionDataStoreKey.SESSION)
            } else {
                val json = Json.encodeToString(info.toAuthInfoSerializable())
                session[SessionDataStoreKey.SESSION] = json
            }
        }
    }
}
