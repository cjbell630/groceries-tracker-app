package com.example.groceriestracker.ui.components.frontpane

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.groceriestracker.ui.pages.TopLevelDestinations

class FrontPane(navBackStackEntry: NavBackStackEntry?, navController: NavController) {
    val fab = DynamicFab()
    val topBar = DynamicTopAppBar(
        navigateUp = navController::navigateUp,
        navSettings = {
            Log.d(
                "FrontPane",
                "settings button clicked"
            ); navController.navigate(TopLevelDestinations.SETTINGS_ROUTE)
        })
    val bottomBar = DynamicBottomNavBar(navBackStackEntry, navController)

    @Composable
    fun FloatingActionButton() {
        fab.Display()
    }

    @Composable
    fun TopAppBar() {
        topBar.Display()
    }

    @Composable
    fun BottomNavBar() {
        bottomBar.Display()
    }

    companion object {
        /* FAB */
        fun DynamicFab.setAction(
            mode: DynamicFab.Companion.ButtonModes = this.mode,
            onClick: () -> Unit = this.onClick
        ) {
            this.show()
            // TODO a little wasteful but it's fine
            this.mode = mode
            this.onClick = onClick
        }

        /* Top Bar */
        fun DynamicTopAppBar.setText(
            text: String,
            showBackButton: Boolean = this.showBackButton,
            showSettingsButton: Boolean = this.showSettingsButton
        ) {
            this.showBackButton = showBackButton
            this.showSettingsButton = showSettingsButton
            this.mode = DynamicTopAppBar.Companion.TopBarModes.Normal
            this.show()
            this.headerText = text
        }

        fun DynamicTopAppBar.setSearch() {
            this.mode = DynamicTopAppBar.Companion.TopBarModes.Search
            this.show()
        }
    }
}