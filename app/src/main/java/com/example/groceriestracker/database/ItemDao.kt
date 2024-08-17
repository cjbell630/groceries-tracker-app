package com.example.groceriestracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groceriestracker.models.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE uid IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<Item>

    @Query("SELECT * FROM item WHERE name LIKE :nameQuery LIMIT 1")
    fun findByName(nameQuery: String): Item

    @Insert
    suspend fun insertAll(vararg items: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(vararg items: Item)
}