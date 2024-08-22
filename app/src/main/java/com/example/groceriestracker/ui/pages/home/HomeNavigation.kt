package com.example.groceriestracker.ui.pages.home

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.groceriestracker.R
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.ui.components.frontpane.DynamicFab.Companion.ButtonModes
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setAction
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setSearch
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setText
import com.example.groceriestracker.ui.pages.home.create.CreateScreen

object HomeDestinations {
    const val LIST_ROUTE = "item_list"
    const val CREATE_ROUTE = "create"
}

fun HomeNavGraph(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    route: String,
    allItems: List<ProcessedItem>,
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

    //TODO add route defaults for app bars and stuff

    navGraphBuilder.navigation(
        route = route,
        startDestination = HomeDestinations.LIST_ROUTE
    ) {
        composable(
            HomeDestinations.LIST_ROUTE,
            enterTransition = baseNavEnterTransition(),
            exitTransition = baseNavExitTransition()
        ) {
            frontPane.topBar.setSearch()

            frontPane.bottomBar.show()

            frontPane.fab.setAction(ButtonModes.Add) {
                Log.d("HomeNavGraph", "fab clicked")
                navController.navigate(HomeDestinations.CREATE_ROUTE)
            }

            HomeScreen(allItems)
        }

        composable(
            HomeDestinations.CREATE_ROUTE,
            enterTransition = baseNavEnterTransition(), // TODO
            exitTransition = baseNavExitTransition() //TODO
        ) {
            frontPane.topBar.setText(stringResource(R.string.label_create_screen), showBackButton = true, showSettingsButton = false)

            frontPane.bottomBar.hide()

            frontPane.fab.setAction(ButtonModes.Next)

            CreateScreen(frontPane)
        }
    }
}
