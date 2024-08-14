package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

class DynamicFab(defaultOnClick: () -> Unit = {}, defaultShow: Boolean = false) {
    var onClick: () -> Unit = defaultOnClick
    var show: Boolean = defaultShow

    @Composable
    fun Display() {
        if (show) {
            FloatingActionButton(onClick = onClick ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add")
            }
        }
    }
}