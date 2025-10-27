package com.example.calgacha.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calgacha.data.repository.UserPreferencesRepository
import com.example.calgacha.data.repository.chickenRepository
import com.example.calgacha.ui.viewmodel.AddViewModel
import com.example.calgacha.ui.viewmodel.LoginViewModel
import com.example.calgacha.ui.viewmodel.MainViewModel

class ViewModelFactory(
    private val chickenRepository: chickenRepository?,
    private val userPreferencesRepository: UserPreferencesRepository?
) : ViewModelProvider.Factory {

    constructor(repository: chickenRepository) : this(repository, null)
    constructor(repository: UserPreferencesRepository) : this(null, repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(chickenRepository!!) as T
            }
            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(chickenRepository!!) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userPreferencesRepository!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
