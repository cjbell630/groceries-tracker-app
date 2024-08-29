package com.example.groceriestracker.math

import android.util.Log
import com.example.groceriestracker.models.ItemStatus

class ProcessedItemHistory(private val events: List<ItemStatus>) {
    val lastUpdate: ItemStatus?
        get() = events.lastOrNull()
    var lastIncrease: ItemStatus? = null
    val estimatedExpiryTime: Long?

    init {
        // get history and current curve since last increase
        val (historicalCurves, currentCurve) = processStatusEvents()
        estimatedExpiryTime = estimateExiryTime(historicalCurves, currentCurve)
    }

    fun processStatusEvents(): Pair<List<Curve>, List<ItemStatus>> {
        var prev: Double = Double.MAX_VALUE
        var currentTimeline: MutableList<ItemStatus> = mutableListOf()
        var curves: MutableList<Curve> = mutableListOf()
        for (status in events) {
            // TODO need to account for a few things I took notes on (such as not always reaching 0)
            if (status.amount <= prev) { // if decrease

            } else { // increase
                lastIncrease = status // NOTE sets last increase
                curves.add(normalizeStatusEvents(currentTimeline))
                currentTimeline = mutableListOf()
            }
            currentTimeline.add(status)
            prev = status.amount
        }
        for (curve in curves) {
            Log.d("processStatusEvents", curve.points.joinToString { "(${it.x}, ${it.y})   " })
        }
        lastIncrease = currentTimeline.first()
        return Pair(curves, currentTimeline)
    }
}

fun normalizeStatusEvents(events: List<ItemStatus>): Curve {
    // TODO error checking for length
    val first = events.first()
    val last = events.last()
    val timeRange = (last.time - first.time).toDouble()
    val amountRange = first.amount - last.amount
    return Curve(events.map {
        Point((it.time - first.time) / timeRange, (it.amount - last.amount) / amountRange)
    })
}

fun estimateExiryTime(historicalCurves: List<Curve>, currentCurve: List<ItemStatus>): Long? {
    // get average curve from history
    val average = getSimpleLinearAverage(historicalCurves)
    Log.d("estimateExiryTime", "average curve: " + average.points.joinToString { "(${it.x}, ${it.y})   " })

    /*
    * P = estimated percentage of time elapsed of product usage
    * C = time of most recent (current) update
    * B = time of first (beginning) update
    * T = total estimated time of product usage (from start to finish)
    * R = estimated time remaining from most recent update
    * E = estimated expiry time (long)
    * P * T = C - B
    * T = (C - B) / P
    * R = T - (C - B)
    * E = B + T = C + R
    * */
    val currentAmountPercent = currentCurve.last().amount / currentCurve.first().amount
    val estimatedTimePercent = interpolateXAtY(average, currentAmountPercent) ?: return null // P
    val timeRange = (currentCurve.last().time - currentCurve.first().time).toDouble() // C - B
    val estimatedTotalLife = timeRange / estimatedTimePercent // T
    val estimatedExpiryTime: Long = currentCurve.first().time + estimatedTotalLife.toLong() // E
    Log.d("estimateExiryTime",
        "currentAmountPercent: ${currentAmountPercent}\n" +
                "estimatedTimePercent: ${estimatedTimePercent}\n" +
                "timeRange: ${timeRange}\n" +
                "estimatedTotalLife: ${estimatedTotalLife}\n" +
                "estimatedExpiryTime: ${estimatedExpiryTime}"
    )
    return estimatedExpiryTime
}