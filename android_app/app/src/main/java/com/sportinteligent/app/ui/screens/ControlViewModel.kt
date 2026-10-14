package com.sportinteligent.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sportinteligent.app.data.remote.*
import com.sportinteligent.app.di.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ControlViewModel : ViewModel() {

    private val api = NetworkModule.provideControlApi()

    private val _systemOverview = MutableStateFlow<UiState<SystemOverviewData>>(UiState.Loading)
    val systemOverview: StateFlow<UiState<SystemOverviewData>> = _systemOverview

    private val _syncOverview = MutableStateFlow<UiState<SyncOverviewData>>(UiState.Loading)
    val syncOverview: StateFlow<UiState<SyncOverviewData>> = _syncOverview

    private val _predictionOverview = MutableStateFlow<UiState<PredictionOverviewData>>(UiState.Loading)
    val predictionOverview: StateFlow<UiState<PredictionOverviewData>> = _predictionOverview

    private val _intelligenceOverview = MutableStateFlow<UiState<IntelligenceOverviewData>>(UiState.Loading)
    val intelligenceOverview: StateFlow<UiState<IntelligenceOverviewData>> = _intelligenceOverview

    init {
        fetchAllData()
    }

    fun fetchAllData() {
        viewModelScope.launch {
            fetchSystemOverview()
            fetchSyncOverview()
            fetchPredictionOverview()
            fetchIntelligenceOverview()
        }
    }

    private suspend fun fetchSystemOverview() {
        _systemOverview.value = UiState.Loading
        try {
            val response = api.getSystemOverview()
            if (response.success && response.data != null) {
                _systemOverview.value = UiState.Success(response.data)
            } else {
                _systemOverview.value = UiState.Error(response.errors.firstOrNull() ?: "Error desconocido")
            }
        } catch (e: Exception) {
            _systemOverview.value = UiState.Error(e.message ?: "Error de red")
        }
    }

    private suspend fun fetchSyncOverview() {
        _syncOverview.value = UiState.Loading
        try {
            val response = api.getSyncOverview()
            if (response.success && response.data != null) {
                _syncOverview.value = UiState.Success(response.data)
            } else {
                _syncOverview.value = UiState.Error(response.errors.firstOrNull() ?: "Error desconocido")
            }
        } catch (e: Exception) {
            _syncOverview.value = UiState.Error(e.message ?: "Error de red")
        }
    }

    private suspend fun fetchPredictionOverview() {
        _predictionOverview.value = UiState.Loading
        try {
            val response = api.getPredictionOverview()
            if (response.success && response.data != null) {
                _predictionOverview.value = UiState.Success(response.data)
            } else {
                _predictionOverview.value = UiState.Error(response.errors.firstOrNull() ?: "Error desconocido")
            }
        } catch (e: Exception) {
            _predictionOverview.value = UiState.Error(e.message ?: "Error de red")
        }
    }

    private suspend fun fetchIntelligenceOverview() {
        _intelligenceOverview.value = UiState.Loading
        try {
            val response = api.getIntelligenceOverview()
            if (response.success && response.data != null) {
                _intelligenceOverview.value = UiState.Success(response.data)
            } else {
                _intelligenceOverview.value = UiState.Error(response.errors.firstOrNull() ?: "Error desconocido")
            }
        } catch (e: Exception) {
            _intelligenceOverview.value = UiState.Error(e.message ?: "Error de red")
        }
    }
}
