package com.example.calculadoradeimc.domain.model

import java.util.Date

/**
 * Domain model representing a complete health measurement
 */
data class Measurement(
    val id: Long = 0,
    val timestamp: Date = Date(),
    val weight: Double,
    val height: Double,
    val age: Int? = null,
    val gender: Gender? = null,
    val waistCircumference: Double? = null,
    val neckCircumference: Double? = null,
    val hipCircumference: Double? = null,
    val activityLevel: ActivityLevel? = null,
    val bmi: Double,
    val bmiClassification: String,
    val bmr: Double? = null,
    val bodyFatPercentage: Double? = null,
    val idealWeight: Double? = null,
    val dailyCaloricNeeds: Double? = null
)

enum class Gender {
    MALE, FEMALE
}

enum class ActivityLevel(val factor: Double, val description: String) {
    SEDENTARY(1.2, "Sedentário (pouco ou nenhum exercício)"),
    LIGHTLY_ACTIVE(1.375, "Levemente ativo (exercício leve 1-3 dias/semana)"),
    MODERATELY_ACTIVE(1.55, "Moderadamente ativo (exercício moderado 3-5 dias/semana)"),
    VERY_ACTIVE(1.725, "Muito ativo (exercício intenso 6-7 dias/semana)"),
    EXTRA_ACTIVE(1.9, "Extremamente ativo (exercício muito intenso, trabalho físico)")
}
