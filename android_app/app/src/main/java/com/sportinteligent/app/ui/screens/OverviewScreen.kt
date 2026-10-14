package com.sportinteligent.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sportinteligent.app.ui.components.MetricCard
import com.sportinteligent.app.ui.theme.*

@Composable
fun OverviewScreen(
    viewModel: ControlViewModel,
    modifier: Modifier = Modifier
) {
    val systemState by viewModel.systemOverview.collectAsState()
    val predictionState by viewModel.predictionOverview.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "SPORT INTELIGENT 2.0",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Centro de Control & Telemetría",
                color = TextSecondary,
                fontSize = 14.sp
            )
        }

        item {
            when (val state = systemState) {
                is UiState.Loading -> CircularProgressIndicator(color = GreenPrimary)
                is UiState.Error -> MetricCard("Servidor", "Error", state.message, RedError)
                is UiState.Success -> {
                    MetricCard(
                        title = "Estado del Sistema",
                        value = state.data.serverStatus.uppercase(),
                        subtitle = "Entorno: ${state.data.environment} | Ver: ${state.data.systemVersion}",
                        accentColor = GreenPrimary
                    )
                }
            }
        }

        item {
            when (val state = predictionState) {
                is UiState.Loading -> {}
                is UiState.Error -> {}
                is UiState.Success -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        MetricCard(
                            title = "Total Pronósticos Generados",
                            value = state.data.totalPredictions.toString(),
                            subtitle = "Evaluados: ${state.data.evaluatedResults}",
                            accentColor = BlueAccent
                        )
                        MetricCard(
                            title = "Precisión General del Modelo",
                            value = state.data.performanceMetrics.accuracyRate,
                            subtitle = "MAE: ${state.data.performanceMetrics.mae} | RMSE: ${state.data.performanceMetrics.rmse}",
                            accentColor = GreenSecondary
                        )
                    }
                }
            }
        }
    }
}
