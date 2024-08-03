package com.example.groceriestracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Item(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "amount") val amount: Double?,
    @ColumnInfo(name = "unit") val unit: String?,
    @ColumnInfo(name = "status_events") @TypeConverters(ItemStatusTypeConverter::class) val events: List<ItemStatus>
)
