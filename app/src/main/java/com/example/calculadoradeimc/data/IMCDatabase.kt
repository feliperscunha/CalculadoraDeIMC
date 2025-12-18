package com.example.calculadoradeimc.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [IMCEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class IMCDatabase : RoomDatabase() {
    abstract fun imcDao(): IMCDao
}
