package com.example.groceriestracker.ui.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.ui.components.ItemCard

@Composable
fun HomeScreen(processedItems: List<ProcessedItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        LazyColumn {
            items(processedItems.sortedBy { item ->
                item.estimatedTimeRemaining // TODO does this work?
            }) { processedItem: ProcessedItem ->
                ItemCard(processedItem)
            }
        }

    }
}