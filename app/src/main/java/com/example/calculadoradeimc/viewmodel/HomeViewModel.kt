package com.example.calculadoradeimc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import com.example.calculadoradeimc.ui.feature.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CalculationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onWeightChange(weight: String) {
        _uiState.value = _uiState.value.copy(weight = weight)
        _errorMessage.value = null
    }

    fun onHeightChange(height: String) {
        _uiState.value = _uiState.value.copy(height = height)
        _errorMessage.value = null
    }

    fun onAgeChange(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
        _errorMessage.value = null
    }

    fun onGenderChange(gender: Int) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun onActivityLevelChange(activityLevel: Int) {
        _uiState.value = _uiState.value.copy(activityLevel = activityLevel)
    }

    fun calculate() {
        viewModelScope.launch {
            val weightStr = _uiState.value.weight
            val heightStr = _uiState.value.height
            val ageStr = _uiState.value.age

            if (weightStr.isBlank() || heightStr.isBlank() || ageStr.isBlank()) {
                _errorMessage.value = "Todos os campos devem ser preenchidos"
                return@launch
            }

            val weight = weightStr.toDoubleOrNull()
            val height = heightStr.toDoubleOrNull()
            val age = ageStr.toIntOrNull()

            if (weight != null && height != null && age != null) {
                _errorMessage.value = null
                val imc = com.example.calculadoradeimc.datasource.Calculations.calculateIMC(weight, height)
                val imcClassification = com.example.calculadoradeimc.datasource.Calculations.getIMCClassification(imc)
                val tmb = com.example.calculadoradeimc.datasource.Calculations.calculateTMB(weight, height, age, _uiState.value.gender)
                val idealWeight = com.example.calculadoradeimc.datasource.Calculations.calculateIdealWeight(height, _uiState.value.gender)
                val dailyCaloric = com.example.calculadoradeimc.datasource.Calculations.calculateDailyCaloric(tmb, _uiState.value.activityLevel)

                _uiState.value = _uiState.value.copy(
                    imc = imc,
                    imcClassification = imcClassification,
                    tmb = tmb,
                    idealWeight = idealWeight,
                    dailyCaloric = dailyCaloric
                )

                repository.insert(
                    com.example.calculadoradeimc.data.IMCEntity(
                        weight = weight,
                        height = height,
                        imc = imc,
                        classification = imcClassification,
                        date = java.util.Date(),
                        tmb = tmb,
                        idealWeight = idealWeight,
                        dailyCaloric = dailyCaloric
                    )
                )
            }
        }
    }
}