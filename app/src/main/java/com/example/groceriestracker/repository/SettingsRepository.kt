package com.example.groceriestracker.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.groceriestracker.dataStore
import com.example.groceriestracker.models.BooleanSetting
import com.example.groceriestracker.models.Setting
import com.example.groceriestracker.models.StringSetting
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsRepository(private val dataStore: DataStore<Preferences>, lifecycleScope: LifecycleCoroutineScope) {
    init{
        lifecycleScope.launch{
            // preload settings
            dataStore.data.first()
        }
    }

    object Settings {
        val Name = StringSetting("name")
        val Number = StringSetting("number")
        val UseDynamic = BooleanSetting("use_dynamic")
    }

    suspend fun <T> Setting<T>.getValue(): T? {
        Log.d("SettingsRepository", "Getting value")
        /*Log.d("SettingsRepository", dataStore.data.map { preferences ->
            preferences[key]
        }.collect { thing -> thing.toString() }.toString())*/
        return dataStore.data.map { preferences ->
            preferences[key]
        }.first()
    }

    suspend fun <T> Setting<T>.setValue(value: T) {
        Log.d("SettingsRepository", "Setting value")
        dataStore.edit { preferences ->
            preferences[key] = value
        }
        Log.d("SettingsRepository", "Set value")
    }
}