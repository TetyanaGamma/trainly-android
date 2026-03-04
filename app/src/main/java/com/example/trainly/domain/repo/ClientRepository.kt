package com.example.trainly.domain.repo

import com.example.trainly.domain.models.Client
import com.example.trainly.domain.models.ClientAnamnesis
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun getActiveClients(): Flow<List<Client>>
     fun getClientById(id: String): Flow<Client?>
    suspend fun insertClient(client: Client)
    suspend fun updateClient(client: Client)
    suspend fun deleteClient(client: Client)

     //methods for filling client's form
     fun getAnamnesis(clientId: String): Flow<ClientAnamnesis?>
    suspend fun saveAnamnesis(anamnesis: ClientAnamnesis)
}