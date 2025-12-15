package com.example.calculadoradeimc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calculadoradeimc.domain.model.ActivityLevel
import com.example.calculadoradeimc.domain.model.Gender
import com.example.calculadoradeimc.domain.model.Measurement
import java.util.Date

/**
 * Room entity for storing measurements in local database
 */
@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val weight: Double,
    val height: Double,
    val age: Int?,
    val gender: String?,
    val waistCircumference: Double?,
    val neckCircumference: Double?,
    val hipCircumference: Double?,
    val activityLevel: String?,
    val bmi: Double,
    val bmiClassification: String,
    val bmr: Double?,
    val bodyFatPercentage: Double?,
    val idealWeight: Double?,
    val dailyCaloricNeeds: Double?
)

/**
 * Extension functions to convert between domain model and entity
 */
fun MeasurementEntity.toDomainModel(): Measurement {
    return Measurement(
        id = id,
        timestamp = Date(timestamp),
        weight = weight,
        height = height,
        age = age,
        gender = gender?.let { Gender.valueOf(it) },
        waistCircumference = waistCircumference,
        neckCircumference = neckCircumference,
        hipCircumference = hipCircumference,
        activityLevel = activityLevel?.let { ActivityLevel.valueOf(it) },
        bmi = bmi,
        bmiClassification = bmiClassification,
        bmr = bmr,
        bodyFatPercentage = bodyFatPercentage,
        idealWeight = idealWeight,
        dailyCaloricNeeds = dailyCaloricNeeds
    )
}

fun Measurement.toEntity(): MeasurementEntity {
    return MeasurementEntity(
        id = id,
        timestamp = timestamp.time,
        weight = weight,
        height = height,
        age = age,
        gender = gender?.name,
        waistCircumference = waistCircumference,
        neckCircumference = neckCircumference,
        hipCircumference = hipCircumference,
        activityLevel = activityLevel?.name,
        bmi = bmi,
        bmiClassification = bmiClassification,
        bmr = bmr,
        bodyFatPercentage = bodyFatPercentage,
        idealWeight = idealWeight,
        dailyCaloricNeeds = dailyCaloricNeeds
    )
}
