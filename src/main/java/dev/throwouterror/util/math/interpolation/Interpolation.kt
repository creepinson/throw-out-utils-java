package dev.throwouterror.util.math.interpolation

import dev.throwouterror.util.math.Tensor
import java.util.*

abstract class Interpolation {
    protected var points = LinkedHashMap<Double, Tensor>()
    protected var pointVecs = ArrayList<Tensor>()
    private val classOfT: Class<*>

    constructor(times: DoubleArray, points: Array<Tensor>) {
        require(points.size >= 2) { "At least two points are needed!" }
        require(times.size == points.size) { "Invalid times array!" }
        classOfT = points[0].javaClass
        for (i in points.indices) {
            this.points[times[i]] = points[i]
        }
        pointVecs = ArrayList(this.points.values)
    }

    constructor(vararg points: Tensor) {
        require(points.size >= 2) { "At least two points are needed!" }
        classOfT = points[0].javaClass
        var time = 0.0
        val stepLength = 1.0 / (points.size - 1)
        for (element in points) {
            this.points[time] = element
            time += stepLength
        }
        pointVecs = ArrayList(this.points.values)
    }

    protected open fun getValue(index: Int, dim: Int): Double {
        return pointVecs[index].data[dim]
    }

    /**
     * 1 <= t <= 1
     */
    fun valueAt(t: Double): Tensor {
        if (t in 0.0..1.0) {
            var firstPoint: Map.Entry<Double, Tensor>? = null
            var indexFirst = -1
            var secondPoint: Map.Entry<Double, Tensor>? = null
            var indexSecond = -1
            var i = 0
            val iterator: Iterator<Map.Entry<Double, Tensor>> = points.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (entry.key >= t) {
                    if (firstPoint == null) {
                        firstPoint = entry
                        indexFirst = i
                    } else {
                        secondPoint = entry
                        indexSecond = i
                    }
                    break
                }
                firstPoint = entry
                indexFirst = i
                i++
            }
            if (secondPoint == null) return firstPoint!!.value.clone()
            val vec = firstPoint!!.value.clone()
            val pointDistance = secondPoint.key - firstPoint.key
            val mu = (t - firstPoint.key) / pointDistance
            for (dim in 0..2) {
                vec.data[dim] = valueAt(mu, indexFirst, indexSecond, dim).toDouble()
            }
            return vec
        }
        return Tensor.ZERO_VECTOR.clone()
    }

    abstract fun valueAt(mu: Double, pointIndex: Int, pointIndexNext: Int, dim: Int): Float
}