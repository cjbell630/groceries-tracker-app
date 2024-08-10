package com.example.groceriestracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.ui.check.CheckScreen
import com.example.groceriestracker.ui.home.HomeScreen

object TopLevelDestinations {
    const val HOME_ROUTE = "home"
    const val CHECK_ROUTE = "check"

}

@Composable
fun TopNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues,
    allItems: List<ProcessedItem>
) {
    NavHost(navController, startDestination = TopLevelDestinations.HOME_ROUTE) {
        composable(TopLevelDestinations.HOME_ROUTE) {
            HomeScreen(innerPadding, allItems)
        }

        composable(TopLevelDestinations.CHECK_ROUTE) {
            CheckScreen(innerPadding, allItems)
        }
    }
}
