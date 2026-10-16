package com.sport.agent.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OverviewScreen() {
    PlaceholderScreen(
        "Resumen",
        "Vista general del estado de la aplicación y actividad reciente."
    )
}

@Composable
fun IntelligenceScreen() {
    PlaceholderScreen(
        "Inteligencia",
        "Módulo de análisis inteligente y predicciones automatizadas."
    )
}

@Composable
fun PredictionsScreen() {
    PlaceholderScreen(
        "Predicciones",
        "Listado y seguimiento de las predicciones deportivas activas."
    )
}

@Composable
fun PlaceholderScreen(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
