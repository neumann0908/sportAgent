package com.sport.agent.domain.model

data class StatisticsMetric(
    val title: String,
    val value: String,
    val description: String = ""
)

data class StatisticsMatch(
    val homeTeam: String,
    val awayTeam: String,
    val score: String = "No disponible",
    val status: String = "No disponible",
    val date: String = ""
)

data class StatisticsStanding(
    val position: String,
    val team: String,
    val played: String,
    val points: String
)

data class StatisticsOverview(
    val sport: String,
    val updatedAt: String,
    val source: String,
    val metrics: List<StatisticsMetric>,
    val matches: List<StatisticsMatch>,
    val standings: List<StatisticsStanding>
)
