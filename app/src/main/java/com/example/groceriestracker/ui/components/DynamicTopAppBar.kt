package com.example.groceriestracker.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.zIndex
import com.example.groceriestracker.R

class DynamicTopAppBar(defaultShow: Boolean = true, private val navigateUp: () -> Unit = {}) : FrontPaneElement {
    override var isVisible: Boolean = defaultShow
    var headerText: String = ""
    var showBackButton: Boolean = false
    var mode: TopBarModes = TopBarModes.Normal

    @Composable
    fun Display() {
        if (isVisible) {
            Box(Modifier.fillMaxWidth().zIndex(1f).semantics { isTraversalGroup = true }) {
                when (mode) {
                    TopBarModes.Search -> BarWithSearch(
                        modifier = Modifier.align(Alignment.TopCenter).semantics { traversalIndex = 0f }
                    )

                    TopBarModes.Normal -> NormalBar()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NormalBar() {
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BarWithSearch(modifier: Modifier) {
        var text by rememberSaveable { mutableStateOf("") }
        var expanded by rememberSaveable { mutableStateOf(false) }
        SearchBar(
            modifier = modifier,

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
            placeholder = { Text(stringResource(R.string.item_search_hint)) },
            active = expanded,
            onActiveChange = { active: Boolean -> expanded = active },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
        ) {
            /*this appears when expanded*/
            Column() {
                // TODO display content
            }
        }
        // TODO on expand, change search icon to back button, hide settings button
    }

    companion object {

        enum class TopBarModes {
            Search, Normal
        }
    }
}