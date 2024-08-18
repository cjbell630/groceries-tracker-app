package com.example.groceriestracker.ui.check

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.BarcodeScanner
import com.example.groceriestracker.BarcodeScanner.Companion.BarcodeDialog
import com.example.groceriestracker.R
import com.example.groceriestracker.models.UpcAssociation
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.ui.components.ItemCard

@Composable
fun CheckScreen(
    processedItems: List<ProcessedItem>,
    getUpcAssociation: (String) -> UpcAssociation?,
    addUpcAssociation: (UpcAssociation) -> Unit,
    incrementItemQuantity: (Int, Double) -> Unit,
    searchItems: (String) -> List<ProcessedItem>
) {
    val scanner = BarcodeScanner(LocalContext.current)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        BarcodeDialog(scanner, getUpcAssociation, addUpcAssociation, incrementItemQuantity, searchItems)

        ElevatedButton(onClick = { scanner.scanBarcode() }) {
            Text(stringResource(R.string.scan_bulk))
        }
        LazyColumn {
            items(processedItems.filter { item ->
                item.iconId != "grape" // TODO filter to "needs update" boolean field
            }) { processedItem: ProcessedItem ->
                ItemCard(processedItem)
            }
        }

    }
}