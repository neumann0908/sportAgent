package com.sport.agent.data.repository

import com.google.gson.JsonObject
import com.sport.agent.data.local.ConnectionPreferences
import com.sport.agent.data.remote.ControlApi
import com.sport.agent.domain.model.SportResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SportsRepository @Inject constructor(
    private val api: ControlApi,
    private val preferences: ConnectionPreferences
) {
    suspend fun getOverview(sport: SportResource): JsonObject {
        val config = preferences.getConfig()
        val baseUrl = config.baseUrl.trim().trimEnd('/')

        require(
            baseUrl.startsWith("http://") ||
            baseUrl.startsWith("https://")
        ) {
            "Configura una URL válida del backend."
        }

        // Endpoint actualizado con el sufijo /overview según el contrato
        val endpoint = "$baseUrl/api/v1/sports/${sport.apiKey}/overview"
        
        val authorization = config.apiToken
            .takeIf { it.isNotBlank() }
            ?.let { "Bearer $it" }

        val response = api.getControlData(endpoint, authorization)

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "Error HTTP ${response.code()}: ${response.message()}"
            )
        }

        val body = response.body()
            ?: throw IllegalStateException(
                "El backend respondió sin contenido."
            )

        if (
            body.has("success") &&
            !body.get("success").isJsonNull &&
            body.get("success").isJsonPrimitive &&
            !body.get("success").asBoolean
        ) {
            throw IllegalStateException(
                body.get("errors")?.toString()
                    ?: "El backend indicó que la operación no fue exitosa."
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
}
