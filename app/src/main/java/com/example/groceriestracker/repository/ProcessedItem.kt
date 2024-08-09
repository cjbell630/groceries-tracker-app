package com.example.groceriestracker.repository

import com.example.groceriestracker.database.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProcessedItem(item: Item, private val onSave: suspend (Item) -> Unit){
    private val databaseEntryUID = item.uid
    private var name = item.name
    private var amount = item.amount
    private var unit = item.unit
    private var statusEvents = item.statusEvents

    init {
    }

    /**
     * Create a new item containing the current state of this object and save it to the database
     */
    suspend fun saveToDatabase() {
        val newItem = Item(databaseEntryUID, name, amount, unit, statusEvents)
        onSave(newItem)
        // TODO refresh?
    }

    fun getRemainingTime(): Int {
        return 0
    }

    fun getRemainingAmount(): Double? {
        return amount
    }

}