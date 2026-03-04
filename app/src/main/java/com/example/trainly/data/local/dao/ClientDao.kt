package com.example.trainly.data.local.dao

import androidx.room.*
import com.example.trainly.data.local.entities.ClientEntity
import com.example.trainly.data.local.entities.ClientWithWorkouts
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients ORDER BY name ASC")
    fun getAllClients(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE id = :id")
    fun getClientById(id: String): Flow<ClientEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: ClientEntity)

    @Delete
    suspend fun deleteClient(client: ClientEntity)

    @Transaction
    @Query("SELECT * FROM clients WHERE id = :clientId")
    fun getClientWithWorkouts(clientId: String): Flow<ClientWithWorkouts>
}