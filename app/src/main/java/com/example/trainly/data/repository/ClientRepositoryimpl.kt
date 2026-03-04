package com.example.trainly.data.repository

import com.example.trainly.data.local.dao.AnamnesisDao
import com.example.trainly.data.local.dao.ClientDao
import com.example.trainly.data.mapper.toDomain
import com.example.trainly.data.mapper.toEntity
import com.example.trainly.domain.models.Client
import com.example.trainly.domain.models.ClientAnamnesis
import com.example.trainly.domain.repo.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ClientRepositoryImpl(
    private val clientDao: ClientDao,
    private val anamnesisDao: AnamnesisDao
) : ClientRepository {

    // Transform Flow of Entities into Flow of Domain Models
    override fun getActiveClients(): Flow<List<Client>> =
        clientDao.getAllClients().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getClientById(id: String): Flow<Client?> {
        return clientDao.getClientById(id).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun insertClient(client: Client) {
        clientDao.insertClient(client.toEntity())
    }

    override suspend fun updateClient(client: Client) {
        clientDao.insertClient(client.toEntity()) // Room's OnConflictStrategy.REPLACE handles updates
    }

    override suspend fun deleteClient(client: Client) {
        clientDao.deleteClient(client.toEntity())
    }

    override fun getAnamnesis(clientId: String): Flow<ClientAnamnesis?> {
        return anamnesisDao.getAnamnesisByClientId(clientId).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun saveAnamnesis(anamnesis: ClientAnamnesis) {
        anamnesisDao.insertOrUpdateAnamnesis(anamnesis.toEntity())
    }


}