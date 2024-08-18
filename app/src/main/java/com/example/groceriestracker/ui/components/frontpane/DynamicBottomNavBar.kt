package com.example.groceriestracker.ui.components.frontpane

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.groceriestracker.R
import com.example.groceriestracker.ui.TopLevelDestinations

class DynamicBottomNavBar(var navBackStackEntry: NavBackStackEntry?, private val navController: NavController) :
    FrontPaneElement {
    override var isVisible: Boolean = true

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
        if (isVisible) {
            NavigationBar() {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Rounded.Home,
                            contentDescription = stringResource(R.string.label_home)

                        )
                    },
                    label = { Text(stringResource(R.string.label_home)) },
                    selected = navBackStackEntry?.isUnder(TopLevelDestinations.HOME_ROUTE) ?: false,
                    onClick = {
                        navController.navigate(TopLevelDestinations.HOME_ROUTE) {
                            /*TODO fix back button behavior on parallel navigation
                            popUpTo(TopLevelDestinations.HOME_ROUTE){
                                inclusive=true
                                saveState=true
                            }
                            launchSingleTop = true
                            restoreState = true*/
                        }
                        Log.d(
                            "BottomNavBar",
                            "navigating to home; full route: ${navBackStackEntry?.destination?.hierarchy?.joinToString { navDestination: NavDestination -> navDestination.route ?: "null" }}"
                        )
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Rounded.Checklist,
                            contentDescription = stringResource(R.string.label_shopping_list)
                        )
                    },
                    label = { Text(stringResource(R.string.label_shopping_list)) },
                    selected = navBackStackEntry?.isUnder(TopLevelDestinations.CHECK_ROUTE) ?: false,
                    onClick = {
                        navController.navigate(TopLevelDestinations.CHECK_ROUTE) {
                            /* TODO fix back button behavior on parallel navigation
                            popUpTo(TopLevelDestinations.CHECK_ROUTE){
                                inclusive=true
                                saveState=true
                            }
                            launchSingleTop = true
                            restoreState = true*/
                        }
                        Log.d(
                            "BottomNavBar",
                            "navigating to home; full route: ${navBackStackEntry?.destination?.hierarchy?.joinToString { navDestination: NavDestination -> navDestination.route ?: "null" }}"
                        )
                    }
                )
            }
        }
    }
}