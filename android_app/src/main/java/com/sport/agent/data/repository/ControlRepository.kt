package com.sport.agent.data.repository

import com.google.gson.JsonObject
import com.sport.agent.data.local.ConnectionPreferences
import com.sport.agent.data.remote.ControlApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ControlRepository @Inject constructor(
    private val api: ControlApi,
    private val preferences: ConnectionPreferences
) {

    suspend fun fetchControlData(
        endpoint: String
    ): JsonObject {
        val config = preferences.getConfig()

        val baseUrl = config.baseUrl.trimEnd('/')
        val path = endpoint.trimStart('/')

        val fullUrl = "$baseUrl/$path"

        val authorization = config.apiToken
            .takeIf { it.isNotBlank() }
            ?.let { "Bearer $it" }

        val response = api.getControlData(
            url = fullUrl,
            authorization = authorization
        )

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "Error HTTP ${response.code()}: ${response.message()}"
            )
        }

        val body = response.body()
            ?: throw IllegalStateException(
                "El servidor respondió sin contenido."
            )

        if (
            body.has("success") &&
            !body.get("success").asBoolean
        ) {
            throw IllegalStateException(
                "El backend indicó que la operación no fue exitosa."
            )
        }

        return if (
            body.has("data") &&
            body.get("data").isJsonObject
        ) {
            body.getAsJsonObject("data")
        } else {
            body
        }
    }

    suspend fun testConnection(): JsonObject {
        return fetchControlData(
            "api/v1/control/overview"
        )
    }
}
