package com.example.groceriestracker.models

import com.example.groceriestracker.math.ProcessedItemHistory
import java.util.*

class ProcessedItem(item: Item, private val onSave: suspend (Item) -> Unit) {
    val databaseEntryUID = item.uid
    val preset: Preset? = if (item.presetId == null) null else Preset.getById(item.presetId)

    var name: String = item.name ?: preset?.name ?: ""

    val remainingAmount: Double?
        get() = history.lastUpdate?.amount

    var unit: String = item.unit ?: name // TODO make unit class

    // TODO kinda cool but is this worth   get() = if(remainingAmount == 1.0) field else "${field}s"

    var iconId: String? = item.iconId

    //TODO error checking
    var icon: FriendlyIcon? = if (iconId == null) preset?.icon else Preset.getById(iconId!!)?.icon

    var needsUpdate: Boolean = item.needsUpdate == 1

    private var statusEvents: MutableList<ItemStatus> = item.statusEvents.toMutableList()

    val history: ProcessedItemHistory = ProcessedItemHistory(item.statusEvents)

    var estimatedTimeRemaining: Long = calculateTimeRemaining()
        private set // not modifiable from outside the class


    init {

    }

    /**
     * Create a new item containing the current state of this object and save it to the database
     */
    suspend fun saveToDatabase() {
        val newItem = Item(
            uid = databaseEntryUID,
            presetId = preset?.id,
            name = name, unit = unit, iconId = iconId,
            needsUpdate = if (needsUpdate) 1 else 0,
            statusEvents.toList()
        )
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