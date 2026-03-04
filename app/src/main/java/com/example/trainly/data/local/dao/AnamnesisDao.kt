package com.example.trainly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trainly.data.local.entities.AnamnesisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnamnesisDao {
    @Query("SELECT * FROM client_anamnesis WHERE clientId = :clientId")
    fun getAnamnesisByClientId(clientId: String): Flow<AnamnesisEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAnamnesis(anamnesis: AnamnesisEntity)
}