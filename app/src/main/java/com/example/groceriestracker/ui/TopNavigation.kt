package com.example.groceriestracker.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.ui.home.HomeScreen

object TopLevelDestinations {
    const val HOME_ROUTE = "home"

}

@Composable
fun TopNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = TopLevelDestinations.HOME_ROUTE) {
        composable(TopLevelDestinations.HOME_ROUTE) {
            HomeScreen()
        }
    }
}
