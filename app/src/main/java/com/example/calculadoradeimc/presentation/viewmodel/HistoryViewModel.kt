package com.example.calculadoradeimc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.data.repository.MeasurementRepository
import com.example.calculadoradeimc.domain.model.Measurement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the history screen showing all measurements
 */
class HistoryViewModel(
    private val repository: MeasurementRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()
    
    init {
        loadMeasurements()
    }
    
    private fun loadMeasurements() {
        viewModelScope.launch {
            try {
                repository.getAllMeasurements().collect { measurements ->
                    _uiState.value = if (measurements.isEmpty()) {
                        HistoryUiState.Empty
                    } else {
                        HistoryUiState.Success(measurements)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = HistoryUiState.Error(e.message ?: "Erro ao carregar histórico")
            }
        }
    }
    
    fun deleteMeasurement(measurement: Measurement) {
        viewModelScope.launch {
            try {
                repository.deleteMeasurement(measurement)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun deleteAllMeasurements() {
        viewModelScope.launch {
            try {
                repository.deleteAllMeasurements()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

sealed class HistoryUiState {
    object Loading : HistoryUiState()
    object Empty : HistoryUiState()
    data class Success(val measurements: List<Measurement>) : HistoryUiState()
    data class Error(val message: String) : HistoryUiState()
}
