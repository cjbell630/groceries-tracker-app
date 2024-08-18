package com.example.groceriestracker.ui.home.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.models.Preset
import com.example.groceriestracker.ui.components.AutofillTextField

@Composable
fun CreateScreen() {
    // TODO headers and visuals and stuff
    Column() {
        val autofillTextField = AutofillTextField<Preset>(Preset::searchByAlias) { preset -> preset?.name ?: "null" }
        autofillTextField.Display(modifier = Modifier.padding(horizontal = 16.dp))
    }
}