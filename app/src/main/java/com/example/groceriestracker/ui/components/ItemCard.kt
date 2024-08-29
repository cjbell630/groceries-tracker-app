package com.example.groceriestracker.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.groceriestracker.models.ProcessedItem
import java.util.*
import kotlin.math.floor
import kotlin.math.roundToInt

val THINGY = 8.dp
val THINGY_SP = 8.sp

@Composable
fun ItemCard(item: ProcessedItem) {
    ElevatedCard(
        Modifier.padding(horizontal = 2 * THINGY, vertical = 1 * THINGY)
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
            ) {
                if (item.history.estimatedExpiryTime == null) {
                    Icon(
                        Icons.Default.QuestionMark,
                        contentDescription = "cannot calculate remaining time",
                        modifier = Modifier.padding(end = 2 * THINGY)
                    )
                } else {
                    // TODO verify this works
                    // TODO add error checking for if lastUpdate is null ( should never happen but still)
                    // use elvis
                    Log.d("ItemCard",
                        "${item.name}: item.history.estimatedExpiryTime: ${item.history.estimatedExpiryTime}\n" +
                                "item.history.lastIncrease?.time!!: ${item.history.lastIncrease?.time!!}\n" +
                                "Date().time: ${Date().time}"
                    )
                    val daysRemaining =( item.history.estimatedExpiryTime - Date().time).toDouble()/86400000.0

                    val estimatedProductLife:Double = (item.history.estimatedExpiryTime - item.history.lastIncrease?.time!!).toDouble()
                    val timeSinceLastIncrease: Double = (Date().time - item.history.lastIncrease?.time!!).toDouble()
                    val progress = ( timeSinceLastIncrease/estimatedProductLife).toFloat()
                    Text("${floor(daysRemaining)} days")
                    CircularProgressIndicator(
                        progress = { progress },
                        color = Color.Red,
                        trackColor = Color.Green,
                        modifier = Modifier.padding(end = 2 * THINGY)
                    )
                }
                Column() {
                    Text(item.remainingAmount.toString())
                    Text(item.unit)
                }
            }
        }
    }
}