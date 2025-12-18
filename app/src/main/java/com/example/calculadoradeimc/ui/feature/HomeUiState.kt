package com.example.calculadoradeimc.ui.feature

data class HomeUiState(
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val gender: Int = 0, // 0 for male, 1 for female
    val activityLevel: Int = 0, // 0 for sedentary, 1 for light, 2 for moderate, 3 for intense
    val imc: Double = 0.0,
    val imcClassification: String = "",
    val bmr: Double = 0.0,
    val idealWeight: String = "",
    val dailyCaloricNeed: String = ""
)