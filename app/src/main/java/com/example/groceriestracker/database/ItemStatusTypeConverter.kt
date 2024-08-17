package com.example.groceriestracker.database

import androidx.room.TypeConverter
import com.example.groceriestracker.models.ItemStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ItemStatusTypeConverter {
    @TypeConverter
    fun fromItemStatusList(itemStatuses: List<ItemStatus>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ItemStatus>>() {}.type
        return gson.toJson(itemStatuses, type)
    }

    @TypeConverter
    fun toItemStatusList(itemStatusesString: String): List<ItemStatus> {
        val gson = Gson()
        val type = object : TypeToken<List<ItemStatus>>() {}.type
        return gson.fromJson(itemStatusesString, type)
    }
}