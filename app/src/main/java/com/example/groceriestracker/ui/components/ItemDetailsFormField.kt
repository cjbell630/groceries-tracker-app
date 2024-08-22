package com.example.groceriestracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ItemDetailsFormField(presetVal: String?) {
    var text by remember { mutableStateOf(presetVal ?: "") }
    Row {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("label") }
        )
        if (text != presetVal) { // show modified icon if modified
            Icon(Icons.Default.Star, contentDescription = "modified", tint = Color.Yellow)
        }
    }

}