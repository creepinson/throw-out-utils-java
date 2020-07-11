/*
 * Copyright (c) Creepinson
 */
package dev.throwouterror.util.math.rotation

import dev.throwouterror.util.math.Tensor

/**
 * @author Throw Out Error (https://throw-out-error.dev)
 */
class RotationMatrix(val data: Tensor) {

    @JvmOverloads
    constructor(m00: Double = 0.0, m01: Double = 0.0, m02: Double = 0.0, m10: Double = 0.0, m11: Double = 0.0, m12: Double = 0.0, m20: Double = 0.0, m21: Double = 0.0, m22: Double = 0.0) : this(Tensor(m00, m01, m02, m10, m11, m12, m20, m21, m22))

    var m00: Double
        get() = data[0]
        set(value) {
            data[0] = value
        }

    var m01: Double
        get() = data[1]
        set(value) {
            data[1] = value
        }


    var m02: Double
        get() = data[2]
        set(value) {
            data[2] = value
        }


    var m10: Double
        get() = data[3]
        set(value) {
            data[3] = value
        }


    var m11: Double
        get() = data[4]
        set(value) {
            data[4] = value
        }


    var m12: Double
        get() = data[5]
        set(value) {
            data[5] = value
        }


    var m20: Double
        get() = data[6]
        set(value) {
            data[6] = value
        }

    var m21: Double
        get() = data[7]
        set(value) {
            data[7] = value
        }

    var m22: Double
        get() = data[8]
        set(value) {
            data[8] = value
        }

    val x: Float
        get() = (data[0] * data[1] * data[2]).toFloat()

    val y: Float
        get() = (data[3] * data[4] * data[5]).toFloat()

    val z: Float
        get() = (data[6] * data[7] * data[8]).toFloat()

    fun toVector(): Tensor {
        return Tensor(x.toDouble(), y.toDouble(), z.toDouble())
    }

    fun transform(v: Tensor): Tensor {
        return v.mul(this)
    }

    fun transform(v: Tensor, dest: Tensor): Tensor {
        return v.mul(this, dest)
    }

    override fun equals(`object`: Any?): Boolean {
        if (`object` is RotationMatrix) {
            return data.equals(`object`.data)
        }
        return false
    }

    override fun toString(): String {
        return data.toString()
    }

}