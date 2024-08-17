package com.example.groceriestracker.ui.components

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier


class AutofillTextField<T>(val search: (String) -> List<T>, val searchableToString: (T?) -> String) {
    var selectedResult: T? = null // for getting from outside
    var selectedString: String = "" // for getting from outside

    fun isValid(): Boolean {
        return (searchableToString(selectedResult) == selectedString)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Display() {
        var dropDownExpanded by remember { mutableStateOf(false) }

        // Really weird code but seems to work and prevent values from getting overwritten on recompose
        var selectedResult2 by remember { mutableStateOf<T?>(null) } // remember state
        fun setSelectedResult(result: T?) {  // update class var and state
            selectedResult2 = result
            selectedResult = result
        }
        selectedResult = selectedResult2

        var selectedString2 by remember { mutableStateOf("") } // remember state
        fun setSelectedString(string: String) { // update class var and state
            selectedString2 = string
            selectedString = string
        }
        selectedString = selectedString2

        ExposedDropdownMenuBox( // TODO move to another file
            expanded = dropDownExpanded,
            onExpandedChange = { dropDownExpanded = !dropDownExpanded }
        ) {
            TextField(
                value = selectedString,
                onValueChange = {
                    setSelectedString(it)
                    if (it != searchableToString(selectedResult)) {
                        setSelectedResult(null)
                    }
                    dropDownExpanded = true
                },
                modifier = Modifier.menuAnchor()
            )
            var filteredItems: List<T> = if (selectedString.isNotEmpty()) search(selectedString) else emptyList()
            Log.d(
                "AutofillTextField",
                "Filtered Items: ${filteredItems.joinToString { item -> searchableToString(item) }}"
            )
            Log.d("AutofillTextField", "Expanded?: ${dropDownExpanded}}")
            if (filteredItems.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = dropDownExpanded,
                    onDismissRequest = { dropDownExpanded = false }
                ) {
                    filteredItems.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                setSelectedString(searchableToString(item))
                                setSelectedResult(item)
                                dropDownExpanded = false
                            },
                            text = { Text(searchableToString(item)) }
                        )
                    }
                }
            }
        }
    }
}
