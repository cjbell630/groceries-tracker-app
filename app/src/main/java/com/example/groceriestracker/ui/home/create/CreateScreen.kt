package com.example.groceriestracker.ui.home.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.groceriestracker.models.Preset
import com.example.groceriestracker.ui.components.AutofillTextField

@Composable
fun CreateScreen(innerPadding: PaddingValues) {
    // TODO headers and visuals and stuff
    Column(
        modifier = Modifier.padding(innerPadding)
    ) {

        //val autofillTextField = AutofillTextField<Preset>() { item -> item?.name ?: "null" }
    }
}