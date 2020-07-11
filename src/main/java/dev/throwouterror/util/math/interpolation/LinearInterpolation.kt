package dev.throwouterror.util.math.interpolation

import dev.throwouterror.util.math.Tensor

class LinearInterpolation : Interpolation {
    constructor(times: DoubleArray, points: Array<Tensor>) : super(times, points)
    constructor(vararg points: Tensor) : super(*points)

    override fun valueAt(mu: Double, pointIndex: Int, pointIndexNext: Int, dim: Int): Float {
        return ((getValue(pointIndexNext, dim) - getValue(pointIndex, dim)) * mu + getValue(pointIndex, dim)).toFloat()
    }
}