package com.example.groceriestracker.ui.pages.home.create

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.models.Item
import com.example.groceriestracker.models.ItemStatus
import com.example.groceriestracker.models.Preset
import com.example.groceriestracker.ui.components.AutofillTextField
import com.example.groceriestracker.ui.components.FieldState
import com.example.groceriestracker.ui.components.ItemDetailsFormField
import com.example.groceriestracker.ui.components.frontpane.DynamicFab.Companion.ButtonModes
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import com.example.groceriestracker.ui.components.frontpane.FrontPane.Companion.setAction
import java.util.*

enum class CreationState {
    NAME, DETAILS
}

@Composable
fun CreateScreen(frontPane: FrontPane, goHome: () -> Unit,
                 storeNewItem: (Item)->Unit) {
    var state by rememberSaveable { mutableStateOf(CreationState.NAME) }
    var preset: Preset? by remember { mutableStateOf(null) }
    var name by remember { mutableStateOf("") }

    fun createItem(): Item {
        return Item(
            presetId = preset?.id,
            name = name,
            unit = null, //TODO
            iconId = null, //TODO
            needsUpdate = 0,
            statusEvents = listOf(ItemStatus(Date().time, 0.0/*TODO*/))
        )
    }

    @Composable
    fun setNameScreen() {
        // TODO headers and visuals and stuff
        Column() {
            // TODO change this to search bar?

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
            preset?.icon?.Display(modifier = Modifier.size(80.dp))
            // TODO move validate function into state class so it can be called on save click
            var nameFieldState by remember { mutableStateOf(FieldState(preset?.name)) }
            ItemDetailsFormField(
                state = nameFieldState,
                onTextChange = { nameFieldState = FieldState(it, it.isEmpty()) },
                valueName = "value name",
                presetVal = preset?.name
            )
            /*
            var unitFieldState by remember { mutableStateOf(FieldState(preset?.name)) }
            ItemDetailsFormField(
                state = unitFieldState,
                onTextChange = {
                    unitFieldState = FieldState(
                        it, errorText = if (it.contains("x")) null else "string must contain x"
                    )
                },
                valueName = "value name",
                presetVal = preset?.name
            )*/

            Button(onClick = {
                storeNewItem(createItem())
                goHome()
            }) {
                Text("Submit") // TODO placeholder bc fab isnt working
            }

            // TODO not working
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