package com.example.calculadoradeimc.domain.usecase

import com.example.calculadoradeimc.domain.model.Gender

/**
 * Use case for calculating ideal weight
 * 
 * Formula: Devine Formula (1974)
 * 
 * For men (height >= 152.4 cm): Ideal Weight = 50 + 2.3 × (height in inches - 60)
 * For women (height >= 152.4 cm): Ideal Weight = 45.5 + 2.3 × (height in inches - 60)
 * 
 * For heights below 152.4 cm (5 feet):
 * Men: 50 kg, Women: 45.5 kg (base values)
 * 
 * This formula is widely used in medical settings and provides a reasonable
 * estimate of healthy body weight based on height and gender.
 */
class CalculateIdealWeightUseCase {
    
    /**
     * Calculate ideal weight using Devine formula
     * @param height Height in centimeters
     * @param gender Gender (MALE or FEMALE)
     * @return Ideal weight in kilograms
     */
    operator fun invoke(height: Double, gender: Gender): Double {
        val heightInInches = height / 2.54
        
        val baseWeight = when (gender) {
            Gender.MALE -> 50.0
            Gender.FEMALE -> 45.5
        }
        
        return if (heightInInches > 60) {
            baseWeight + 2.3 * (heightInInches - 60)
        } else {
            baseWeight
        }
    }
    
    /**
     * Get interpretation comparing current weight to ideal weight
     */
    fun getInterpretation(currentWeight: Double, idealWeight: Double): String {
        val difference = currentWeight - idealWeight
        val percentDiff = (difference / idealWeight) * 100
        
        return when {
            percentDiff < -10 -> {
                String.format(
                    "Seu peso ideal é %.1f kg. Você está %.1f kg abaixo do peso ideal (%.1f%%). " +
                    "Considere ganhar peso de forma saudável.",
                    idealWeight, -difference, -percentDiff
                )
            }
            percentDiff > 10 -> {
                String.format(
                    "Seu peso ideal é %.1f kg. Você está %.1f kg acima do peso ideal (%.1f%%). " +
                    "Considere um plano de emagrecimento saudável.",
                    idealWeight, difference, percentDiff
                )
            }
            else -> {
                String.format(
                    "Seu peso ideal é %.1f kg. Você está muito próximo do seu peso ideal! " +
                    "Continue mantendo seus hábitos saudáveis.",
                    idealWeight
                )
            }
        }
    }
}
