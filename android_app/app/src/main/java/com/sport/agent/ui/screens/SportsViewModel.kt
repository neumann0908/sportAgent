package com.sport.agent.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.sport.agent.data.repository.SportsRepository
import com.sport.agent.domain.model.SportResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    private val _selectedSport = MutableStateFlow(SportResource.FOOTBALL)
    val selectedSport = _selectedSport.asStateFlow()

    private val _data = MutableStateFlow<JsonObject?>(null)
    val data = _data.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        loadSelectedSport()
    }

    fun selectSport(sport: SportResource) {
        if (_selectedSport.value == sport) return
        _selectedSport.value = sport
        _data.value = null
        _error.value = null
        loadSelectedSport()
    }

    fun loadSelectedSport() {
        val sport = _selectedSport.value
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _data.value = repository.getOverview(sport)
            } catch (e: Exception) {
                _data.value = null
                _error.value = e.message ?: "No se pudieron cargar los datos."
            } finally {
                _loading.value = false
            }
        }
    }

    fun refresh() {
        loadSelectedSport()
    }
}
