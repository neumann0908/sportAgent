package com.sport.agent.domain.model

sealed interface ConnectionState {

    data object Idle : ConnectionState

    data object Checking : ConnectionState

    data object Connected : ConnectionState

    data class Error(
        val message: String
    ) : ConnectionState
}
