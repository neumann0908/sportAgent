package com.sport.agent.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sport.agent.ui.theme.SportCard
import com.sport.agent.ui.theme.SportGreen
import com.sport.agent.ui.theme.SportTextSecondary

@Composable
fun OverviewScreen(
    viewModel: ControlViewModel = hiltViewModel()
) {
    val data by viewModel.data.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOverview()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "SPORT INTELIGENT 2.0",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Centro de inteligencia deportiva",
                color = SportTextSecondary
            )
        }

        item {
            DashboardCard(
                title = "Estado de la plataforma",
                description = "Resumen de los servicios deportivos."
            )
        }

        item {
            when {
                loading -> CircularProgressIndicator(
                    color = SportGreen
                )

                error != null -> Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "No se pudo cargar el Centro de Control.",
                        color = MaterialTheme.colorScheme.error
                    )

                    Text(
                        text = error ?: "",
                        color = SportTextSecondary
                    )

                    Button(
                        onClick = { viewModel.loadOverview() }
                    ) {
                        Text("Reintentar")
                    }
                }

                data != null -> DashboardCard(
                    title = "Respuesta del backend",
                    description = data.toString()
                )

                else -> Text(
                    text = "Todavía no hay datos disponibles.",
                    color = SportTextSecondary
                )
            }
        }
    }
}

@Composable
fun SportsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Deportes",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            DashboardCard(
                title = "Football",
                description = "Estadísticas, partidos, equipos y competiciones."
            )
        }

        item {
            DashboardCard(
                title = "NBA",
                description = "Equipos, jugadores, partidos y estadísticas."
            )
        }

        item {
            DashboardCard(
                title = "MLB",
                description = "Equipos, jugadores, partidos y estadísticas."
            )
        }
    }
}

@Composable
fun IntelligenceScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Inteligencia deportiva",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            DashboardCard(
                title = "Análisis estadístico",
                description = "Preparado para integrar los módulos de análisis."
            )
        }

        item {
            DashboardCard(
                title = "Comparaciones",
                description = "Preparado para comparar equipos y jugadores."
            )
        }

        item {
            DashboardCard(
                title = "Rankings",
                description = "Preparado para mostrar clasificaciones deportivas."
            )
        }
    }
}

@Composable
fun PredictionsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Predicciones",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            DashboardCard(
                title = "Predicciones Football",
                description = "Módulo preparado para conectarse al motor de Football."
            )
        }

        item {
            DashboardCard(
                title = "Predicciones NBA",
                description = "Módulo preparado para conectarse al motor de NBA."
            )
        }

        item {
            DashboardCard(
                title = "Predicciones MLB",
                description = "Módulo preparado para conectarse al motor de MLB."
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    description: String
) {
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
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = SportTextSecondary
            )
        }
    }
}
