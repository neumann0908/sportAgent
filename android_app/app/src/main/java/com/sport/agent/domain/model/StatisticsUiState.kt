package com.sport.agent.domain.model

sealed interface StatisticsUiState {
    data object Idle : StatisticsUiState
    data object Loading : StatisticsUiState
    data class Success(val overview: StatisticsOverview) : StatisticsUiState
    data class Empty(val message: String) : StatisticsUiState
    data class Error(val message: String) : StatisticsUiState
}
