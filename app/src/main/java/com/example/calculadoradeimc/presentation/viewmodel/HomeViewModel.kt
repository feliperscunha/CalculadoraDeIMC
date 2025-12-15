package com.example.calculadoradeimc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.data.repository.MeasurementRepository
import com.example.calculadoradeimc.domain.model.ActivityLevel
import com.example.calculadoradeimc.domain.model.Gender
import com.example.calculadoradeimc.domain.model.Measurement
import com.example.calculadoradeimc.domain.model.ValidationResult
import com.example.calculadoradeimc.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel for the home screen where users input measurements
 */
class HomeViewModel(
    private val repository: MeasurementRepository
) : ViewModel() {
    
    // Use cases - in a production app, these would be injected via DI
    private val calculateBMI = CalculateBMIUseCase()
    private val calculateBMR = CalculateBMRUseCase()
    private val calculateBodyFat = CalculateBodyFatUseCase()
    private val calculateIdealWeight = CalculateIdealWeightUseCase()
    private val calculateDailyCaloricNeeds = CalculateDailyCaloricNeedsUseCase()
    private val validateInput = ValidateInputUseCase()
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    fun onWeightChanged(weight: String) {
        _uiState.value = _uiState.value.copy(
            weight = weight,
            weightError = null
        )
    }
    
    fun onHeightChanged(height: String) {
        _uiState.value = _uiState.value.copy(
            height = height,
            heightError = null
        )
    }
    
    fun onAgeChanged(age: String) {
        _uiState.value = _uiState.value.copy(
            age = age,
            ageError = null
        )
    }
    
    fun onGenderChanged(gender: Gender?) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }
    
    fun onWaistChanged(waist: String) {
        _uiState.value = _uiState.value.copy(waistCircumference = waist)
    }
    
    fun onNeckChanged(neck: String) {
        _uiState.value = _uiState.value.copy(neckCircumference = neck)
    }
    
    fun onHipChanged(hip: String) {
        _uiState.value = _uiState.value.copy(hipCircumference = hip)
    }
    
    fun onActivityLevelChanged(activityLevel: ActivityLevel?) {
        _uiState.value = _uiState.value.copy(activityLevel = activityLevel)
    }
    
    fun calculateMetrics() {
        val state = _uiState.value
        
        // Validate required fields
        val weightValidation = validateInput.validateWeight(state.weight)
        val heightValidation = validateInput.validateHeight(state.height)
        
        if (weightValidation is ValidationResult.Invalid || heightValidation is ValidationResult.Invalid) {
            _uiState.value = state.copy(
                weightError = (weightValidation as? ValidationResult.Invalid)?.message,
                heightError = (heightValidation as? ValidationResult.Invalid)?.message,
                calculationResult = null
            )
            return
        }
        
        // Parse validated inputs
        val weight = state.weight.replace(",", ".").toDouble()
        val height = state.height.toDouble()
        
        // Calculate BMI (always required)
        val (bmi, bmiClassification) = calculateBMI(weight, height)
        
        // Calculate optional metrics if data is available
        var bmr: Double? = null
        var bodyFat: Double? = null
        var idealWeight: Double? = null
        var dailyCalories: Double? = null
        
        // BMR and related calculations require age and gender
        if (state.age.isNotEmpty() && state.gender != null) {
            val ageValidation = validateInput.validateAge(state.age)
            if (ageValidation is ValidationResult.Valid) {
                val age = state.age.toInt()
                bmr = calculateBMR(weight, height, age, state.gender)
                idealWeight = calculateIdealWeight(height, state.gender)
                
                // Daily caloric needs require activity level
                if (state.activityLevel != null) {
                    dailyCalories = calculateDailyCaloricNeeds(bmr, state.activityLevel)
                }
                
                // Body fat requires circumferences
                if (state.waistCircumference.isNotEmpty() && state.neckCircumference.isNotEmpty()) {
                    val waist = state.waistCircumference.replace(",", ".").toDoubleOrNull()
                    val neck = state.neckCircumference.replace(",", ".").toDoubleOrNull()
                    val hip = if (state.gender == Gender.FEMALE && state.hipCircumference.isNotEmpty()) {
                        state.hipCircumference.replace(",", ".").toDoubleOrNull()
                    } else null
                    
                    if (waist != null && neck != null && (state.gender == Gender.MALE || hip != null)) {
                        try {
                            bodyFat = calculateBodyFat(waist, neck, height, hip, state.gender)
                        } catch (e: Exception) {
                            // Body fat calculation failed, continue without it
                        }
                    }
                }
            }
        }
        
        val measurement = Measurement(
            timestamp = Date(),
            weight = weight,
            height = height,
            age = state.age.toIntOrNull(),
            gender = state.gender,
            waistCircumference = state.waistCircumference.replace(",", ".").toDoubleOrNull(),
            neckCircumference = state.neckCircumference.replace(",", ".").toDoubleOrNull(),
            hipCircumference = state.hipCircumference.replace(",", ".").toDoubleOrNull(),
            activityLevel = state.activityLevel,
            bmi = bmi,
            bmiClassification = bmiClassification,
            bmr = bmr,
            bodyFatPercentage = bodyFat,
            idealWeight = idealWeight,
            dailyCaloricNeeds = dailyCalories
        )
        
        _uiState.value = state.copy(
            calculationResult = measurement,
            weightError = null,
            heightError = null,
            ageError = null
        )
        
        // Save measurement to database
        viewModelScope.launch {
            try {
                repository.saveMeasurement(measurement)
            } catch (e: Exception) {
                // Handle error (could add error state to UI)
            }
        }
    }
    
    fun clearResults() {
        _uiState.value = HomeUiState()
    }
    
    // Expose interpretation methods for UI
    fun getBMIInterpretation(bmi: Double): String = calculateBMI.getInterpretation(bmi)
    
    fun getBMRInterpretation(bmr: Double): String = calculateBMR.getInterpretation(bmr)
    
    fun getBodyFatInterpretation(bodyFat: Double, gender: Gender): String = 
        calculateBodyFat.getInterpretation(bodyFat, gender)
    
    fun getBodyFatClassification(bodyFat: Double, gender: Gender): String = 
        calculateBodyFat.classify(bodyFat, gender)
    
    fun getIdealWeightInterpretation(currentWeight: Double, idealWeight: Double): String = 
        calculateIdealWeight.getInterpretation(currentWeight, idealWeight)
    
    fun getDailyCaloricInterpretation(dailyCalories: Double): String = 
        calculateDailyCaloricNeeds.getInterpretation(dailyCalories)
}

data class HomeUiState(
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val gender: Gender? = null,
    val waistCircumference: String = "",
    val neckCircumference: String = "",
    val hipCircumference: String = "",
    val activityLevel: ActivityLevel? = null,
    val weightError: String? = null,
    val heightError: String? = null,
    val ageError: String? = null,
    val calculationResult: Measurement? = null
)
