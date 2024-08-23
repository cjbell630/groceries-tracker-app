package com.example.groceriestracker.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.groceriestracker.models.BooleanSetting
import com.example.groceriestracker.models.Setting
import com.example.groceriestracker.models.StringSetting
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    object Settings {
        val Name = StringSetting("name")
        val Number = StringSetting("number")
        val UseDynamic = BooleanSetting("use_dynamic")
    }

    suspend fun <T> Setting<T>.getValue(): T? {
        return dataStore.data.map { preferences ->
            preferences[key]
        }.single()
    }

    suspend fun <T> Setting<T>.setValue(value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}