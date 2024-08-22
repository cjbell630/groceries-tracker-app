package com.example.groceriestracker.ui.pages.settings

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setText


object SettingsDestinations {
    const val SETTINGS_HOME = "settings_home"
}

fun SettingsNavGraph(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    route: String,
    //allItems: List<ProcessedItem>,
    frontPane: FrontPane
) {

    fun baseNavEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
        return {
            fadeIn(
                animationSpec = tween(300, easing = LinearEasing)
            ) + slideIntoContainer(
                animationSpec = tween(100, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }
    }

    fun baseNavExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
        return {
            fadeOut(
                animationSpec = tween(300, easing = LinearEasing)
            )
        }
    }

    navGraphBuilder.navigation(
        route = route,
        startDestination = SettingsDestinations.SETTINGS_HOME
    ) {
        composable(
            SettingsDestinations.SETTINGS_HOME,
            enterTransition = baseNavEnterTransition(),
            exitTransition = baseNavExitTransition()
        ) {
            frontPane.topBar.setText("Settings", showBackButton = true)

            frontPane.bottomBar.hide()

            frontPane.fab.hide()

            SettingsHome()
        }
    }
}