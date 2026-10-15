package com.sport.agent.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sport.agent.ui.screens.IntelligenceScreen
import com.sport.agent.ui.screens.OverviewScreen
import com.sport.agent.ui.screens.PredictionsScreen
import com.sport.agent.ui.screens.SettingsScreen
import com.sport.agent.ui.screens.SportsScreen

private data class NavigationItem(
    val route: String,
    val label: String
)

private val navigationItems = listOf(
    NavigationItem("overview", "Overview"),
    NavigationItem("sports", "Deportes"),
    NavigationItem("intelligence", "Inteligencia"),
    NavigationItem("predictions", "Predicciones"),
    NavigationItem("settings", "Ajustes")
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val backStackEntry by
        navController.currentBackStackEntryAsState()

    val currentRoute =
        backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { item ->

                    val selected =
                        currentRoute == item.route

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(
                                    navController.graph
                                        .findStartDestination().id
                                ) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            when (item.route) {
                                "overview" -> Icon(
                                    Icons.Default.Home,
                                    contentDescription = item.label
                                )

                                "sports" -> Icon(
                                    Icons.Default.SportsSoccer,
                                    contentDescription = item.label
                                )

                                "intelligence" -> Icon(
                                    Icons.Default.Psychology,
                                    contentDescription = item.label
                                )

                                "predictions" -> Icon(
                                    Icons.Default.ShowChart,
                                    contentDescription = item.label
                                )

                                else -> Icon(
                                    Icons.Default.Settings,
                                    contentDescription = item.label
                                )
                            }
                        },
                        label = {
                            Text(item.label)
                        }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "overview",
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {

            composable("overview") {
                OverviewScreen()
            }

            composable("sports") {
                SportsScreen()
            }

            composable("intelligence") {
                IntelligenceScreen()
            }

            composable("predictions") {
                PredictionsScreen()
            }

            composable("settings") {
                SettingsScreen()
            }
        }
    }
}
