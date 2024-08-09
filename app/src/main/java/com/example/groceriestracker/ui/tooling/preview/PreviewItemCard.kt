package com.example.groceriestracker.ui.tooling.preview

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.groceriestracker.ui.ItemCard
import com.example.groceriestracker.repository.ProcessedItem

@Preview
@Composable
fun PreviewItemCard(
    @PreviewParameter(ProcessedItemPreviewProvider::class) item: ProcessedItem
) {
    MaterialTheme {
        Surface {
            ItemCard(item)
        }
    }
}