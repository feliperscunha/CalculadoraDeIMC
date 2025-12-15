package com.example.calculadoradeimc.domain.usecase

import com.example.calculadoradeimc.domain.model.Gender
import kotlin.math.log10

/**
 * Use case for calculating body fat percentage
 * 
 * Formula: U.S. Navy Method
 * 
 * For men: Body Fat % = 86.010 × log10(waist - neck) - 70.041 × log10(height) + 36.76
 * For women: Body Fat % = 163.205 × log10(waist + hip - neck) - 97.684 × log10(height) - 78.387
 * 
 * All measurements should be in centimeters.
 * 
 * Body fat classifications:
 * Men:
 * - Essential fat: 2-5%
 * - Athletes: 6-13%
 * - Fitness: 14-17%
 * - Average: 18-24%
 * - Obese: 25%+
 * 
 * Women:
 * - Essential fat: 10-13%
 * - Athletes: 14-20%
 * - Fitness: 21-24%
 * - Average: 25-31%
 * - Obese: 32%+
 */
class CalculateBodyFatUseCase {
    
    /**
     * Calculate body fat percentage using U.S. Navy method
     * @param waist Waist circumference in centimeters
     * @param neck Neck circumference in centimeters
     * @param height Height in centimeters
     * @param hip Hip circumference in centimeters (required for women, null for men)
     * @param gender Gender (MALE or FEMALE)
     * @return Body fat percentage
     */
    operator fun invoke(
        waist: Double,
        neck: Double,
        height: Double,
        hip: Double?,
        gender: Gender
    ): Double {
        return when (gender) {
            Gender.MALE -> {
                86.010 * log10(waist - neck) - 70.041 * log10(height) + 36.76
            }
            Gender.FEMALE -> {
                require(hip != null) { "Hip measurement is required for women" }
                163.205 * log10(waist + hip - neck) - 97.684 * log10(height) - 78.387
            }
        }
    }
    
    /**
     * Classify body fat percentage
     */
    fun classify(bodyFat: Double, gender: Gender): String {
        return when (gender) {
            Gender.MALE -> when {
                bodyFat < 6 -> "Gordura Essencial"
                bodyFat < 14 -> "Atleta"
                bodyFat < 18 -> "Fitness"
                bodyFat < 25 -> "Média"
                else -> "Obeso"
            }
            Gender.FEMALE -> when {
                bodyFat < 14 -> "Gordura Essencial"
                bodyFat < 21 -> "Atleta"
                bodyFat < 25 -> "Fitness"
                bodyFat < 32 -> "Média"
                else -> "Obeso"
            }
        }
    }
    
    /**
     * Get detailed interpretation
     */
    fun getInterpretation(bodyFat: Double, gender: Gender): String {
        val classification = classify(bodyFat, gender)
        return "Seu percentual de gordura corporal estimado é ${String.format("%.1f", bodyFat)}% ($classification). " +
               when {
                   bodyFat < (if (gender == Gender.MALE) 6 else 14) -> 
                       "Este nível é muito baixo e pode ser prejudicial à saúde. Consulte um médico."
                   bodyFat > (if (gender == Gender.MALE) 25 else 32) -> 
                       "Este nível é elevado. Considere adotar hábitos mais saudáveis."
                   else -> 
                       "Este é um nível saudável. Continue mantendo seus hábitos!"
               }
    }
}
