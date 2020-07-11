package dev.throwouterror.util.math.interpolation

import dev.throwouterror.util.math.Tensor

open class CubicInterpolation : Interpolation {
    var beginVec: Tensor
    var endVec: Tensor

    constructor(times: DoubleArray, points: Array<Tensor>) : super(times, points) {
        beginVec = points[0].add(points[0].sub(points[1]))
        endVec = points[points.size - 1].add(points[points.size - 1].sub(points[points.size - 2]))
    }

    constructor(vararg points: Tensor) : super(*points) {
        beginVec = points[0].add(points[0].sub(points[1]))
        endVec = points[points.size - 1].add(points[points.size - 1].sub(points[points.size - 2]))
    }

    override fun getValue(index: Int, dim: Int): Double {
        if (index < 0) return beginVec.data[dim]
        return if (index >= points.size) endVec.data[dim] else pointVecs[index].data[dim]
    }

    override fun valueAt(mu: Double, pointIndex: Int, pointIndexNext: Int, dim: Int): Float {
        val v0 = getValue(pointIndex - 1, dim)
        val v1 = getValue(pointIndex, dim)
        val v2 = getValue(pointIndexNext, dim)
        val v3 = getValue(pointIndexNext + 1, dim)
        val a0: Double
        val a1: Double
        val a2: Double
        val a3: Double
        val mu2: Double
        mu2 = mu * mu
        a0 = v3 - v2 - v0 + v1
        a1 = v0 - v1 - a0
        a2 = v2 - v0
        a3 = v1
        return (a0 * mu * mu2 + a1 * mu2 + a2 * mu + a3).toFloat()
    }
}