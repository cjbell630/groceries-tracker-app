package com.example.groceriestracker.ui.pages.settings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.groceriestracker.R
import com.example.groceriestracker.repository.SettingsRepository
import com.example.groceriestracker.repository.SettingsRepository.Settings.UseDynamic
import com.example.groceriestracker.ui.components.SettingsCard
import com.example.groceriestracker.ui.theme.GroceriesTrackerTypography
import kotlinx.coroutines.launch

@Composable
fun SettingsHome(settingsRepo: SettingsRepository) {
    var useDynamicTheme: Boolean? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(coroutineScope) {
        Log.d("SettingsHome", "Effect launched")
        launch {
            useDynamicTheme = with(settingsRepo) {
                UseDynamic.getValue()
            }
        }
        Log.d("SettingsHome", "UseDynamic got: ${useDynamicTheme}")
    }
    Column() {
        Text(
            text = stringResource(R.string.settings_category_ui),
            style = GroceriesTrackerTypography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        SettingsCard(
            name = stringResource(R.string.settings_name_dynamic_theme),
            description = stringResource(R.string.settings_desc_dynamic_theme)
        ) { modifier ->
            // TODO theme doesn't change until app restart, look at how it was done in NowInAndroid
            Switch(
                modifier = modifier,
                checked = useDynamicTheme ?: false /*TODO loading*/,
                onCheckedChange = {
                    useDynamicTheme = it;
                    coroutineScope.launch {
                        with(settingsRepo) {
                            UseDynamic.setValue(it)
                        }
                    }
                }
            )
        }
    }


}