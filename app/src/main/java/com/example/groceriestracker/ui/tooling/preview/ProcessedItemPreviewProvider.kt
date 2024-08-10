package com.example.groceriestracker.ui.tooling.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.database.Item

class ProcessedItemPreviewProvider : PreviewParameterProvider<ProcessedItem> {
    override val values = sequenceOf(
        ProcessedItem(
            Item(
                name = "Milk",
                amount = 2.0,
                unit = "Liters",
                iconId = "grape",
                statusEvents = emptyList() // Adjust as necessary
            ),
            onSave = { /* Do nothing */ }
        ),
        ProcessedItem(
            Item(
                name = "Bread",
                amount = 1.0,
                unit = "Loaf",
                iconId = "grape",
                statusEvents = emptyList() // Adjust as necessary
            ),
            onSave = { /* Do nothing */ }
        )
    )
}
