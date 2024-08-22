package com.example.groceriestracker.ui.components

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AutofillTextField(
    search: (String) -> List<T>, searchableToString: (T?) -> String, modifier: Modifier=Modifier,
    text: String, onTextChange: (String) -> Unit, selection: T?, onSelectionChange: (T?) -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox( // TODO move to another file
        expanded = dropDownExpanded,
        onExpandedChange = { dropDownExpanded = !dropDownExpanded },
        modifier = modifier
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
                if (it != searchableToString(selection)) {
                    onSelectionChange(null)
                }
                dropDownExpanded = true
            },
            modifier = Modifier.menuAnchor()
        )
        var filteredItems: List<T> = if (text.isNotEmpty()) search(text) else emptyList()
        Log.d(
            "AutofillTextField",
            "Filtered Items: ${filteredItems.joinToString { item -> searchableToString(item) }}"
        )
        Log.d("AutofillTextField", "Expanded?: ${dropDownExpanded}")
        if (filteredItems.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false }
            ) {
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onTextChange(searchableToString(item))
                            onSelectionChange(item)
                            dropDownExpanded = false
                        },
                        text = { Text(searchableToString(item)) }
                    )
                }
            }
        }
    }
}
