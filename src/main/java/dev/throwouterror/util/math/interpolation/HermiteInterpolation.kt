package dev.throwouterror.util.math.interpolation

import dev.throwouterror.util.math.Tensor

class HermiteInterpolation : CubicInterpolation {
    var tension: Tension
    var bias: Double

    constructor(times: DoubleArray, points: Array<Tensor>, bias: Double, tension: Tension) : super(times, points) {
        this.bias = bias
        this.tension = tension
    }

    @JvmOverloads
    constructor(times: DoubleArray, points: Array<Tensor>, tension: Tension = Tension.Normal) : this(times, points, 0.0, tension)

    constructor(bias: Double, tension: Tension, vararg points: Tensor) : super(*points) {
        this.bias = bias
        this.tension = tension
    }

    constructor(tension: Tension, vararg points: Tensor) : this(0.0, tension, *points)
    constructor(vararg points: Tensor) : this(Tension.Normal, *points)

    override fun valueAt(mu: Double, pointIndex: Int, pointIndexNext: Int, dim: Int): Float {
        var m0: Double
        var m1: Double
        val mu3: Double
        val a0: Double
        val a1: Double
        val a2: Double
        val a3: Double
        val v0 = getValue(pointIndex - 1, dim)
        val v1 = getValue(pointIndex, dim)
        val v2 = getValue(pointIndexNext, dim)
        val v3 = getValue(pointIndexNext + 1, dim)
        val mu2: Double = mu * mu
        mu3 = mu2 * mu
        m0 = (v1 - v0) * (1 + bias) * (1 - tension.value) / 2
        m0 += (v2 - v1) * (1 - bias) * (1 - tension.value) / 2
        m1 = (v2 - v1) * (1 + bias) * (1 - tension.value) / 2
        m1 += (v3 - v2) * (1 - bias) * (1 - tension.value) / 2
        a0 = 2 * mu3 - 3 * mu2 + 1
        a1 = mu3 - 2 * mu2 + mu
        a2 = mu3 - mu2
        a3 = -2 * mu3 + 3 * mu2
        return (a0 * v1 + a1 * m0 + a2 * m1 + a3 * v2).toFloat()
    }

    enum class Tension(val value: Int) {
        High(1), Normal(0), Low(-1);
    }
}