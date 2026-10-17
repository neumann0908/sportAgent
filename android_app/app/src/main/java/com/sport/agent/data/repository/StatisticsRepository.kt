package com.sport.agent.data.repository

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.sport.agent.data.local.ConnectionPreferences
import com.sport.agent.data.remote.ControlApi
import com.sport.agent.domain.model.StatisticsMatch
import com.sport.agent.domain.model.StatisticsMetric
import com.sport.agent.domain.model.StatisticsOverview
import com.sport.agent.domain.model.StatisticsStanding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepository @Inject constructor(
    private val api: ControlApi,
    private val preferences: ConnectionPreferences
) {

    suspend fun getStatistics(
        sport: String
    ): StatisticsOverview {

        require(
            sport in listOf("football", "nba", "mlb")
        ) {
            "El deporte solicitado no es válido."
        }

        val config = preferences.getConfig()

        val baseUrl = config.baseUrl
            .trim()
            .trimEnd('/')

        require(baseUrl.isNotBlank()) {
            "Primero debes configurar la dirección del backend."
        }

        val token = config.apiToken
            ?.trim()
            ?.takeIf { it.isNotBlank() }

        val authorization = token?.let {
            "Bearer $it"
        }

        val endpoint = "$baseUrl/api/v1/sports/$sport/overview"

        val response = api.getControlData(
            endpoint,
            authorization
        )

        if (!response.isSuccessful) {
            val detail = when (response.code()) {
                401 -> "Autenticación incorrecta."
                403 -> "No tienes permiso para consultar estos datos."
                404 -> "El endpoint solicitado no existe."
                429 -> "Se ha alcanzado el límite de consultas."
                in 500..599 -> "El servidor tiene un problema."
                else -> "Error HTTP ${response.code()}."
            }

            throw StatisticsRequestException(detail)
        }

        val body = response.body()
            ?: throw StatisticsRequestException(
                "El backend devolvió una respuesta vacía."
            )

        if (
            body.has("success") &&
            body.get("success").isJsonPrimitive &&
            !body.get("success").asBoolean
        ) {
            throw StatisticsRequestException(
                "El backend indicó que la consulta no fue exitosa."
            )
        }

        val data = if (
            body.has("data") &&
            body.get("data").isJsonObject
        ) {
            body.getAsJsonObject("data")
        } else {
            body
        }

        if (data.entrySet().isEmpty()) {
            throw NoStatisticsException(
                "El servidor no tiene estadísticas disponibles."
            )
        }

        val metadata = if (
            body.has("metadata") &&
            body.get("metadata").isJsonObject
        ) {
            body.getAsJsonObject("metadata")
        } else {
            JsonObject()
        }

        return parseOverview(
            data = data,
            metadata = metadata,
            requestedSport = sport
        )
    }

    private fun parseOverview(
        data: JsonObject,
        metadata: JsonObject,
        requestedSport: String
    ): StatisticsOverview {

        val metrics = parseMetrics(data)
        val matches = parseMatches(data)
        val standings = parseStandings(data)

        val updatedAt = metadata.stringValue(
            "updated_at",
            "last_updated"
        ).ifBlank {
            data.stringValue(
                "updated_at",
                "last_updated"
            )
        }

        val source = metadata.stringValue(
            "source",
            "provider"
        ).ifBlank {
            data.stringValue(
                "source",
                "provider"
            )
        }

        return StatisticsOverview(
            sport = data.stringValue("sport").ifBlank { requestedSport },
            updatedAt = updatedAt.ifBlank { "No informado" },
            source = source.ifBlank { "No informado" },
            metrics = metrics,
            matches = matches,
            standings = standings
        )
    }

    private fun parseMetrics(data: JsonObject): List<StatisticsMetric> {
        val result = mutableListOf<StatisticsMetric>()
        val array = findArray(data, "metrics", "statistics")

        array?.forEach { element ->
            if (!element.isJsonObject) return@forEach
            val item = element.asJsonObject
            val title = item.stringValue("title", "name", "label")
            val value = item.stringValue("value", "total", "count")

            if (title.isNotBlank() && value.isNotBlank()) {
                result.add(
                    StatisticsMetric(
                        title = title,
                        value = value,
                        description = item.stringValue("description", "subtitle")
                    )
                )
            }
        }
        return result
    }

    private fun parseMatches(data: JsonObject): List<StatisticsMatch> {
        val result = mutableListOf<StatisticsMatch>()
        val mainArray = findArray(data, "matches", "games")

        val arrays = if (mainArray != null) {
            listOf(mainArray)
        } else {
            listOfNotNull(
                findArray(data, "recent_matches"),
                findArray(data, "upcoming_matches")
            )
        }

        arrays.forEach { array ->
            array.forEach matchLoop@ { element ->
                if (!element.isJsonObject) {
                    return@matchLoop
                }

                val item = element.asJsonObject

                val home = item.stringValue(
                    "home_team",
                    "homeTeam",
                    "home"
                )

                val away = item.stringValue(
                    "away_team",
                    "awayTeam",
                    "away"
                )

                if (home.isNotBlank() && away.isNotBlank()) {

                    result.add(
                        StatisticsMatch(
                            homeTeam = home,
                            awayTeam = away,
                            score = item.stringValue(
                                "score",
                                "result",
                                "display_score"
                            ).ifBlank {
                                "No disponible"
                            },
                            status = item.stringValue(
                                "status",
                                "state"
                            ).ifBlank {
                                "No disponible"
                            },
                            date = item.stringValue(
                                "date",
                                "match_date",
                                "start_time"
                            )
                        )
                    )
                }
            }
        }
        return result
    }

    private fun parseStandings(data: JsonObject): List<StatisticsStanding> {
        val result = mutableListOf<StatisticsStanding>()
        val array = findArray(data, "standings", "table", "rankings")

        array?.forEach { element ->
            if (!element.isJsonObject) return@forEach
            val item = element.asJsonObject
            val team = item.stringValue("team", "team_name", "name")

            if (team.isNotBlank()) {
                result.add(
                    StatisticsStanding(
                        position = item.stringValue("position", "rank").ifBlank { "—" },
                        team = team,
                        played = item.stringValue("played", "matches_played", "games").ifBlank { "—" },
                        points = item.stringValue("points", "pts").ifBlank { "—" }
                    )
                )
            }
        }
        return result
    }

    private fun findArray(json: JsonObject, vararg keys: String): JsonArray? {
        for (key in keys) {
            val value = json.get(key)
            if (value != null && value.isJsonArray) {
                return value.asJsonArray
            }
        }
        return null
    }

    private fun JsonObject.stringValue(vararg keys: String): String {
        for (key in keys) {
            val value: JsonElement = get(key) ?: continue
            if (!value.isJsonNull && value.isJsonPrimitive) {
                return value.asString
            }
        }
        return ""
    }
}

class NoStatisticsException(message: String) : Exception(message)
class StatisticsRequestException(message: String) : Exception(message)
