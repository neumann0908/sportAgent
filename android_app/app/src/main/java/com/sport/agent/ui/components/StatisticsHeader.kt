package com.sport.agent.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsHeader(sport: String, updatedAt: String, source: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text("CENTRO DE ESTADÍSTICAS", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(
            text = when (sport.lowercase()) {
                "football" -> "Football"
                "nba" -> "NBA"
                "mlb" -> "MLB"
                else -> sport.uppercase()
            },
            style = MaterialTheme.typography.headlineSmall
        )
        Text("Actualización: $updatedAt", style = MaterialTheme.typography.bodySmall)
        Text("Proveedor: $source", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
