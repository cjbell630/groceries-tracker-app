package com.example.groceriestracker.ui.pages.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.ui.components.ItemCard

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreen(processedItems: List<ProcessedItem>) {
    val navController = rememberNavController()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<ProcessedItem>()
    val selectedItem = scaffoldNavigator.currentDestination?.content
    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(processedItems.sortedBy { item ->
                        item.estimatedTimeRemaining // TODO does this work?
                    }) { processedItem: ProcessedItem ->
                        ItemCard(processedItem) {
                            if (processedItem != selectedItem) {
                                scaffoldNavigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    content = processedItem
                                )
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