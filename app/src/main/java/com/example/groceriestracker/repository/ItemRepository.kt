package com.example.groceriestracker.repository

import androidx.lifecycle.LiveData
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.database.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(private val itemDao: ItemDao) {

    val allItems: LiveData<List<Item>> = itemDao.getAll()

    suspend fun insert(item: Item) {
        withContext(Dispatchers.IO) {
            itemDao.insertAll(item)
        }
    }
}