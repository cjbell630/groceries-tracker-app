package com.example.groceriestracker.repository

import com.example.groceriestracker.FriendlyIcon
import com.example.groceriestracker.IconInfo
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.math.estimateTimeRemaining
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProcessedItem(item: Item, private val onSave: suspend (Item) -> Unit) {
    private val databaseEntryUID = item.uid
    var name: String = item.name ?: ""
    var remainingAmount: Double? = item.amount
        set(value) {
            // TODO write
            // TODO see if called on incrementation
            field = value
        }

    var unit: String = item.unit ?: name

    // TODO kinda cool but is this worth   get() = if(remainingAmount == 1.0) field else "${field}s"
    var iconId: String? = item.iconId
    private var statusEvents = item.statusEvents

    var estimatedTimeRemaining: Long = calculateTimeRemaining()
        private set // not modifiable from outside the class

    //TODO error checking
    var icon: FriendlyIcon? = if (iconId != null) IconInfo.getIconById(iconId!!)?.icon else null

    init {
    }

    /**
     * Create a new item containing the current state of this object and save it to the database
     */
    suspend fun saveToDatabase() {
        val newItem = Item(databaseEntryUID, name, remainingAmount, unit, iconId, statusEvents)
        onSave(newItem)
        // TODO refresh?
    }

    private fun calculateTimeRemaining(): Long {
        return remainingAmount?.toLong()!! // TODO placeholder
        // TODO return estimateTimeRemaining(statusEvents)
    }
}