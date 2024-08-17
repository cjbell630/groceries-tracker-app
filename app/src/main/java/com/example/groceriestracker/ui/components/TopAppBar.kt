package com.example.groceriestracker.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.zIndex

class TopAppBar(defaultShow: Boolean = true, private val navigateUp: () -> Unit = {}) {
    var show: Boolean = defaultShow
    var headerText: String = ""
    var showBackButton: Boolean = false

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Display() {
        if (show) {
            Box(Modifier.fillMaxWidth().zIndex(1f).semantics { isTraversalGroup=true }) {
                var text by rememberSaveable { mutableStateOf("") }
                var expanded by rememberSaveable { mutableStateOf(false) }
                SearchBar(
                    modifier = Modifier.align(Alignment.TopCenter)/*.zIndex(if (expanded) 1f else 0f)*/.semantics { traversalIndex = 0f },

                    /* 1.3?
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = text,
                            onQueryChange = { text = it },
                            onSearch = { expanded = false },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("Hinted search text") },
                            loadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }*/
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { expanded = false },
                    placeholder = { Text("Hinted search text") },
                    active = expanded,
                    onActiveChange = { active: Boolean -> expanded = active },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
                ) {
                    /*this appears when expanded*/
                    Column() {

                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DisplayWithoutSearchBar() {
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