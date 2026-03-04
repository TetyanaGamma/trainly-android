package com.example.trainly.presentation.client_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainly.domain.models.Client
import com.example.trainly.domain.repo.ClientRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClientListViewModel(
    private val repository: ClientRepository
) : ViewModel() {

    // Using StateFlow to hold the list of clients for the UI
    private val _uiState = MutableStateFlow<List<Client>>(emptyList())
    val uiState: StateFlow<List<Client>> = _uiState.asStateFlow()

    init {
        loadClients()
    }

    private fun loadClients() {
        viewModelScope.launch {
            repository.getActiveClients()
                .onEach { clients ->
                    _uiState.value = clients
                }
                .launchIn(viewModelScope)
        }
    }

    fun addClient(name: String,
                  phone: String,
                  totalWorkouts: Int) {
        viewModelScope.launch {
            val newClient = Client(
                name = name,
                phone = phone,
                photoUri = null,
                totalWorkoutsPaid = totalWorkouts,
                workoutsUsed = 0, // Начинаем с нуля
                isActive = true
            )
            repository.insertClient(newClient)
        }
    }

    // Function to handle client deletion
    fun deleteClient(client: Client) {
        viewModelScope.launch {
            repository.deleteClient(client)
        }
    }
}