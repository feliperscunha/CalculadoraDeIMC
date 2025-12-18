package com.example.calculadoradeimc.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IMCDao {
    @Insert
    suspend fun insert(imcEntity: IMCEntity)

    @Query("SELECT * FROM imc_table ORDER BY date DESC")
    fun getAll(): Flow<List<IMCEntity>>

    @Query("SELECT * FROM imc_table WHERE id = :id")
    suspend fun getById(id: Int): IMCEntity?
}
