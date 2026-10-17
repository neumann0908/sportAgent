package com.sport.agent.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sport.agent.domain.model.StatisticsStanding

@Composable
fun StatisticsTable(standings: List<StatisticsStanding>) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Pos.", modifier = Modifier.weight(0.6f), style = MaterialTheme.typography.labelMedium)
            Text("Equipo", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelMedium)
            Text("PJ", modifier = Modifier.weight(0.6f), style = MaterialTheme.typography.labelMedium)
            Text("Pts", modifier = Modifier.weight(0.6f), style = MaterialTheme.typography.labelMedium)
        }
        HorizontalDivider()
        standings.forEach { standing ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(standing.position, modifier = Modifier.weight(0.6f))
                Text(standing.team, modifier = Modifier.weight(2f), maxLines = 2)
                Text(standing.played, modifier = Modifier.weight(0.6f))
                Text(standing.points, modifier = Modifier.weight(0.6f))
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}
