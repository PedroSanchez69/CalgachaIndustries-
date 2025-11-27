package com.example.calgacha.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val chickens by viewModel.chickens.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Calgacha Industries", style = MaterialTheme.typography.titleLarge) })

        // The image is now a fixed element at the top.
        Image(
            painter = painterResource(id = R.drawable.gallina_fachera),
            contentDescription = "Welcome Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (chickens.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("¡Bienvenido!", style = MaterialTheme.typography.titleMedium)
                Text("Agrega tu primera gallina para empezar.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            // The LazyColumn now correctly takes up the remaining space and scrolls.
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(chickens) { chicken ->
                    // Use .let for a safe, non-nullable ID for navigation
                    chicken.id?.let { chickenId ->
                        ChickensRow(
                            chicken = chicken,
                            onClick = { navController.navigate(Routes.detailRoute(chickenId)) },
                            onDelete = { viewModel.deleteChicken(chicken) }
                        )
                    }
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
