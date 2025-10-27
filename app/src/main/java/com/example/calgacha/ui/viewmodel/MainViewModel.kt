package com.example.calgacha.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calgacha.data.remote.model.Chicken
import com.example.calgacha.data.repository.chickenRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val chickenRepository: chickenRepository) : ViewModel() {

    val chickens: StateFlow<List<Chicken>> = chickenRepository.getChickens()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteChicken(chicken: Chicken) {
        viewModelScope.launch {
            chickenRepository.deleteChicken(chicken)
        }
    }

    suspend fun getChickenById(id: Int): Chicken? {
        return chickenRepository.getChickenById(id)
    }
}
