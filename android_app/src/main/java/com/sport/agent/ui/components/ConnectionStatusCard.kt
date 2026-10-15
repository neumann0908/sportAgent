package com.sport.agent.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sport.agent.domain.model.ConnectionState
import com.sport.agent.ui.theme.SportCard
import com.sport.agent.ui.theme.SportGreen
import com.sport.agent.ui.theme.SportRed
import com.sport.agent.ui.theme.SportTextSecondary
import com.sport.agent.ui.theme.SportYellow

@Composable
fun ConnectionStatusCard(
    state: ConnectionState,
    message: String
) {
    val status = when (state) {
        ConnectionState.Idle -> "Inactivo"
        ConnectionState.Checking -> "Comprobando..."
        ConnectionState.Connected -> "Conectado"
        is ConnectionState.Error -> "Error de conexión"
    }

    val statusColor = when (state) {
        ConnectionState.Idle -> SportTextSecondary
        ConnectionState.Checking -> SportYellow
        ConnectionState.Connected -> SportGreen
        is ConnectionState.Error -> SportRed
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SportCard
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "ESTADO DEL BACKEND",
                style = MaterialTheme.typography.labelMedium,
                color = SportTextSecondary
            )

            Text(
                text = status,
                style = MaterialTheme.typography.titleLarge,
                color = statusColor
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = SportTextSecondary
            )
        }
    }
}
