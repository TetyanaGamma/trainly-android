package com.example.trainly.presentation.client_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainly.domain.models.Client
import com.example.trainly.domain.repo.ClientRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClientProfileViewModel(
    private val repository: ClientRepository,
    private val clientId: String // Передаем ID при создании
) : ViewModel() {

    private val _client = MutableStateFlow<Client?>(null)
    val client: StateFlow<Client?> = _client.asStateFlow()

    init {
        loadClient()
    }

    private fun loadClient() {
        repository.getClientById(clientId)
            .onEach { fetchedClient ->
                _client.value = fetchedClient
            }
            .launchIn(viewModelScope)
    }

    fun deleteClient() {
        viewModelScope.launch {
            _client.value?.let {
                repository.deleteClient(it)
            }
        }
    }

    fun updateClient(name: String, phone: String, workouts: Int) {
        viewModelScope.launch {
            val currentClient = _client.value ?: return@launch

            // Создаем копию объекта с новыми данными
            val updatedClient = currentClient.copy(
                name = name,
                phone = phone,
                totalWorkoutsPaid = workouts
                // workoutsUsed оставляем прежним, чтобы не сбить прогресс
            )

            repository.updateClient(updatedClient)
        }
    }
}