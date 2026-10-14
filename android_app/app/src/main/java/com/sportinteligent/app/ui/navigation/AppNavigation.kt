package com.sportinteligent.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sportinteligent.app.ui.screens.ControlViewModel
import com.sportinteligent.app.ui.screens.OverviewScreen
import com.sportinteligent.app.ui.theme.*

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
                containerColor = BackgroundSecondary,
                contentColor = TextPrimary
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GreenPrimary,
                            selectedTextColor = GreenPrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = CardBackground
                        ),
                        onClick = { currentScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (currentScreen) {
            is Screen.Overview -> OverviewScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
            else -> {
                // Pantalla temporal para las demás secciones
                Surface(
                    modifier = Modifier.padding(innerPadding),
                    color = BackgroundPrimary
                ) {
                    Text(
                        text = "Sección ${currentScreen.title} en desarrollo",
                        color = TextSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
