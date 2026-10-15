package com.sport.agent.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.sport.agent.data.repository.ControlRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlViewModel @Inject constructor(
    private val repository: ControlRepository
) : ViewModel() {

    private val _data =
        MutableStateFlow<JsonObject?>(null)

    val data = _data.asStateFlow()

    private val _loading =
        MutableStateFlow(false)

    val loading = _loading.asStateFlow()

    private val _error =
        MutableStateFlow<String?>(null)

    val error = _error.asStateFlow()

    fun loadOverview() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _data.value =
                    repository.fetchControlData(
                        "api/v1/control/overview"
                    )
            } catch (exception: Exception) {
                _error.value =
                    exception.message ?: "No se pudo cargar el resumen."
            } finally {
                _loading.value = false
            }
        }
    }
}
