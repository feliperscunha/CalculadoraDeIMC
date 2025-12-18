package com.example.calculadoradeimc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import com.example.calculadoradeimc.ui.feature.HomeUiState
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

    fun onWeightChange(weight: String) {
        _uiState.value = _uiState.value.copy(weight = weight)
    }

    fun onHeightChange(height: String) {
        _uiState.value = _uiState.value.copy(height = height)
    }

    fun onAgeChange(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun onGenderChange(gender: Int) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun onActivityLevelChange(activityLevel: Int) {
        _uiState.value = _uiState.value.copy(activityLevel = activityLevel)
    }

    fun calculate() {
        viewModelScope.launch {
            val weight = _uiState.value.weight.toDoubleOrNull()
            val height = _uiState.value.height.toDoubleOrNull()
            val age = _uiState.value.age.toIntOrNull()

            if (weight != null && height != null && age != null) {
                val imc = com.example.calculadoradeimc.datasource.Calculations.calculateIMC(weight, height)
                val imcClassification = com.example.calculadoradeimc.datasource.Calculations.getIMCClassification(imc)
                val bmr = com.example.calculadoradeimc.datasource.Calculations.calculateBMR(weight, height, age, _uiState.value.gender)
                val idealWeight = com.example.calculadoradeimc.datasource.Calculations.calculateIdealWeight(height, _uiState.value.gender)
                val dailyCaloricNeed = com.example.calculadoradeimc.datasource.Calculations.calculateDailyCaloricNeed(bmr, _uiState.value.activityLevel)

                _uiState.value = _uiState.value.copy(
                    imc = imc,
                    imcClassification = imcClassification,
                    bmr = bmr,
                    idealWeight = idealWeight,
                    dailyCaloricNeed = dailyCaloricNeed
                )

                repository.insert(
                    com.example.calculadoradeimc.data.IMCEntity(
                        weight = weight,
                        height = height,
                        imc = imc,
                        classification = imcClassification,
                        date = java.util.Date(),
                        bmr = bmr,
                        idealWeight = idealWeight,
                        dailyCaloricNeed = dailyCaloricNeed
                    )
                )
            }
        }
    }
}