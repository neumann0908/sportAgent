package com.sport.agent.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sport.agent.ui.screens.ControlViewModel
import com.sport.agent.ui.screens.OverviewScreen
import com.sport.agent.ui.screens.SportsScreen
import com.sport.agent.ui.screens.IntelligenceScreen
import com.sport.agent.ui.screens.PredictionsScreen
import com.sport.agent.ui.screens.SettingsScreen
import com.sport.agent.ui.theme.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Overview : Screen("overview", "Overview", Icons.Default.Home)
    object Sports : Screen("sports", "Deportes", Icons.Default.SportsBasketball)
    object Intelligence : Screen("intelligence", "Inteligencia", Icons.Default.Psychology)
    object Predictions : Screen("predictions", "Predicciones", Icons.Default.TrendingUp)
    object Settings : Screen("settings", "Ajustes", Icons.Default.Settings)
}

@Composable
fun MainScreen(viewModel: ControlViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Overview) }

    val items = listOf(
        Screen.Overview,
        Screen.Sports,
        Screen.Intelligence,
        Screen.Predictions,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = SportCard,
                contentColor = SportTextSecondary
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SportGreen,
                            selectedTextColor = SportGreen,
                            unselectedIconColor = SportTextSecondary,
                            unselectedTextColor = SportTextSecondary,
                            indicatorColor = SportCard
                        ),
                        onClick = { currentScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (currentScreen) {
                is Screen.Overview -> OverviewScreen(viewModel = viewModel)
                is Screen.Sports -> SportsScreen()
                is Screen.Intelligence -> IntelligenceScreen()
                is Screen.Predictions -> PredictionsScreen()
                is Screen.Settings -> SettingsScreen()
            }
        }
    }
}
