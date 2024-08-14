package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.groceriestracker.R

class TopAppBar() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Display(){
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(R.string.header_app_name))
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