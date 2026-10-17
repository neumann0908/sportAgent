package com.sport.agent.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sport.agent.domain.model.StatisticsUiState
import com.sport.agent.ui.components.StatisticsHeader
import com.sport.agent.ui.components.StatisticsLoading
import com.sport.agent.ui.components.StatisticsMatchCard
import com.sport.agent.ui.components.StatisticsMetricCard
import com.sport.agent.ui.components.StatisticsTable

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {

    val selectedSport by viewModel.selectedSport.collectAsState()

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStatistics()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        contentPadding = PaddingValues(
            vertical = 18.dp
        ),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Text(
                text = "SPORT AGENT",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {

            Text(
                text = "Estadísticas deportivas",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                SportButton(
                    label = "Football",
                    selected = selectedSport == "football",
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.selectSport("football")
                }

                SportButton(
                    label = "NBA",
                    selected = selectedSport == "nba",
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.selectSport("nba")
                }

                SportButton(
                    label = "MLB",
                    selected = selectedSport == "mlb",
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.selectSport("mlb")
                }
            }
        }

        item {

            Button(
                onClick = {
                    viewModel.refresh()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Actualizar estadísticas")
            }
        }

        when (val state = uiState) {

            StatisticsUiState.Idle -> {

                item {

                    Text(
                        text = "Selecciona un deporte para consultar sus datos."
                    )
                }
            }

            StatisticsUiState.Loading -> {

                item {
                    StatisticsLoading()
                }
            }

            is StatisticsUiState.Error -> {

                item {

                    MessageCard(
                        title = "Error de conexión",
                        message = state.message
                    )
                }
            }

            is StatisticsUiState.Empty -> {

                item {

                    MessageCard(
                        title = "Sin estadísticas disponibles",
                        message = state.message
                    )
                }
            }

            is StatisticsUiState.Success -> {

                val overview = state.overview

                item {

                    StatisticsHeader(
                        sport = overview.sport,
                        updatedAt = overview.updatedAt,
                        source = overview.source
                    )
                }

                if (overview.metrics.isNotEmpty()) {

                    item {

                        Text(
                            text = "Resumen estadístico",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    items(overview.metrics) { metric ->

                        StatisticsMetricCard(
                            metric = metric
                        )
                    }
                }

                if (overview.matches.isNotEmpty()) {

                    item {

                        Text(
                            text = "Partidos",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    items(overview.matches) { match ->

                        StatisticsMatchCard(
                            match = match
                        )
                    }
                }

                if (overview.standings.isNotEmpty()) {

                    item {

                        Text(
                            text = "Clasificación",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    item {

                        StatisticsTable(
                            standings = overview.standings
                        )
                    }
                }

                if (
                    overview.metrics.isEmpty() &&
                    overview.matches.isEmpty() &&
                    overview.standings.isEmpty()
                ) {

                    item {

                        MessageCard(
                            title = "Datos incompletos",
                            message = "El servidor respondió, pero no entregó estadísticas reconocibles."
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SportButton(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    if (selected) {

        Button(
            onClick = onClick,
            modifier = modifier
        ) {

            Text(label)
        }

    } else {

        OutlinedButton(
            onClick = onClick,
            modifier = modifier
        ) {

            Text(label)
        }
    }
}

@Composable
private fun MessageCard(
    title: String,
    message: String
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

