package com.dylan0221.inventory.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dylan0221.inventory.presentation.ui.screens.CalendarScreen
import com.dylan0221.inventory.presentation.ui.screens.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(route = Screens.Home.route) {
            HomeScreen()
        }
        composable(route = Screens.Calendar.route) {
            CalendarScreen()
        }

    }
}