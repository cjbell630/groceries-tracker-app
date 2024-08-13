package com.example.groceriestracker.ui.check

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.groceriestracker.BarcodeScanner
import com.example.groceriestracker.BarcodeScanner.Companion.BarcodeDialog
import com.example.groceriestracker.database.UpcAssociation
import com.example.groceriestracker.repository.ProcessedItem
import com.example.groceriestracker.repository.UpcAssociationRepository
import com.example.groceriestracker.ui.components.ItemCard

@Composable
fun CheckScreen(innerPadding: PaddingValues, processedItems: List<ProcessedItem>, getUpcAssociation: (String) -> UpcAssociation?, addUpcAssociation: (UpcAssociation) -> Unit, incrementItemQuantity: (Int, Double) -> Unit) {
    val scanner = BarcodeScanner(LocalContext.current)

    Column(
        modifier = Modifier
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BarcodeDialog(scanner, getUpcAssociation, addUpcAssociation, incrementItemQuantity)

        ElevatedButton(onClick = {scanner.scanBarcode()}) {
            Text("Scan bulk")
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