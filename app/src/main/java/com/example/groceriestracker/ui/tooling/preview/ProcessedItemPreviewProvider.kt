package com.example.groceriestracker.ui.tooling.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.database.Item

class ProcessedItemPreviewProvider : PreviewParameterProvider<ProcessedItem> {
    override val values = sequenceOf(
        ProcessedItem(
            Item(
                uid = 1,
                name = "Milk",
                amount = 2.0,
                unit = "Liters",
                statusEvents = emptyList() // Adjust as necessary
            ),
            onSave = { /* Do nothing */ }
        ),
        ProcessedItem(
            Item(
                uid = 2,
                name = "Bread",
                amount = 1.0,
                unit = "Loaf",
                statusEvents = emptyList() // Adjust as necessary
            ),
            onSave = { /* Do nothing */ }
        )
    )
}
