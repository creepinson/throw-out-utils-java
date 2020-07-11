/*
 * Copyright (c) Creepinson
 */
package dev.throwouterror.util.math.rotation

import dev.throwouterror.util.math.Direction
import dev.throwouterror.util.math.Tensor

enum class Rotation(val axis: Direction.Axis, val matrix: RotationMatrix, val clockwise: Boolean) {
    X_CLOCKWISE(Direction.Axis.X, RotationMatrix(
            1.0,
            0.00,
            0.0,
            0.0,
            0.0,
            -1.0,
            0.0,
            1.0,
            0.0
    ), true) {
        override val opposite: Rotation?
            get() = X_COUNTER_CLOCKWISE
    },
    X_COUNTER_CLOCKWISE(Direction.Axis.X, RotationMatrix(
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0,
            0.0,
            -1.0,
            0.0
    ), false) {
        override val opposite: Rotation?
            get() = X_CLOCKWISE

    },
    Y_CLOCKWISE(Direction.Axis.Y, RotationMatrix(
            0.0,
            0.0,
            1.0,
            0.0,
            1.0,
            0.0,
            -1.0,
            0.0,
            0.0
    ), true) {
        override val opposite: Rotation?
            get() = Y_COUNTER_CLOCKWISE
    },
    Y_COUNTER_CLOCKWISE(Direction.Axis.Y, RotationMatrix(
            0.0,
            0.0,
            -1.0,
            1.0,
            0.0,
            0.0,
            1.0,
            0.0,
            0.0
    ), false) {
        override val opposite: Rotation?
            get() = Y_CLOCKWISE
    },
    Z_CLOCKWISE(Direction.Axis.Z, RotationMatrix(
            0.0,
            -1.0,
            0.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0
    ), true) {
        override val opposite: Rotation?
            get() = Z_COUNTER_CLOCKWISE
    },
    Z_COUNTER_CLOCKWISE(Direction.Axis.Z, RotationMatrix(
            0.0,
            1.0,
            0.0,
            -1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            1.0
    ), false) {
        override val opposite: Rotation?
            get() = Z_CLOCKWISE
    };

    val direction: Int = if (clockwise) 1 else -1
    var vec: Tensor
        get() = vec.clone()

    fun getRotatedComponentPositive(axis: Direction.Axis?): Boolean {
        when (axis) {
            Direction.Axis.X -> return if (matrix.m00 != 0.0) matrix.m00 > 0 else if (matrix.m10 != 0.0) matrix.m10 > 0 else matrix.m20 > 0
            Direction.Axis.Y -> return if (matrix.m01 != 0.0) matrix.m01 > 0 else if (matrix.m11 != 0.0) matrix.m11 > 0 else matrix.m21 > 0
            Direction.Axis.Z -> return if (matrix.m02 != 0.0) matrix.m02 > 0 else if (matrix.m12 != 0.0) matrix.m12 > 0 else matrix.m22 > 0
        }
        return true
    }

    fun getRotatedComponent(axis: Direction.Axis): Direction.Axis {
        return when (axis) {
            Direction.Axis.X -> if (matrix.m00 != 0.0) Direction.Axis.X else if (matrix.m10 != 0.0) Direction.Axis.Y else Direction.Axis.Z
            Direction.Axis.Y -> if (matrix.m01 != 0.0) Direction.Axis.X else if (matrix.m11 != 0.0) Direction.Axis.Y else Direction.Axis.Z
            Direction.Axis.Z -> if (matrix.m02 != 0.0) Direction.Axis.X else if (matrix.m12 != 0.0) Direction.Axis.Y else Direction.Axis.Z
        }
        return axis
    }

    fun negativeX(): Boolean {
        return if (matrix.m00 != 0.0) matrix.m00 < 0 else if (matrix.m01 != 0.0) matrix.m01 < 0 else matrix.m02 < 0
    }

    fun <T> getX(x: T, y: T, z: T): T {
        return if (matrix.m00 != 0.0) x else if (matrix.m01 != 0.0) y else z
    }

    fun negativeY(): Boolean {
        return if (matrix.m10 != 0.0) matrix.m10 < 0 else if (matrix.m11 != 0.0) matrix.m11 < 0 else matrix.m12 < 0
    }

    fun <T> getY(x: T, y: T, z: T): T {
        return if (matrix.m10 != 0.0) x else if (matrix.m11 != 0.0) y else z
    }

    fun negativeZ(): Boolean {
        return if (matrix.m20 != 0.0) matrix.m20 < 0 else if (matrix.m21 != 0.0) matrix.m21 < 0 else matrix.m22 < 0
    }

    fun <T> getZ(x: T, y: T, z: T): T {
        return if (matrix.m20 != 0.0) x else if (matrix.m21 != 0.0) y else z
    }

    open val opposite: Rotation? = null

    companion object {
        fun getRotation(axis: Direction.Axis?, clockwise: Boolean): Rotation? {
            when (axis) {
                Direction.Axis.X -> return if (clockwise) X_CLOCKWISE else X_COUNTER_CLOCKWISE
                Direction.Axis.Y -> return if (clockwise) Y_CLOCKWISE else Y_COUNTER_CLOCKWISE
                Direction.Axis.Z -> return if (clockwise) Z_CLOCKWISE else Z_COUNTER_CLOCKWISE
            }
            return null
        }

        fun getRotation(vec: Tensor): Rotation? {
            if (vec.x > 0) return X_CLOCKWISE
            if (vec.x < 0) return X_COUNTER_CLOCKWISE
            if (vec.y > 0) return Y_CLOCKWISE
            if (vec.y < 0) return Y_COUNTER_CLOCKWISE
            if (vec.z > 0) return Z_CLOCKWISE
            return if (vec.z < 0) Z_COUNTER_CLOCKWISE else null
        }
    }

    init {
        vec = Tensor.ZERO_VECTOR.clone()
        RotationUtils.setValue(vec, direction.toFloat(), axis)
    }
}