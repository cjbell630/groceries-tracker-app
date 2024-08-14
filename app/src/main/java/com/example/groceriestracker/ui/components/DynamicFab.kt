package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

class DynamicFab(defaultOnClick: ()->Unit) {
    private val onClick: ()->Unit = defaultOnClick

    @Composable
    fun Display(){
        FloatingActionButton(onClick = { onClick }) {
            Icon(Icons.Rounded.Add, contentDescription = "Add")
        }
    }
}