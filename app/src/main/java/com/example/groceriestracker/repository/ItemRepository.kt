package com.example.groceriestracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.database.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: LiveData<List<Item>> = itemDao.getAll()
    val processedItems: LiveData<List<ProcessedItem>> =
        allItems.map { items -> items.map { ProcessedItem(it, this::update) } }

    suspend fun update(item: Item) {
        withContext(Dispatchers.IO) {
            itemDao.update(item)
        }
    }

    suspend fun delete(item: Item) {
        withContext(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }

    suspend fun insert(item: Item) {
        withContext(Dispatchers.IO) {
            itemDao.insertAll(item)
        }
    }


}