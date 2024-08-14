package com.example.groceriestracker.ui.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.groceriestracker.ui.TopLevelDestinations

class BottomNavBar(var navBackStackEntry: NavBackStackEntry?, private val navController: NavController) {

    /**
     * Checks whether the current route is under the provided route.
     * For example: /home/list_screen -> isUnder("home") = true
     *              /home             -> isUnder("home") = true
     *              /check/barcode    -> isUnder("home") = false
     */
    fun NavBackStackEntry.isUnder(route: String): Boolean {
        // TODO move this function to somewhere else
        return destination.hierarchy.any { navDestination: NavDestination ->
            navDestination.route == route
        }
    }

    @Composable
    fun Display() {

        NavigationBar() {
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Rounded.Home,
                        contentDescription = "Home"

                    )
                },
                label = { Text("Home") },
                selected = navBackStackEntry?.isUnder(TopLevelDestinations.HOME_ROUTE) ?: false,
                onClick = {
                    navController.navigate(TopLevelDestinations.HOME_ROUTE)
                }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Rounded.Checklist,
                        contentDescription = "Check"

                    )
                },
                label = { Text("Check") },
                selected = navBackStackEntry?.isUnder(TopLevelDestinations.CHECK_ROUTE) ?: false,
                onClick = {
                    navController.navigate(TopLevelDestinations.CHECK_ROUTE)
                }
            )
        }
    }
}