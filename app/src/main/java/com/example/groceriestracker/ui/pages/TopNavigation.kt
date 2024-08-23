package com.example.groceriestracker.ui.pages

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.models.UpcAssociation
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.repository.SettingsRepository
import com.example.groceriestracker.ui.pages.check.CheckNavGraph
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.pages.home.HomeNavGraph
import com.example.groceriestracker.ui.pages.settings.SettingsNavGraph

object TopLevelDestinations {
    const val HOME_ROUTE = "home"
    const val CHECK_ROUTE = "check"
    const val SETTINGS_ROUTE = "settings"
}

@Composable
fun TopNavHost(
    navController: NavHostController = rememberNavController(),
    allItems: List<ProcessedItem>,
    settingsRepo: SettingsRepository,

    frontPane: FrontPane,

    getUpcAssociation: (String) -> UpcAssociation?,
    addUpcAssociation: (UpcAssociation) -> Unit,
    incrementItemQuantity: (Int, Double) -> Unit,
    searchItems: (String) -> List<ProcessedItem>
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

    NavHost(navController, startDestination = TopLevelDestinations.HOME_ROUTE) {
        HomeNavGraph(
            this@NavHost, navController, TopLevelDestinations.HOME_ROUTE, allItems,
            frontPane
        )

        CheckNavGraph(
            this@NavHost, TopLevelDestinations.CHECK_ROUTE, allItems,
            frontPane,
            getUpcAssociation, addUpcAssociation, incrementItemQuantity, searchItems
        )

        SettingsNavGraph(
            navGraphBuilder = this@NavHost,
            navController = navController,
            route = TopLevelDestinations.SETTINGS_ROUTE,
            frontPane = frontPane,
            settingsRepo = settingsRepo
        )
    }
}
