package dev.throwouterror.util.math.interpolation

import dev.throwouterror.util.math.Tensor
import kotlin.math.cos

class CosineInterpolation(vararg points: Tensor) : Interpolation(*points) {
    override fun valueAt(mu: Double, pointIndex: Int, pointIndexNext: Int, dim: Int): Float {
        val mu2 = (1 - cos(mu * Math.PI)) / 2
        return (getValue(pointIndex, dim) * (1 - mu2) + getValue(pointIndexNext, dim) * mu2).toFloat()
    }
}