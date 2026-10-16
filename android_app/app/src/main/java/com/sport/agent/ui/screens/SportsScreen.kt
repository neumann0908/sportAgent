package com.sport.agent.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sport.agent.domain.model.SportResource
import com.sport.agent.ui.components.SportSelectorCard

@Composable
fun SportsScreen(
    viewModel: SportsViewModel = hiltViewModel()
) {
    val selectedSport by viewModel.selectedSport.collectAsState()
    val data by viewModel.data.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSelectedSport()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Deportes",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Selecciona el motor deportivo que deseas consultar.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Selector de deportes en fila horizontal
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            SportResource.values().forEach { sport ->
                SportSelectorCard(
                    sport = sport,
                    selected = selectedSport == sport,
                    onClick = { viewModel.selectSport(sport) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Datos de ${selectedSport.displayName}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Botones de actualización manual
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.refresh() },
                enabled = !loading
            ) {
                Text("Actualizar")
            }

            OutlinedButton(
                onClick = { viewModel.loadSelectedSport() },
                enabled = !loading
            ) {
                Text("Volver a consultar")
            }
        }

        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)

        // Estado de la consulta y visualización de datos
        when {
            loading -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Consultando el backend en Termux...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            error != null -> {
                Text(
                    text = "No se pudieron cargar los datos: $error",
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Comprueba que el servidor FastAPI esté corriendo en 127.0.0.1:8000.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            data != null -> {
                Text(
                    text = "Respuesta recibida del backend:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = data.toString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
            else -> {
                Text(
                    text = "Todavía no hay datos. Pulsa Actualizar.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
