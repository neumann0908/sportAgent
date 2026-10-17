package com.sport.agent.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sport.agent.domain.model.StatisticsMatch

@Composable
fun StatisticsMatchCard(match: StatisticsMatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(match.status, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                Text(match.date.ifBlank { "Fecha no disponible" }, style = MaterialTheme.typography.labelSmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(match.homeTeam, style = MaterialTheme.typography.titleMedium)
                    Text(match.awayTeam, style = MaterialTheme.typography.titleMedium)
                }
                Text(match.score, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
