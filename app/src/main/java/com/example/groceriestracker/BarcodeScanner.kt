package com.example.groceriestracker

import android.content.Context
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog
import com.example.groceriestracker.BarcodeScanner.ScannerState
import com.example.groceriestracker.database.UpcAssociation
import com.example.groceriestracker.repository.UpcAssociationRepository
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

    fun updateState(newState: ScannerState){
        state.value = newState
    }

    fun getState() : ScannerState{
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

    companion object{

        @Composable
        fun BarcodeDialog(scanner:BarcodeScanner, getUpcAssociation: (String) -> UpcAssociation?, addUpcAssociation: (UpcAssociation) -> Unit, incrementItemQuantity: (Int, Double) -> Unit) {
            when(scanner.getState()){
                ScannerState.SCANNED -> {
                    val upc = scanner.barcode?.rawValue!!
                    val upcAssociation = getUpcAssociation(upc)
                    if(upcAssociation != null){
                        incrementItemQuantity(upcAssociation.itemId, upcAssociation.amount)
                        // TODO popup snackbar or something
                        scanner.scanBarcode()
                    }else{
                        // TODO Fill in props
                        addUpcAssociation(UpcAssociation(upc=upc, itemId=1, amount=10.0))
                        scanner.scanBarcode() // TODO
                        /*
                        Dialog(
                            onDismissRequest = { /*state = ScannerState.CANCELLED*/ } // TODO
                        ) {
                            Text("scanned: ${scanner.barcode?.rawValue}") // TODO
                        }*/
                    }
                }

                else -> {}
            }
        }
    }
}