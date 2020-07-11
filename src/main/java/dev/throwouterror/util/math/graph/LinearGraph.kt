package dev.throwouterror.util.math.graph

import java.util.*

class LinearGraph(points: HashMap<Float, Float>) : Graph(points) {
    override fun getY(x: Float): Float {
        val pointBefore = getPreviousPointX(x)
        val pointAfter = getNextPointX(x)
        if (pointBefore != null && pointAfter != null) {
            if (pointBefore === pointAfter) return points[pointBefore]!!
            val distance = pointAfter - pointBefore
            val relativePos = x - pointBefore
            val percentage = relativePos / distance
            val pointBeforeY = points[pointBefore]!!
            val distanceY = points[pointAfter]!! - pointBeforeY
            return pointBeforeY + distanceY * percentage
        }
        if (pointBefore != null) return points[pointBefore]!!
        return if (pointAfter != null) points[pointAfter]!! else 0f
    }
}