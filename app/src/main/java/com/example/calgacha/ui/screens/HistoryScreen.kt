package com.example.calgacha.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calgacha.data.remote.model.Chicken
import com.example.calgacha.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: MainViewModel, navController: NavController, onItemClick: (Int) -> Unit) {
    val chickens = viewModel.chickens.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Historial") }) }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            LazyColumn(contentPadding = PaddingValues(8.dp)) {
                items(chickens.value) { chicken ->
                    HistoryItemRow(
                        chicken = chicken,
                        onClick = { onItemClick(chicken.id) },
                        onDelete = { viewModel.deleteChicken(chicken) }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun HistoryItemRow(chicken: Chicken, onClick: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Nombre gallina : " + chicken.nombre, style = MaterialTheme.typography.titleMedium)
            Text("Numero (ID): " + chicken.id, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Edad: " + chicken.edad.toString(), style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            Text("Raza: " + chicken.raza, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            Text("Descripcion breve de la gallina: " + chicken.descripcion, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Chicken")
        }
    }
}
