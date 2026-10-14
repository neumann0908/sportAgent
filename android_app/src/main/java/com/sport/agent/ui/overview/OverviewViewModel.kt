package com.sport.agent.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.sport.agent.data.local.ConnectionPreferences
import com.sport.agent.data.repository.ControlRepository
import com.sport.agent.domain.model.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val repository: ControlRepository,
    private val preferences: ConnectionPreferences
) : ViewModel() {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Idle)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _overviewData = MutableStateFlow<JsonObject?>(null)
    val overviewData: StateFlow<JsonObject?> = _overviewData.asStateFlow()

    fun testConnection(baseUrl: String, token: String) {
        viewModelScope.launch {
            _connectionState.value = ConnectionState.Checking
            try {
                preferences.saveConfig(baseUrl, token)
                val result = repository.testConnection()
                _overviewData.value = result
                _connectionState.value = ConnectionState.Connected
            } catch (e: Exception) {
                _connectionState.value = ConnectionState.Error(e.localizedMessage ?: "Error desconocido al conectar")
            }
        }
    }
}
