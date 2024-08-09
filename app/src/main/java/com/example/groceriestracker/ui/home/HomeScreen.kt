package com.example.groceriestracker.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.ui.components.ItemCard

@Composable
fun HomeScreen(innerPadding: PaddingValues, processedItems: List<ProcessedItem>){
    Column(
        modifier = Modifier
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyColumn {
            items(processedItems) { processedItem: ProcessedItem ->
                ItemCard(processedItem)
            }
        }

    }
}