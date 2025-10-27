package com.example.calgacha.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calgacha.ui.viewmodel.AddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(vm: AddViewModel, navController: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Nueva gallina") })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = vm.name.value,
                onValueChange = { vm.onNameChange(it) },
                label = { Text("Nombre") },
                isError = vm.nameError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            vm.nameError.value?.let {
                Text(text = it)
            }

            OutlinedTextField(
                value = vm.age.value,
                onValueChange = { vm.onAgeChange(it) },
                label = { Text("Edad") },
                isError = vm.ageError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            vm.ageError.value?.let {
                Text(text = it)
            }

            OutlinedTextField(
                value = vm.breed.value,
                onValueChange = { vm.onBreedChange(it) },
                label = { Text("Raza") },
                isError = vm.breedError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            vm.breedError.value?.let {
                Text(text = it)
            }

            OutlinedTextField(
                value = vm.description.value,
                onValueChange = { vm.onDescriptionChange(it) },
                label = { Text("Descripción") },
                isError = vm.descriptionError.value != null,
                modifier = Modifier.fillMaxWidth()
            )
            vm.descriptionError.value?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    vm.addChicken {
                        navController.popBackStack()
                    }
                },
                enabled = vm.isFormValid(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
