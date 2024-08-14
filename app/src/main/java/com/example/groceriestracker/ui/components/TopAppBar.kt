package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

class TopAppBar() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Display(){
        CenterAlignedTopAppBar(
            title = {
                Text("Groceries Tracker")
            },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(Icons.Rounded.Receipt, contentDescription = "Open shopping list")
                }
            }
        )
    }
}