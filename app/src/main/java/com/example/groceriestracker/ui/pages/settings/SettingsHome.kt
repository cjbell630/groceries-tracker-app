package com.example.groceriestracker.ui.pages.settings

import android.util.Log
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.groceriestracker.repository.SettingsRepository
import com.example.groceriestracker.repository.SettingsRepository.Settings.UseDynamic
import kotlinx.coroutines.launch

@Composable
fun SettingsHome(settingsRepo: SettingsRepository) {
    Text("settings")
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

    // TODO theme doesn't change until app restart, look at how it was done in NowInAndroid
    Switch(
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