package com.example.groceriestracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE uid IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<Item>

    @Query("SELECT * FROM item WHERE name LIKE :nameQuery LIMIT 1")
    fun findByName(nameQuery: String): Item

    @Insert
    suspend fun insertAll(vararg users: Item)

    @Delete
    fun delete(user: Item)
}