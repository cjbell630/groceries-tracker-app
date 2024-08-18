package com.example.groceriestracker.ui.home

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
import com.example.groceriestracker.ui.components.BottomNavBar
import com.example.groceriestracker.ui.components.DynamicFab
import com.example.groceriestracker.ui.components.DynamicFab.Companion.ButtonModes
import com.example.groceriestracker.ui.components.DynamicTopAppBar
import com.example.groceriestracker.ui.components.FrontPane
import com.example.groceriestracker.ui.home.create.CreateScreen

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
            frontPane.fab.show = true
            frontPane.topBar.show = true
            frontPane.fab.mode = ButtonModes.Add
            frontPane.topBar.headerText = stringResource(R.string.header_app_name)
            frontPane.topBar.showBackButton = false
            frontPane.fab.onClick = {
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
            fun setOnClick(onClick: () -> Unit) {
                frontPane.fab.onClick = onClick
            }
            frontPane.fab.mode = ButtonModes.Next
            frontPane.fab.show = true // TODO change to next arrow (then save icon on next screen)
            frontPane.topBar.show = true
            frontPane.topBar.headerText = "Create" //TODO
            frontPane.topBar.showBackButton = true
            // TODO hide bottom bar
            CreateScreen(::setOnClick)
        }
    }
}
