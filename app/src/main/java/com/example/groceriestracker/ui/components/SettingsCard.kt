package com.example.groceriestracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.ui.theme.GroceriesTrackerTypography

@Composable
fun SettingsCard(name: String, description: String = "", content: @Composable() (RowScope.(Modifier) -> Unit)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text =name, style = GroceriesTrackerTypography.titleLarge)
            Text(text = description, style = GroceriesTrackerTypography.labelMedium)
        }
        content(Modifier.padding(16.dp))
    }
}