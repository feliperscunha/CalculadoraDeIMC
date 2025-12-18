package com.example.calculadoradeimc.datasource

import kotlin.math.pow

object Calculations {

    fun calculateIMC(weight: Double, height: Double): Double {
        return weight / (height / 100).pow(2)
    }

    fun getIMCClassification(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 24.9 -> "Peso normal"
            imc < 29.9 -> "Sobrepeso"
            imc < 34.9 -> "Obesidade Grau I"
            imc < 39.9 -> "Obesidade Grau II"
            else -> "Obesidade Grau III"
        }
    }

    fun calculateBMR(weight: Double, height: Double, age: Int, gender: Int): Double {
        return if (gender == 0) { // Male
            88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        } else { // Female
            447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
        }
    }

    fun calculateIdealWeight(height: Double, gender: Int): String {
        val idealWeight = if (gender == 0) { // Male
            50 + 0.91 * (height - 152.4)
        } else { // Female
            45.5 + 0.91 * (height - 152.4)
        }
        return String.format("%.2f", idealWeight)
    }

    fun calculateDailyCaloricNeed(bmr: Double, activityLevel: Int): String {
        val caloricNeed = when (activityLevel) {
            0 -> bmr * 1.2 // Sedentary
            1 -> bmr * 1.375 // Light
            2 -> bmr * 1.55 // Moderate
            else -> bmr * 1.725 // Intense
        }
        return String.format("%.2f", caloricNeed)
    }
}
