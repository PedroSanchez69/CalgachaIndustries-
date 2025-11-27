package com.example.calgacha.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.calgacha.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(itemId: Int, viewModel: MainViewModel, onBack: () -> Unit) {
    LaunchedEffect(key1 = itemId) {
        viewModel.getChickenById(id = itemId)
    }

    val chicken by viewModel.selectedChicken.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onDetailScreenLeave()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Gallina") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (chicken != null) {
                // ⬇️ Imagen grande
                if (chicken!!.imagenUri != null) {
                    AsyncImage(
                        model = chicken!!.imagenUri,
                        contentDescription = "Imagen de ${chicken!!.nombre}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Información General",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Nombre:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = chicken!!.nombre,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Edad:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "${chicken!!.edad} meses",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Raza:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = chicken!!.raza,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Descripción:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = chicken!!.descripcion,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                Text(
                    "Gallina no encontrada",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}