package com.sport.agent.domain.model

enum class SportResource(
    val apiKey: String,
    val displayName: String,
    val description: String
) {
    FOOTBALL(
        apiKey = "football",
        displayName = "Football",
        description = "Competiciones, equipos y partidos"
    ),
    NBA(
        apiKey = "nba",
        displayName = "NBA",
        description = "Equipos, jugadores y partidos"
    ),
    MLB(
        apiKey = "mlb",
        displayName = "MLB",
        description = "Equipos, jugadores y estadísticas de béisbol"
    )
}
