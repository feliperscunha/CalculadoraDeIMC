package com.example.calculadoradeimc.data.repository

import com.example.calculadoradeimc.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for measurement operations
 */
interface MeasurementRepository {
    
    /**
     * Get all measurements ordered by date (newest first)
     */
    fun getAllMeasurements(): Flow<List<Measurement>>
    
    /**
     * Get a specific measurement by ID
     */
    suspend fun getMeasurementById(id: Long): Measurement?
    
    /**
     * Get the most recent measurement
     */
    suspend fun getLatestMeasurement(): Measurement?
    
    /**
     * Save a new measurement
     */
    suspend fun saveMeasurement(measurement: Measurement): Long
    
    /**
     * Update an existing measurement
     */
    suspend fun updateMeasurement(measurement: Measurement)
    
    /**
     * Delete a measurement
     */
    suspend fun deleteMeasurement(measurement: Measurement)
    
    /**
     * Delete all measurements
     */
    suspend fun deleteAllMeasurements()
    
    /**
     * Get count of measurements
     */
    fun getMeasurementCount(): Flow<Int>
}
