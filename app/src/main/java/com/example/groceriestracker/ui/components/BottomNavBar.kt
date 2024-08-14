package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.groceriestracker.ui.TopLevelDestinations

class BottomNavBar(var currentRoute: String, private val navController: NavController) {


    @Composable
    fun Display(){
        NavigationBar() {
            NavigationBarItem(
                icon = {
                    Icon(
                        Icons.Rounded.Home,
                        contentDescription = "Home"

                    )
                },
                label = { Text("Home") },
                selected = currentRoute == TopLevelDestinations.HOME_ROUTE,
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
                selected = currentRoute == TopLevelDestinations.CHECK_ROUTE,
                onClick = {
                    navController.navigate(TopLevelDestinations.CHECK_ROUTE)
                }
            )
        }
    }
}