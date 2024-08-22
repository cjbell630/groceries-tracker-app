package com.example.groceriestracker.ui.pages.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.ui.components.ItemCard
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setSearch
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setText

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreen(frontPane: FrontPane, processedItems: List<ProcessedItem>) {
    /* scaffold code adapted from google dev page */

    fun ThreePaneScaffoldNavigator<*>.isListExpanded() =
        scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

    fun ThreePaneScaffoldNavigator<*>.isDetailExpanded() =
        scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded

    val navController = rememberNavController()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<ProcessedItem>()
    val selectedItem = scaffoldNavigator.currentDestination?.content
    val listScrollState = rememberScrollState()
    val backBehavior = if (scaffoldNavigator.isListExpanded() && scaffoldNavigator.isDetailExpanded()) {
        BackNavigationBehavior.PopUntilContentChange
    } else {
        BackNavigationBehavior.PopUntilScaffoldValueChange
    }

    BackHandler(enabled = scaffoldNavigator.canNavigateBack(backBehavior)) {
        scaffoldNavigator.navigateBack(backBehavior)
    }

    fun updateFrontPane(){
        if(scaffoldNavigator.isDetailExpanded() && !scaffoldNavigator.isListExpanded() ){
            Log.d("HomeScreen", "setting top bar")
            frontPane.topBar.setText("Details", showBackButton = true, showSettingsButton = false)
        }else{
            frontPane.topBar.setSearch()
        }
    }


    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                LazyColumn(/*verticalArrangement = Arrangement.spacedBy(16.dp)*/) {
                    items(processedItems.sortedBy { item ->
                        item.estimatedTimeRemaining // TODO does this work?
                    }) { processedItem: ProcessedItem ->
                        ItemCard(processedItem) {
                            if (processedItem != selectedItem) {
                                scaffoldNavigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    content = processedItem
                                )
                                updateFrontPane()
                            }
                        }
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                Crossfade(targetState = selectedItem, label = "Detail Pane") { item ->
                    Text("item details: ${item?.name ?: "no item selected"}")
                }
            }
        }
    )
}