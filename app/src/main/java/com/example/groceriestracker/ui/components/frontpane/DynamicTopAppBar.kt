package com.example.groceriestracker.ui.components.frontpane

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.groceriestracker.R

class DynamicTopAppBar(
    defaultShow: Boolean = true,
    private val navigateUp: () -> Unit = {},
    private val navSettings: () -> Unit = {}
) : FrontPaneElement {
    override var isVisible: Boolean = defaultShow
    var headerText: String = ""
    var showBackButton: Boolean = false
    var showSettingsButton: Boolean = true
    var mode: TopBarModes = TopBarModes.Normal

    @Composable
    fun Display() {
        if (isVisible) {
            Box(Modifier.fillMaxWidth().zIndex(1f).semantics { isTraversalGroup = true }) {
                when (mode) {
                    TopBarModes.Search -> BarWithSearch(
                        modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).semantics { traversalIndex = 0f }
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
                    BackButton(navigateUp)
                }
            },
            actions = {
                if (showSettingsButton) {
                    SettingsButton(navSettings = navSettings)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BarWithSearch(modifier: Modifier) {
        var text by rememberSaveable { mutableStateOf("") }
        var expanded by rememberSaveable { mutableStateOf(false) }
        // TODO add transitions
        SearchBar(
            modifier = if (expanded) modifier else modifier.padding(horizontal = 8.dp),

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
            placeholder = {
                Text(
                    text = stringResource(R.string.item_search_hint)
                )
            },
            active = expanded,
            onActiveChange = { active: Boolean -> expanded = active },
            leadingIcon = {
                if (expanded) {
                    BackButton { expanded = false }
                } else {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },
            trailingIcon = {
                if (!expanded) {
                    SettingsButton(navSettings = navSettings)
                }
            },
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

        @Composable
        fun SettingsButton(navSettings: () -> Unit) {
            IconButton(onClick = navSettings) {
                Icon(Icons.Default.Settings, contentDescription = null)
            }
        }

        @Composable
        fun BackButton(onClick: () -> Unit) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back button" // TODO localize
                )
            }
        }
    }
}