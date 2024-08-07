package com.example.groceriestracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.groceriestracker.R
import androidx.compose.runtime.Composable

@Composable
fun AppDrawer(
    currentRoute: String,
    onNavigateToHome: () -> Unit,
    //onNavigateToInterests: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet() {
        // TODO JetNewsLogo( modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp))
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.title_home)) },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = currentRoute == TopLevelDestinations.HOME_ROUTE,
            onClick = { onNavigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        /*
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.interests_title)) },
            icon = { Icon(Icons.Filled.ListAlt, null) },
            selected = currentRoute == JetnewsDestinations.INTERESTS_ROUTE,
            onClick = { navigateToInterests(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )*/
    }
}