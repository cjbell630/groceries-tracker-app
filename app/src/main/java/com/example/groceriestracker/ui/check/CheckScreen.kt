package com.example.groceriestracker.ui.check

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
fun CheckScreen(innerPadding: PaddingValues, processedItems: List<ProcessedItem>){

    Column(
        modifier = Modifier
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyColumn {
            items(processedItems.filter {
                item->item.iconId!="grape" // TODO filter to "needs update" boolean field
            }) { processedItem: ProcessedItem ->
                ItemCard(processedItem)
            }
        }

    }
}