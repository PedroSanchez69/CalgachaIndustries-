package com.example.calgacha.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calgacha.data.remote.RetrofitInstance
import com.example.calgacha.data.remote.model.Chicken

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val _chickens = MutableStateFlow<List<Chicken>>(emptyList())
    val chickens = _chickens.asStateFlow()

    fun loadChickens() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getChickens()
            if (response.isSuccessful) {
                _chickens.value = response.body() ?: emptyList()
            }
        }
    }

    fun addChicken(chicken: Chicken, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.createChicken(chicken)
            if (response.isSuccessful) {
                onSuccess()
            }
        }
    }

    fun deleteChicken(chicken: Chicken) {
        viewModelScope.launch {
            chicken.id?.let {
                RetrofitInstance.api.deleteChicken(it)
                loadChickens()
            }
        }
    }
}
