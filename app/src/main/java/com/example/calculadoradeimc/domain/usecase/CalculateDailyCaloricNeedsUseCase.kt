package com.example.calculadoradeimc.domain.usecase

import com.example.calculadoradeimc.domain.model.ActivityLevel

/**
 * Use case for calculating daily caloric needs
 * 
 * Formula: Total Daily Energy Expenditure (TDEE)
 * TDEE = BMR × Activity Factor
 * 
 * Activity Factors:
 * - Sedentary (little or no exercise): 1.2
 * - Lightly active (light exercise 1-3 days/week): 1.375
 * - Moderately active (moderate exercise 3-5 days/week): 1.55
 * - Very active (intense exercise 6-7 days/week): 1.725
 * - Extra active (very intense exercise, physical job): 1.9
 * 
 * This represents the total number of calories you should consume per day
 * to maintain your current weight, considering your activity level.
 */
class CalculateDailyCaloricNeedsUseCase {
    
    /**
     * Calculate daily caloric needs based on BMR and activity level
     * @param bmr Basal Metabolic Rate in calories per day
     * @param activityLevel Activity level
     * @return Daily caloric needs in calories
     */
    operator fun invoke(bmr: Double, activityLevel: ActivityLevel): Double {
        return bmr * activityLevel.factor
    }
    
    /**
     * Get interpretation with caloric targets for different goals
     */
    fun getInterpretation(dailyCalories: Double): String {
        val maintenance = String.format("%.0f", dailyCalories)
        val weightLoss = String.format("%.0f", dailyCalories - 500)
        val weightGain = String.format("%.0f", dailyCalories + 500)
        
        return """
            Suas necessidades calóricas diárias são de $maintenance calorias.
            
            Recomendações:
            • Para manter o peso: $maintenance calorias/dia
            • Para perder peso de forma saudável: $weightLoss calorias/dia (déficit de 500 cal)
            • Para ganhar peso: $weightGain calorias/dia (superávit de 500 cal)
            
            Nota: Consulte um nutricionista para um plano alimentar personalizado.
        """.trimIndent()
    }
    
    /**
     * Get caloric target for weight loss
     */
    fun getWeightLossTarget(dailyCalories: Double): Double {
        return dailyCalories - 500 // Safe deficit of 500 calories per day
    }
    
    /**
     * Get caloric target for weight gain
     */
    fun getWeightGainTarget(dailyCalories: Double): Double {
        return dailyCalories + 500 // Safe surplus of 500 calories per day
    }
}
