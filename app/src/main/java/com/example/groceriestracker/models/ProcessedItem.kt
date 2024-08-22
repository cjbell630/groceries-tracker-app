package com.example.groceriestracker.models

import android.os.Parcelable
import com.example.groceriestracker.math.ProcessedItemHistory
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
class ProcessedItem(private val item: Item, private val onSave: suspend (Item) -> Unit) : Parcelable {
    val databaseEntryUID = item.uid
    var name: String = item.name ?: ""
    val remainingAmount: Double?
        get() = history.lastUpdate?.amount

    var unit: String = item.unit ?: name

    // TODO kinda cool but is this worth   get() = if(remainingAmount == 1.0) field else "${field}s"
    var iconId: String? = item.iconId
    private var statusEvents: MutableList<ItemStatus> = item.statusEvents.toMutableList()

    val history: ProcessedItemHistory = ProcessedItemHistory(item.statusEvents)

    var estimatedTimeRemaining: Long = calculateTimeRemaining()
        private set // not modifiable from outside the class

    //TODO error checking
    var icon: FriendlyIcon? = if (iconId != null) Preset.getById(iconId!!)?.icon else null

    init {
    }

    /**
     * Create a new item containing the current state of this object and save it to the database
     */
    suspend fun saveToDatabase() {
        val newItem = Item(databaseEntryUID, name, unit, iconId, statusEvents.toList())
        onSave(newItem)
        // TODO refresh?
    }

    private fun calculateTimeRemaining(): Long {
        return remainingAmount?.toLong()!! // TODO placeholder
        // TODO return estimateTimeRemaining(statusEvents)
    }

    fun setQuantity(newQuantity: Double) {
        statusEvents.add(ItemStatus(Date().time, newQuantity))
        //TODO recalc history
        //NOTE does not save to database
    }

    fun incrementQuantity(amountToIncrement: Double) {
        statusEvents.add(ItemStatus(Date().time, (history.lastUpdate?.amount ?: 0.0) + amountToIncrement))
        //TODO recalc history
        //NOTE does not save to database
    }

    fun matchQuery(query: String): Boolean {
        return name.contains(query, ignoreCase = true) // TODO
    }
}