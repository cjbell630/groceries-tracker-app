package com.example.groceriestracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.groceriestracker.R

class TopAppBar(defaultShow: Boolean = true, private val navigateUp: () -> Unit = {}) {
    var show: Boolean = defaultShow
    var headerText: String = ""
    var showBackButton: Boolean = false

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Display() {
        if (show) {
            CenterAlignedTopAppBar(
                title = {
                    Text(headerText)
                },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back button" // TODO localize
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Rounded.Receipt, contentDescription = "Open shopping list")
                    }
                }
            )
        }
    }
}