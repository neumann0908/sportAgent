package com.sport.agent.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sport.agent.ui.components.ConnectionStatusCard

@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel = hiltViewModel()
) {
    val config by viewModel.config.collectAsState()
    val connectionState by viewModel.connectionState.collectAsState()
    val message by viewModel.message.collectAsState()

    var urlInput by remember(config.baseUrl) { 
        mutableStateOf(config.baseUrl.ifEmpty { "http://127.0.0.1:8000/" }) 
    }
    var tokenInput by remember(config.apiToken) { 
        mutableStateOf(config.apiToken) 
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "SportAgent - SportInteligent 2.0",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Conexión con Servidor FastAPI",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = urlInput,
            onValueChange = { urlInput = it },
            label = { Text("URL del Backend (ej: http://127.0.0.1:8000/)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tokenInput,
            onValueChange = { tokenInput = it },
            label = { Text("Token de Acceso (Opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.saveConnection(urlInput, tokenInput)
                viewModel.testConnection()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Probar Endpoint /overview")
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Componente visual limpio integrado en pantalla
        ConnectionStatusCard(
            state = connectionState,
            message = message.ifEmpty { "Configura la IP de Termux y presiona el botón." }
        )
    }
}
