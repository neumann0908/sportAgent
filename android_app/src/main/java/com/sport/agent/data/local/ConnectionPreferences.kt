package com.sport.agent.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.connectionDataStore by preferencesDataStore(
    name = "sport_connection_preferences"
)

data class ConnectionConfig(
    val baseUrl: String = "http://127.0.0.1:8000/",
    val apiToken: String = ""
)

@Singleton
class ConnectionPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val baseUrlKey = stringPreferencesKey("base_url")
    private val apiTokenKey = stringPreferencesKey("api_token")

    val config: Flow<ConnectionConfig> =
        context.connectionDataStore.data.map { preferences ->
            ConnectionConfig(
                baseUrl = preferences[baseUrlKey]
                    ?: "http://127.0.0.1:8000/",
                apiToken = preferences[apiTokenKey] ?: ""
            )
        }

    suspend fun getConfig(): ConnectionConfig {
        return config.first()
    }

    suspend fun saveConfig(
        baseUrl: String,
        apiToken: String
    ) {
        val normalizedUrl = normalizeBaseUrl(baseUrl)

        require(
            normalizedUrl.startsWith("http://") ||
            normalizedUrl.startsWith("https://")
        ) {
            "La dirección debe comenzar con http:// o https://"
        }

        require(
            !normalizedUrl.contains(" ")
        ) {
            "La dirección no puede contener espacios"
        }

        context.connectionDataStore.edit { preferences ->
            preferences[baseUrlKey] = normalizedUrl
            preferences[apiTokenKey] = apiToken.trim()
        }
    }

    private fun normalizeBaseUrl(url: String): String {
        val cleaned = url.trim()

        return if (cleaned.endsWith("/")) {
            cleaned
        } else {
            "$cleaned/"
        }
    }
}
