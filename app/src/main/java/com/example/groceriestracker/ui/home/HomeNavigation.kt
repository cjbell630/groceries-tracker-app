package com.example.groceriestracker.ui.home

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.groceriestracker.ui.components.TopAppBar
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
    topAppBar: TopAppBar,
    bottomNavBar: BottomNavBar,
    floatingActionButton: DynamicFab,
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
            floatingActionButton.show = true
            topAppBar.show = true
            floatingActionButton.mode = ButtonModes.Add
            topAppBar.headerText = stringResource(R.string.header_app_name)
            topAppBar.showBackButton = false
            floatingActionButton.onClick = {
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
                floatingActionButton.onClick = onClick
            }
            floatingActionButton.mode = ButtonModes.Next
            floatingActionButton.show = true // TODO change to next arrow (then save icon on next screen)
            topAppBar.show = true
            topAppBar.headerText = "Create" //TODO
            topAppBar.showBackButton = true
            // TODO hide bottom bar
            CreateScreen(::setOnClick)
        }
    }
}
