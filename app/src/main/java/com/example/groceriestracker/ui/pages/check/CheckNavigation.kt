package com.example.groceriestracker.ui.pages.check

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.groceriestracker.R
import com.example.groceriestracker.models.UpcAssociation
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setText

object CheckDestinations {
    const val LIST_ROUTE = "shopping_list"
    const val SCAN_ROUTE = "scan"
}

fun CheckNavGraph(
    navGraphBuilder: NavGraphBuilder,
    route: String,
    allItems: List<ProcessedItem>,

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

    navGraphBuilder.navigation(
        route = route,
        startDestination = CheckDestinations.LIST_ROUTE
    ) {
        composable(
            CheckDestinations.LIST_ROUTE,
            enterTransition = baseNavEnterTransition(),
            exitTransition = baseNavExitTransition()
        ) {
            frontPane.topBar.setText(stringResource(R.string.header_app_name))

            frontPane.bottomBar.show()

            frontPane.fab.hide()

            CheckScreen(
                allItems, getUpcAssociation, addUpcAssociation, incrementItemQuantity, searchItems
            )
        }
    }
}
