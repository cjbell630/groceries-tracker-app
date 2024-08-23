package com.example.groceriestracker.models

import androidx.datastore.preferences.core.*


abstract class Setting<T>(val keyName: String) {
    abstract val key: Preferences.Key<T>
}

class StringSetting(keyName: String) : Setting<String>(keyName) {
    override val key: Preferences.Key<String> = stringPreferencesKey(keyName)
}

class IntSetting(keyName: String) : Setting<Int>(keyName) {
    override val key: Preferences.Key<Int> = intPreferencesKey(keyName)
}

class BooleanSetting(keyName: String) : Setting<Boolean>(keyName) {
    override val key: Preferences.Key<Boolean> = booleanPreferencesKey(keyName)
}

class DoubleSetting(keyName: String) : Setting<Double>(keyName) {
    override val key: Preferences.Key<Double> = doublePreferencesKey(keyName)
}