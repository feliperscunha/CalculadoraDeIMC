package com.example.calculadoradeimc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.data.repository.MeasurementRepository
import com.example.calculadoradeimc.domain.model.Measurement
import com.example.calculadoradeimc.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the detail screen showing a specific measurement
 */
class DetailViewModel(
    private val repository: MeasurementRepository,
    private val calculateBMI: CalculateBMIUseCase = CalculateBMIUseCase(),
    private val calculateBMR: CalculateBMRUseCase = CalculateBMRUseCase(),
    private val calculateBodyFat: CalculateBodyFatUseCase = CalculateBodyFatUseCase(),
    private val calculateIdealWeight: CalculateIdealWeightUseCase = CalculateIdealWeightUseCase(),
    private val calculateDailyCaloricNeeds: CalculateDailyCaloricNeedsUseCase = CalculateDailyCaloricNeedsUseCase()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    
    fun loadMeasurement(measurementId: Long) {
        viewModelScope.launch {
            try {
                val measurement = repository.getMeasurementById(measurementId)
                _uiState.value = if (measurement != null) {
                    DetailUiState.Success(
                        measurement = measurement,
                        bmiInterpretation = calculateBMI.getInterpretation(measurement.bmi),
                        bmrInterpretation = measurement.bmr?.let { calculateBMR.getInterpretation(it) },
                        bodyFatInterpretation = if (measurement.bodyFatPercentage != null && measurement.gender != null) {
                            calculateBodyFat.getInterpretation(measurement.bodyFatPercentage, measurement.gender)
                        } else null,
                        idealWeightInterpretation = measurement.idealWeight?.let { 
                            calculateIdealWeight.getInterpretation(measurement.weight, it)
                        },
                        dailyCaloricInterpretation = measurement.dailyCaloricNeeds?.let {
                            calculateDailyCaloricNeeds.getInterpretation(it)
                        }
                    )
                } else {
                    DetailUiState.Error("Medição não encontrada")
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Erro ao carregar medição")
            }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(
        val measurement: Measurement,
        val bmiInterpretation: String,
        val bmrInterpretation: String?,
        val bodyFatInterpretation: String?,
        val idealWeightInterpretation: String?,
        val dailyCaloricInterpretation: String?
    ) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
