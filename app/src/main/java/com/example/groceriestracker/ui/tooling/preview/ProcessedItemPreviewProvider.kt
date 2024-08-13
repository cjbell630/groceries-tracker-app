package com.example.groceriestracker.ui.tooling.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.database.ItemStatus

class ProcessedItemPreviewProvider : PreviewParameterProvider<ProcessedItem> {
    override val values = sequenceOf(
        ProcessedItem(
            Item(
                name = "Milk",
                unit = "Liters",
                iconId = "grape",
                statusEvents = listOf(ItemStatus(1723166595, 2.0)) // Adjust as necessary
            ),
            onSave = { /* Do nothing */ }
        ),
        ProcessedItem(
            Item(
                name = "Bread",
                unit = "Loaf",
                iconId = "grape",
                statusEvents = listOf(ItemStatus(1723339395, 1.0)) // Adjust as necessary
            ),
            onSave = { /* Do nothing */ }
        )
    )
}
