package com.example.calgacha.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calgacha.data.remote.model.Chicken
import com.example.calgacha.data.repository.chickenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val chickenRepository: chickenRepository) : ViewModel() {

    val chickens: StateFlow<List<Chicken>> = chickenRepository.getChickens()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedChicken = MutableStateFlow<Chicken?>(null)
    val selectedChicken: StateFlow<Chicken?> = _selectedChicken.asStateFlow()

    init {
        // Actualiza las gallinas cuando el viewmodel se crea
        viewModelScope.launch {
            chickenRepository.refreshChickens()
        }
    }

    fun deleteChicken(chicken: Chicken) {
        viewModelScope.launch {
            chickenRepository.deleteChicken(chicken)
        }
    }

    fun getChickenById(id: Int) {
        viewModelScope.launch {
            _selectedChicken.value = if (id != -1) chickenRepository.getChickenById(id) else null
        }
    }

    fun onDetailScreenLeave() {
        _selectedChicken.value = null
    }
}
