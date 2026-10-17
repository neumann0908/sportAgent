package com.sport.agent.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.agent.data.repository.NoStatisticsException
import com.sport.agent.data.repository.StatisticsRepository
import com.sport.agent.domain.model.StatisticsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: StatisticsRepository
) : ViewModel() {

    private val _selectedSport = MutableStateFlow("football")
    val selectedSport: StateFlow<String> = _selectedSport.asStateFlow()

    private val _uiState = MutableStateFlow<StatisticsUiState>(StatisticsUiState.Idle)
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    private var requestJob: Job? = null

    fun selectSport(sport: String) {
        if (sport !in listOf("football", "nba", "mlb")) return
        if (_selectedSport.value == sport) return
        _selectedSport.value = sport
        loadStatistics()
    }

    fun refresh() { loadStatistics() }

    fun loadStatistics() {
        requestJob?.cancel()
        val requestedSport = _selectedSport.value

        requestJob = viewModelScope.launch {
            _uiState.value = StatisticsUiState.Loading
            try {
                val overview = repository.getStatistics(requestedSport)
                if (requestedSport != _selectedSport.value) return@launch

                val hasData = overview.metrics.isNotEmpty() || overview.matches.isNotEmpty() || overview.standings.isNotEmpty()

                _uiState.value = if (hasData) StatisticsUiState.Success(overview) else StatisticsUiState.Empty("El proveedor no ha enviado estadísticas disponibles.")
            } catch (error: CancellationException) {
                throw error
            } catch (error: NoStatisticsException) {
                _uiState.value = StatisticsUiState.Empty(error.message ?: "No hay estadísticas disponibles.")
            } catch (error: Exception) {
                _uiState.value = StatisticsUiState.Error(error.message ?: "No se pudieron cargar las estadísticas.")
            }
        }
    }
}
