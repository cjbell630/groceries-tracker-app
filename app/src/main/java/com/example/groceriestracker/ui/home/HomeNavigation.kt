package com.example.groceriestracker.ui.home

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.database.UpcAssociation
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.ui.TopLevelDestinations
import com.example.groceriestracker.ui.check.CheckScreen
import com.example.groceriestracker.ui.components.BottomNavBar
import com.example.groceriestracker.ui.components.DynamicFab
import com.example.groceriestracker.ui.components.TopAppBar
import com.example.groceriestracker.ui.home.HomeScreen

object HomeDestinations {
    const val LIST_ROUTE = "item_list"
    const val CREATE_ROUTE = "create"

}

fun HomeNavHost(
    navGraphBuilder: NavGraphBuilder,
    route: String,
    innerPadding: PaddingValues,
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
            HomeScreen(innerPadding, allItems)
        }
    }
}
