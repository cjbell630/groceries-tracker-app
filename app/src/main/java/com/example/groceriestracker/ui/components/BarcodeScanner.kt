package com.example.groceriestracker.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.groceriestracker.models.Preset
import com.example.groceriestracker.models.UpcAssociation
import com.example.groceriestracker.models.ProcessedItem
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class BarcodeScanner(context: Context) {
    private val scanner: GmsBarcodeScanner
    var barcode: Barcode? = null
    var state = mutableStateOf(ScannerState.WAITING)

    init {
        Log.d("BarcodeScanner", "init")
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_8, Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E
            )
            .enableAutoZoom() // available on 16.1.0 and higher
            .build()
        scanner = GmsBarcodeScanning.getClient(context, options)
    }

    fun updateState(newState: ScannerState) {
        state.value = newState
    }

    fun getState(): ScannerState {
        return state.value
    }

    fun scanBarcode() {
        updateState(ScannerState.SCANNING)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                this.barcode = barcode
                updateState(ScannerState.SCANNED)
                Log.d("BarcodeScanner", "State: Scanned")
            }
            .addOnCanceledListener {
                // Task canceled
                this.barcode = null
                updateState(ScannerState.CANCELLED)
                Log.d("BarcodeScanner", "State: Cancelled")
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // TODO error handling
                this.barcode = null
                updateState(ScannerState.FAILED)
                Log.d("BarcodeScanner", "State: Failed")
            }
    }

    enum class ScannerState {
        WAITING, SCANNING, SCANNED, CANCELLED, FAILED
    }

    companion object {

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun AssociateUPCDialog(
            upc: String,
            searchItems: (String) -> List<ProcessedItem>,
            onCancel: () -> Unit,
            onSuccessfulSubmit: (Int, Double) -> Unit
        ) {


            Log.d("AssociateUPCDialog", "Launching")
            BasicAlertDialog(
                onDismissRequest = { onCancel()/*state = ScannerState.CANCELLED*/ }, // TODO
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
            ) {
                Surface(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("scanned: ${upc}") // TODO


                        var name by remember { mutableStateOf("") }
                        var item: ProcessedItem? by remember { mutableStateOf(null) }

                        AutofillTextField(
                            search = searchItems,
                            searchableToString = { searchable -> searchable?.name ?: "null" },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = name,
                            onTextChange = { name = it },
                            selection = item,
                            onSelectionChange = { item = it }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        TextButton(
                            onClick = { onCancel() },
                            modifier = Modifier.align(Alignment.Start)
                        ) {
                            Text("Cancel")
                        }
                        TextButton(
                            onClick = {
                                if (name == item?.name) {
                                    onSuccessfulSubmit(
                                        item?.databaseEntryUID!!,
                                        15.0/*TODO*/
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.Start)
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }

        @Composable
        fun BarcodeDialog(
            scanner: BarcodeScanner,
            getUpcAssociation: (String) -> UpcAssociation?,
            addUpcAssociation: (UpcAssociation) -> Unit,
            incrementItemQuantity: (Int, Double) -> Unit,
            searchItems: (String) -> List<ProcessedItem>
        ) {
            when (scanner.getState()) {
                ScannerState.SCANNED -> {
                    val upc = scanner.barcode?.rawValue!!
                    val upcAssociation = getUpcAssociation(upc)
                    Log.d("BarcodeDialog", "upc association: ${upcAssociation.toString()}")
                    if (upcAssociation != null) {
                        incrementItemQuantity(upcAssociation.itemId, upcAssociation.amount)
                        // TODO popup snackbar or something
                        scanner.scanBarcode()
                    } else {
                        fun onSuccessfulSubmit(itemId: Int, amount: Double) {
                            addUpcAssociation(UpcAssociation(upc = upc, itemId = itemId, amount = amount))
                            incrementItemQuantity(itemId, amount)
                            scanner.scanBarcode()
                        }
                        // TODO Fill in props
                        AssociateUPCDialog(
                            upc, searchItems, onCancel = {
                                scanner.updateState(ScannerState.CANCELLED)
                            }, onSuccessfulSubmit = ::onSuccessfulSubmit
                        )
                    }
                }

                else -> {}
            }
        }
    }
}