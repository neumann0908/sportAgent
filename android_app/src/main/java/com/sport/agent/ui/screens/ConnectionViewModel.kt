package com.sport.agent.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.agent.data.local.ConnectionConfig
import com.sport.agent.data.local.ConnectionPreferences
import com.sport.agent.data.repository.ControlRepository
import com.sport.agent.domain.model.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val preferences: ConnectionPreferences,
    private val repository: ControlRepository
) : ViewModel() {

    val config: StateFlow<ConnectionConfig> =
        preferences.config.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConnectionConfig()
        )

    private val _connectionState =
        MutableStateFlow<ConnectionState>(
            ConnectionState.Idle
        )

    val connectionState =
        _connectionState.asStateFlow()

    private val _message = MutableStateFlow("")

    val message = _message.asStateFlow()

    fun saveConnection(
        baseUrl: String,
        token: String
    ) {
        viewModelScope.launch {
            try {
                preferences.saveConfig(
                    baseUrl = baseUrl,
                    apiToken = token
                )

                _message.value = "Configuración guardada."
                _connectionState.value = ConnectionState.Idle

            } catch (exception: Exception) {
                _message.value =
                    exception.message ?: "No se pudo guardar la configuración."

                _connectionState.value =
                    ConnectionState.Error(_message.value)
            }
        }
    }

    fun testConnection() {
        viewModelScope.launch {
            _connectionState.value = ConnectionState.Checking
            _message.value = "Comprobando conexión..."

            try {
                repository.testConnection()

                _connectionState.value = ConnectionState.Connected
                _message.value = "El backend respondió correctamente."

            } catch (exception: Exception) {
                val errorMessage =
                    exception.message ?: "Error de conexión."

                _connectionState.value =
                    ConnectionState.Error(errorMessage)

                _message.value = errorMessage
            }
        }
    }
}
