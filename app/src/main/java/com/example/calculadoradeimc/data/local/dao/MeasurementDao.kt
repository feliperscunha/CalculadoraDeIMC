package com.example.calculadoradeimc.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.calculadoradeimc.data.local.entity.MeasurementEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Measurement operations
 */
@Dao
interface MeasurementDao {
    
    /**
     * Get all measurements ordered by timestamp (newest first)
     */
    @Query("SELECT * FROM measurements ORDER BY timestamp DESC")
    fun getAllMeasurements(): Flow<List<MeasurementEntity>>
    
    /**
     * Get a specific measurement by ID
     */
    @Query("SELECT * FROM measurements WHERE id = :id")
    suspend fun getMeasurementById(id: Long): MeasurementEntity?
    
    /**
     * Get measurements within a date range
     */
    @Query("SELECT * FROM measurements WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getMeasurementsInRange(startTime: Long, endTime: Long): Flow<List<MeasurementEntity>>
    
    /**
     * Get the most recent measurement
     */
    @Query("SELECT * FROM measurements ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestMeasurement(): MeasurementEntity?
    
    /**
     * Insert a new measurement
     */
    @Insert
    suspend fun insertMeasurement(measurement: MeasurementEntity): Long
    
    /**
     * Update an existing measurement
     */
    @Update
    suspend fun updateMeasurement(measurement: MeasurementEntity)
    
    /**
     * Delete a measurement
     */
    @Delete
    suspend fun deleteMeasurement(measurement: MeasurementEntity)
    
    /**
     * Delete all measurements
     */
    @Query("DELETE FROM measurements")
    suspend fun deleteAllMeasurements()
    
    /**
     * Get count of all measurements
     */
    @Query("SELECT COUNT(*) FROM measurements")
    fun getMeasurementCount(): Flow<Int>
}
