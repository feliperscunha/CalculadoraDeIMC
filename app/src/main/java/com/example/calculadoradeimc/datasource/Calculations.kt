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

    fun calculateTMB(weight: Double, height: Double, age: Int, gender: Int): Double {
        return if (gender == 0) {
            5 + (10 * weight) + (6.25 * height) - (5 * age)
        } else {
            (10 * weight) + (6.25 * height) - (5 * age) - 161
        }
    }

    fun calculateIdealWeight(height: Double, gender: Int): String {
        val idealWeight = if (gender == 0) { // Male
            50 + 0.91 * (height - 152.4)
        } else {
            45.5 + 0.91 * (height - 152.4)
        }
        return String.format("%.2f", idealWeight)
    }

    fun calculateDailyCaloric(tmb: Double, activityLevel: Int): String {
        val caloric = when (activityLevel) {
            0 -> tmb * 1.2
            1 -> tmb * 1.375
            2 -> tmb * 1.55
            else -> tmb * 1.725
        }
        return String.format("%.2f", caloric)
    }
}
