package com.example.groceriestracker.math

import com.example.groceriestracker.database.ItemStatus

class ProcessedItemHistory(private val events: List<ItemStatus>){
    val lastUpdate:ItemStatus?
        get() = events.lastOrNull()
    val estimatedExpiryTime:Long
    init{
        // TODO process
        estimatedExpiryTime = 0 //TODO
    }


}

fun processStatusEvents(events: List<ItemStatus>) {

}

fun estimateTimeRemaining(events: List<ItemStatus>): Long {
    // TODO get average curve from history
    // TODO get current curve since last increase
    // TODO predict future based on

    return 0 //TODO
}