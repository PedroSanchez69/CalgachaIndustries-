package com.example.calgacha.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calgacha.R
import com.example.calgacha.data.remote.model.Chicken
import com.example.calgacha.navigation.Routes
import com.example.calgacha.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val chickens = viewModel.chickens.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Lista de gallinas", style= MaterialTheme.typography.titleLarge) })

        if (chickens.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.gallina_fachera), contentDescription = "Welcome Image")
                Spacer(modifier = Modifier.height(16.dp))
                Text("¡Bienvenido a Calgacha Industries!.", style= MaterialTheme.typography.titleMedium)
                Text("Agrega tu primera gallina insana", style= MaterialTheme.typography.bodyMedium)
                Text("(Sin servicio para agregar fotos por el momento)", style=MaterialTheme.typography.bodySmall)
            }
        } else {
            LazyColumn {
                items(chickens) { chicken ->
                    ChickensRow(
                        chicken = chicken,
                        onClick = { navController.navigate("${Routes.DETAIL}/${chicken.id}") },
                        onDelete = { viewModel.deleteChicken(chicken) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChickensRow(chicken: Chicken, onClick: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(chicken.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(chicken.descripcion, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar gallina")
        }
    }
}
