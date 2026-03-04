package com.example.trainly.presentation.client_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainly.domain.models.ClientAnamnesis
import com.example.trainly.domain.repo.ClientRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClientFormViewModel(
    private val repository: ClientRepository,
    private val clientId: String
) : ViewModel() {

    // Single source of truth for the UI state
    private val _uiState = MutableStateFlow(ClientAnamnesis(clientId = clientId))
    val uiState: StateFlow<ClientAnamnesis> = _uiState.asStateFlow()

    init {
        loadAnamnesis()
    }

    private fun loadAnamnesis() {
        viewModelScope.launch {
            repository.getAnamnesis(clientId)
                .take(1)  // Fetch initial data from the database once
                .collect { savedAnamnesis ->
                    savedAnamnesis?.let {
                        _uiState.value = it
                    }
                }
        }
    }
 // Universal function to update any field in the anamnesis model.
 // Uses a lambda to modify the current state via the .copy() method.
    fun updateFields(update: (ClientAnamnesis) -> ClientAnamnesis) {
        _uiState.value = update(_uiState.value)
    }

    // Persists the current state of the form to the local database.
    fun saveAnamnesis() {
        viewModelScope.launch {
            repository.saveAnamnesis(_uiState.value)
        }
    }
}