package com.sport.agent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sport.agent.ui.screens.IntelligenceScreen
import com.sport.agent.ui.screens.OverviewScreen
import com.sport.agent.ui.screens.PredictionsScreen
import com.sport.agent.ui.screens.SettingsScreen
import com.sport.agent.ui.screens.StatisticsScreen

sealed class Screen(val route: String) {
    object Overview : Screen("overview")
    object Sports : Screen("sports")
    object Intelligence : Screen("intelligence")
    object Predictions : Screen("predictions")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Overview.route,
        modifier = modifier
    ) {
        composable(Screen.Overview.route) { OverviewScreen() }
        composable(Screen.Sports.route) { StatisticsScreen() }
        composable(Screen.Intelligence.route) { IntelligenceScreen() }
        composable(Screen.Predictions.route) { PredictionsScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}
