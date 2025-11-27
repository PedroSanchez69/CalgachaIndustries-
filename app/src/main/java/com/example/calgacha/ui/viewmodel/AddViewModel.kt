package com.example.calgacha.ui.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calgacha.data.remote.model.Chicken
import com.example.calgacha.data.repository.chickenRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: chickenRepository) : ViewModel() {

    var name = mutableStateOf("")
    var nameError = mutableStateOf<String?>(null)
    var age = mutableStateOf("")
    var ageError = mutableStateOf<String?>(null)
    var breed = mutableStateOf("")
    var breedError = mutableStateOf<String?>(null)
    var description = mutableStateOf("")
    var descriptionError = mutableStateOf<String?>(null)

    var imageBitmap = mutableStateOf<Bitmap?>(null)
    var imageUri = mutableStateOf<Uri?>(null)

    fun onNameChange(value: String) {
        name.value = value
        nameError.value = when {
            value.isBlank() -> "El nombre no puede estar vacío"
            value.length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
    }

    fun onAgeChange(value: String) {
        age.value = value
        ageError.value = when {
            value.isBlank() -> "La edad no puede estar vacía"
            value.toIntOrNull() == null -> "La edad debe ser un número válido"
            (value.toIntOrNull() ?: 0) <= 0 -> "La edad debe ser mayor a 0"
            else -> null
        }
    }

    fun onBreedChange(value: String) {
        breed.value = value
        breedError.value = when {
            value.isBlank() -> "La raza no puede estar vacía"
            else -> null
        }
    }

    fun onDescriptionChange(value: String) {
        description.value = value
        descriptionError.value = when {
            value.isBlank() -> "La descripción no puede estar vacía"
            else -> null
        }
    }

    fun onImageChange(uri: Uri?, bitmap: Bitmap?) {
        imageUri.value = uri
        imageBitmap.value = bitmap
    }

    fun addChicken(onChickenAdded: () -> Unit) {
        if (!isFormValid()) return

        val imagePathToSave = imageUri.value?.toString()

        val newChicken = Chicken(
            // id is now null, so the server will generate it
            id = null,
            nombre = name.value,
            edad = age.value.toIntOrNull() ?: 0,
            raza = breed.value,
            descripcion = description.value,
            imagenUri = imagePathToSave
        )

        viewModelScope.launch {
            repository.insertChicken(newChicken)
            onChickenAdded()
        }
    }

    fun isFormValid(): Boolean {
        return nameError.value == null &&
                ageError.value == null &&
                breedError.value == null &&
                descriptionError.value == null &&
                name.value.isNotBlank() &&
                age.value.isNotBlank() &&
                breed.value.isNotBlank() &&
                description.value.isNotBlank()
    }
}
