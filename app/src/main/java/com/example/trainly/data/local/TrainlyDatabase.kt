package com.example.trainly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trainly.data.local.dao.AnamnesisDao
import com.example.trainly.data.local.dao.ClientDao
import com.example.trainly.data.local.entities.AnamnesisEntity
import com.example.trainly.data.local.entities.ClientEntity
import com.example.trainly.data.local.entities.WorkoutEntity

@Database(entities = [ClientEntity::class,
    WorkoutEntity::class,
    AnamnesisEntity::class],
    version = 3, exportSchema = false)

abstract class TrainlyDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun anamnesisDao(): AnamnesisDao
}