package com.example.groceriestracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Egg
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.groceriestracker.repository.ProcessedItem

val THINGY = 8.dp
val THINGY_SP = 8.sp

@Composable
fun ItemCard(item: ProcessedItem) {
    ElevatedCard(
        Modifier.padding(horizontal=2 * THINGY, vertical=1*THINGY)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // Makes the row take up the full width of the card
                .padding(2 * THINGY), // Adds padding inside the row
            horizontalArrangement = Arrangement.SpaceBetween, // Distributes space evenly between children

            verticalAlignment = Alignment.CenterVertically // Centers items vertically
        ) { // row contains row[icon, name], then row[circle, column[amount, unit]
            Row(
                horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically // Centers items vertically
            ) { // row contains
                item.icon?.Display(
                    modifier = Modifier
                        .size(10 * THINGY) // Larger icon size
                        .padding(end = 1 * THINGY)
                )
                Text(
                    item.name,
                    fontSize = 3 * THINGY_SP
                ) // Larger text size
            }
            Row(
                horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically // Centers items vertically
            ){

                CircularProgressIndicator(
                    progress={
                        0.8f //TODO
                    },
                    color = Color.Red,
                    trackColor = Color.Green,
                    modifier=Modifier.padding(end = 2 * THINGY)
                )
                Column(
                ) {
                    Text(item.remainingAmount.toString())
                    Text(item.unit)
                }
            }
        }
    }
}