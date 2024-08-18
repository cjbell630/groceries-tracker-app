package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

class DynamicFab(defaultOnClick: () -> Unit = {}, defaultShow: Boolean = false) {
    var onClick: () -> Unit = defaultOnClick
    var show: Boolean = defaultShow
    var mode: ButtonModes = ButtonModes.Add

    @Composable
    private fun GetIcon() {
        when (mode) {
            ButtonModes.Add -> Icon(Icons.Default.Add, contentDescription = "Add")
            ButtonModes.Next -> Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Next")
            ButtonModes.Save -> Icon(Icons.Default.Save, contentDescription = "Save")
        }
    }

    @Composable
    fun Display() {
        if (show) {
            FloatingActionButton(onClick = onClick) {
                GetIcon()
            }
        }
    }

    companion object {
        enum class ButtonModes {
            Add, Next, Save
        }
    }
}