package dev.throwouterror.util.math.graph

import java.util.*

abstract class Graph(val points: HashMap<Float, Float>) {
    fun getPreviousPointX(below: Float): Float? {
        var next: Float? = null
        for (point in points.keys) {
            if (point <= below && (next == null || point > next)) next = point
        }
        return next
    }

    fun getNextPointX(above: Float): Float? {
        var next: Float? = null
        for (point in points.keys) {
            if (point >= above && (next == null || point < next)) next = point
        }
        return next
    }

    val lastPointX: Float
        get() = getPreviousPointX(Float.MAX_VALUE)!!

    val firstPointX: Float
        get() = getNextPointX(Float.MIN_VALUE)!!

    abstract fun getY(x: Float): Float

}