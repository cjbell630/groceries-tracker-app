package com.example.groceriestracker.ui.components

import android.os.Parcelable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.parcelize.Parcelize

@Parcelize
data class FieldState(val text: String = "", val isError: Boolean, val errorText: String? = null) : Parcelable {
    constructor(text: String?) : this(
        text ?: "",
        false
    )

    constructor(text: String?, errorText: String?) : this(
        text ?: "",
        errorText != null,
        errorText
    )
}

@Composable
fun ItemDetailsFormField(
    state: FieldState, onTextChange: (String) -> Unit,
    valueName: String,
    presetVal: String?,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Row {
        TextField(
            value = state.text,
            onValueChange = onTextChange,
            //TODO necessary? placeholder = {Text("placeholder text internal")},
            label = { Text(valueName) },
            trailingIcon = {
                if (state.isError) { // if field has error
                    Icon(Icons.Default.Error, contentDescription = "error")
                } else if (state.text != presetVal) { // show modified icon if modified
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "modified",
                        tint = Color.Yellow
                    )
                }
            },
            isError = state.isError,
            supportingText = {
                if (state.isError && state.errorText != null) {
                    Text(state.errorText)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )
    }

}