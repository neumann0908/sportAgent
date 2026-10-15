package com.sport.agent.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sport.agent.domain.model.ConnectionState
import com.sport.agent.ui.components.ConnectionStatusCard

@Composable
fun SettingsScreen(
    viewModel: ConnectionViewModel = hiltViewModel()
) {
    val config by viewModel.config.collectAsState()
    val connectionState by viewModel.connectionState.collectAsState()
    val message by viewModel.message.collectAsState()

    var baseUrl by remember(config.baseUrl) {
        mutableStateOf(config.baseUrl)
    }

    var token by remember(config.apiToken) {
        mutableStateOf(config.apiToken)
    }

    LaunchedEffect(Unit) {
        // La configuración se carga desde DataStore.
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ajustes",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Configuración del servidor",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = baseUrl,
            onValueChange = { baseUrl = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Dirección del backend") },
            placeholder = {
                Text("http://127.0.0.1:8000/")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = token,
            onValueChange = { token = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Token API (opcional)") },
            singleLine = true
        )

        Button(
            onClick = {
                viewModel.saveConnection(
                    baseUrl = baseUrl,
                    token = token
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar configuración")
        }

        Button(
            onClick = {
                viewModel.testConnection()
            },
            enabled = connectionState !is ConnectionState.Checking,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (connectionState is ConnectionState.Checking) {
                CircularProgressIndicator()
            } else {
                Text("Probar conexión")
            }
        }

        ConnectionStatusCard(
            state = connectionState,
            message = message
        )

        Text(
            text = "La conexión local requiere que el backend esté ejecutándose en Termux.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
