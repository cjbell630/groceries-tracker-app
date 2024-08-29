package com.example.groceriestracker.math

import android.util.Log

data class Point(var x: Double, var y: Double)

class Curve(val points: List<Point>) {
    var head: Int = 0
    val length = points.size
    fun getCurrentPoint(): Point {
        return points[head]
    }
}

fun getLinearInterpolationX(leftPoint: Point, targetY: Double, rightPoint: Point): Double {
    return ((rightPoint.x - leftPoint.x) / (rightPoint.y - leftPoint.y)) * (targetY - leftPoint.y) + leftPoint.x
}

fun getLinearInterpolationY(leftPoint: Point, targetX: Double, rightPoint: Point): Double {
    return ((rightPoint.y - leftPoint.y) / (rightPoint.x - leftPoint.x)) * (targetX - leftPoint.x) + leftPoint.y
}

fun interpolateXAtY(curve: Curve, targetY: Double): Double? {
    curve.head = 0
    while (curve.head < curve.length && curve.getCurrentPoint().y < targetY) {
        curve.head++
    }
    if (curve.head >= curve.length) {
        return null
    }
    val leftPoint = curve.getCurrentPoint()
    if (leftPoint.y == targetY) {
        return leftPoint.x
    }
    curve.head++
    val rightPoint = curve.getCurrentPoint()
    return getLinearInterpolationX(leftPoint, targetY, rightPoint)
}

fun getSimpleLinearAverage(lines: List<Curve>): Curve {
    //var indices = List(lines.size){index -> Pair<Int, Int>(index, 0)}
    var average: MutableList<Point> = mutableListOf()
    val numLines: Double = lines.size.toDouble()
    lines.forEach {
        it.head = 0
    }
    while (
        !lines.any { it.head >= it.length }
    ) {
        //val targetIndex = indices.minByOrNull { it.second }
        val targetX = lines.minOf {
            it.getCurrentPoint().x
        }

        var sum = 0.0

        for (line in lines) {
            val currentPoint = line.getCurrentPoint()
            val yAtTarget: Double
            if (currentPoint.x == targetX) {
                yAtTarget = currentPoint.y
                line.head++
            } else {
                val rightPoint = line.getCurrentPoint()
                line.head--
                val leftPoint = line.getCurrentPoint()
                line.head++

                yAtTarget = getLinearInterpolationY(
                    leftPoint = leftPoint, targetX = targetX, rightPoint = rightPoint
                )
            }
            sum += yAtTarget
        }
        average.add(Point(targetX, sum / numLines))
    }
    return Curve(average)
}