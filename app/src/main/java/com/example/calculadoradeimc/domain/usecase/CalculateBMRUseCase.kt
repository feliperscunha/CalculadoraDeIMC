package com.example.calculadoradeimc.domain.usecase

import com.example.calculadoradeimc.domain.model.Gender

/**
 * Use case for calculating Basal Metabolic Rate (BMR) - Taxa Metabólica Basal (TMB)
 * 
 * Formula: Mifflin-St Jeor Equation (most accurate for modern populations)
 * 
 * For men: BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
 * For women: BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161
 * 
 * BMR represents the number of calories your body burns at rest to maintain vital functions.
 */
class CalculateBMRUseCase {
    
    /**
     * Calculate BMR using Mifflin-St Jeor equation
     * @param weight Weight in kilograms
     * @param height Height in centimeters
     * @param age Age in years
     * @param gender Gender (MALE or FEMALE)
     * @return BMR in calories per day
     */
    operator fun invoke(weight: Double, height: Double, age: Int, gender: Gender): Double {
        val baseBMR = (10 * weight) + (6.25 * height) - (5 * age)
        return when (gender) {
            Gender.MALE -> baseBMR + 5
            Gender.FEMALE -> baseBMR - 161
        }
    }
    
    /**
     * Get interpretation of BMR value
     */
    fun getInterpretation(bmr: Double): String {
        return "Sua Taxa Metabólica Basal (TMB) é de ${String.format("%.0f", bmr)} calorias por dia. " +
               "Este é o número mínimo de calorias que seu corpo precisa em repouso para manter " +
               "funções vitais como respiração, circulação sanguínea e temperatura corporal."
    }
}
