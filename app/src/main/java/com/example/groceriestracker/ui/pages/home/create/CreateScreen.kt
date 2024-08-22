package com.example.groceriestracker.ui.pages.home.create

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.models.Item
import com.example.groceriestracker.models.Preset
import com.example.groceriestracker.ui.components.AutofillTextField
import com.example.groceriestracker.ui.components.ItemDetailsFormField
import com.example.groceriestracker.ui.components.frontpane.DynamicFab.Companion.ButtonModes
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setAction

enum class CreationState {
    NAME, DETAILS
}

@Composable
fun CreateScreen(frontPane: FrontPane, goHome:()->Unit) {
    var state by remember { mutableStateOf(CreationState.NAME) }
    var preset: Preset? by remember { mutableStateOf(null) }

    @Composable
    fun setNameScreen() {
        // TODO headers and visuals and stuff
        Column() {
            // TODO change this to search bar?
            var name by remember { mutableStateOf("") }

            frontPane.fab.setAction(mode = ButtonModes.Next) {
                Log.d("CreateScreen", "next button pressed... name: ${name}")
                if (name == preset?.name) {
                    /*TODO continue to next screen*/
                    state = CreationState.DETAILS
                    Log.d("CreateScreen", "going to next screen")
                }
            }

            AutofillTextField(
                search = Preset::searchByAlias, searchableToString = { searchable -> searchable?.name ?: "null" },
                text = name, onTextChange = { name = it }, selection = preset, onSelectionChange = { preset = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    fun detailsScreen() {
        // TODO headers and visuals and stuff
        Column() {
            ItemDetailsFormField("presetVal text")

            frontPane.fab.setAction(mode = ButtonModes.Save) {
                Log.d("CreateScreen", "save button pressed...")
                if (/*TODO if fields are filled out correctly*/true) {
                    /*TODO save to database*/
                    Log.d("CreateScreen", "going home")
                    goHome()
                }
            }
        }
    }

    when (state) {
        CreationState.NAME -> setNameScreen()
        CreationState.DETAILS -> detailsScreen()
    }
}