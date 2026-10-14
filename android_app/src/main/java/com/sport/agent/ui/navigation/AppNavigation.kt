package com.sport.agent.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sport.agent.ui.overview.OverviewScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Overview : Screen("overview", "Overview", Icons.Default.Home)
    object Deportes : Screen("deportes", "Deportes", Icons.Default.PlayArrow)
    object Inteligencia : Screen("inteligencia", "Inteligencia", Icons.Default.Star)
    object Predicciones : Screen("predicciones", "Predicciones", Icons.Default.Info)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val items = listOf(
        Screen.Overview,
        Screen.Deportes,
        Screen.Inteligencia,
        Screen.Predicciones
    )
    
    var selectedRoute by remember { mutableStateOf(Screen.Overview.route) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SportAgent - SportInteligent 2.0") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = selectedRoute == screen.route,
                        onClick = { selectedRoute = screen.route },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedRoute) {
                Screen.Overview.route -> OverviewScreen()
                Screen.Deportes.route -> ScreenPlaceholder("Gestión de Deportes")
                Screen.Inteligencia.route -> ScreenPlaceholder("Módulo de Inteligencia Artificial")
                Screen.Predicciones.route -> ScreenPlaceholder("Panel de Predicciones Deportivas")
            }
        }
    }
}

@Composable
fun ScreenPlaceholder(title: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }
}
