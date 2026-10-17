package com.sport.agent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sport.agent.ui.screens.IntelligenceScreen
import com.sport.agent.ui.screens.OverviewScreen
import com.sport.agent.ui.screens.PredictionsScreen
import com.sport.agent.ui.screens.SportsScreen
import com.sport.agent.ui.screens.StatisticsScreen


sealed class Screen(val route: String) {
    object Overview : Screen("overview")
    object Sports : Screen("sports")
    object Intelligence : Screen("intelligence")
    object Predictions : Screen("predictions")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Sports.route
    ) {
        composable(Screen.Overview.route) {
            OverviewScreen()
        }
        composable(Screen.Sports.route) {
            SportsScreen()
        }
        composable(Screen.Intelligence.route) {
            IntelligenceScreen()
        }
        composable(Screen.Predictions.route) {
            PredictionsScreen()
        }
        composable("sports") {
            StatisticsScreen()}

    }
}
