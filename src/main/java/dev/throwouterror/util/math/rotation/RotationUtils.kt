/*
 * Copyright (c) Creepinson
 */
package dev.throwouterror.util.math.rotation

import dev.throwouterror.util.math.Direction
import dev.throwouterror.util.math.Direction.AxisDirection
import dev.throwouterror.util.math.Tensor

object RotationUtils {
    private var facingNames: Array<String?>? = arrayOf()
    private var horizontalFacingNames: Array<String?>? = arrayOf()
    fun getHorizontalFacingNames(): Array<String?>? {
        if (horizontalFacingNames == null) {
            horizontalFacingNames = arrayOfNulls(4)
            for (i in horizontalFacingNames!!.indices) {
                horizontalFacingNames!![i] = Direction.byHorizontalIndex(i).name
            }
        }
        return horizontalFacingNames
    }

    fun getFacingNames(): Array<String?>? {
        if (facingNames == null) {
            facingNames = arrayOfNulls(6)
            for (i in facingNames!!.indices) {
                facingNames!![i] = Direction.byHorizontalIndex(i).dirName
            }
        }
        return facingNames
    }

    fun getFacing(axis: Direction.Axis?): Direction? {
        when (axis) {
            Direction.Axis.X -> return Direction.EAST
            Direction.Axis.Y -> return Direction.UP
            Direction.Axis.Z -> return Direction.NORTH
        }
        return null
    }

    fun setValue(vec: Tensor, value: Double, axis: Direction.Axis?) {
        setValue(vec, value.toFloat(), axis)
    }

    fun setValue(vec: Tensor, value: Float, axis: Direction.Axis?) {
        vec.setValueByAxis(axis, value.toDouble())
    }

    operator fun get(axis: Direction.Axis?, x: Float, y: Float, z: Float): Float {
        when (axis) {
            Direction.Axis.X -> return x
            Direction.Axis.Y -> return y
            Direction.Axis.Z -> return z
        }
        return 0f
    }

    operator fun get(axis: Direction.Axis?, v: Tensor): Double {
        return v.getValueByAxis(axis)
    }

    operator fun get(axis: Direction.Axis?, x: Double, y: Double, z: Double): Double {
        when (axis) {
            Direction.Axis.X -> return x
            Direction.Axis.Y -> return y
            Direction.Axis.Z -> return z
        }
        return 0.0
    }

    operator fun get(axis: Direction.Axis?, x: Int, y: Int, z: Int): Int {
        when (axis) {
            Direction.Axis.X -> return x
            Direction.Axis.Y -> return y
            Direction.Axis.Z -> return z
        }
        return 0
    }

    fun getDifferentAxis(one: Direction.Axis?, two: Direction.Axis): Direction.Axis? {
        when (one) {
            Direction.Axis.X -> {
                return if (two === Direction.Axis.Y) Direction.Axis.Z else Direction.Axis.Y
            }
            Direction.Axis.Y -> {
                return if (two === Direction.Axis.X) Direction.Axis.Z else Direction.Axis.X
            }
            Direction.Axis.Z -> {
                return if (two === Direction.Axis.Y) Direction.Axis.X else Direction.Axis.Y
            }
        }
        return null
    }

    fun getDifferentAxisFirst(axis: Direction.Axis): Direction.Axis {
        return when (axis) {
            Direction.Axis.X -> Direction.Axis.Y
            Direction.Axis.Y -> Direction.Axis.Z
            Direction.Axis.Z -> Direction.Axis.X
        }
        return axis
    }

    fun getDifferentAxisSecond(axis: Direction.Axis): Direction.Axis {
        return when (axis) {
            Direction.Axis.X -> Direction.Axis.Z
            Direction.Axis.Y -> Direction.Axis.X
            Direction.Axis.Z -> Direction.Axis.Y
        }
        return axis
    }

    fun rotate(axis: Direction.Axis, rotation: Rotation): Direction.Axis {
        return if (axis === rotation.axis) axis else when (axis) {
            Direction.Axis.X -> {
                if (rotation.axis === Direction.Axis.Y) Direction.Axis.Z else Direction.Axis.Y
            }
            Direction.Axis.Y -> {
                if (rotation.axis === Direction.Axis.Z) Direction.Axis.X else Direction.Axis.Y
            }
            Direction.Axis.Z -> {
                if (rotation.axis === Direction.Axis.X) Direction.Axis.Y else Direction.Axis.X
            }
        }
        return axis
    }

    fun rotate(rotation: Rotation, by: Rotation): Rotation? {
        val vec = rotation.vec
        by.matrix.transform(vec)
        return Rotation.getRotation(vec)
    }

    fun flip(rotation: Rotation, axis: Direction.Axis): Rotation? {
        return if (rotation.axis === axis) rotation.opposite else rotation
    }

    fun rotate(facing: Direction, rotation: Rotation): Direction {
        val rotatedNormal = rotation.matrix.toVector().mul(facing.directionVec)
        for (rotated in Direction.values()) {
            if (rotated.directionVec == rotatedNormal) return rotated
        }
        return facing
    }

    fun rotate(vec: Tensor, rotation: Rotation?): Tensor? {
        return rotation?.matrix?.transform(vec)
    }

    fun flip(vec: Tensor, axis: Direction.Axis): Tensor {
        return when (axis) {
            Direction.Axis.X -> Tensor(-vec.x, vec.y, vec.z)
            Direction.Axis.Y -> Tensor(vec.x, -vec.y, vec.z)
            Direction.Axis.Z -> Tensor(vec.x, vec.y, -vec.z)
        }
    }

    fun isFacingPositive(index: Int): Boolean {
        return index == 1 || index == 3 || index == 5
    }

    fun getUAxisFromFacing(facing: Direction): Direction.Axis? {
        return when (facing.axis) {
            Direction.Axis.X -> Direction.Axis.Z
            Direction.Axis.Z -> Direction.Axis.X
            else -> null
        }
    }

    fun getVAxisFromFacing(facing: Direction): Direction.Axis? {
        return when (facing.axis) {
            Direction.Axis.Z -> Direction.Axis.Y
            Direction.Axis.Y -> Direction.Axis.Z
            else -> null
        }
    }

    fun getUFromFacing(facing: Direction, x: Float, y: Float, z: Float): Float {
        return when (facing.axis) {
            Direction.Axis.X -> z
            Direction.Axis.Z -> x
            else -> 0f
        }
    }

    fun getVFromFacing(facing: Direction, x: Float, y: Float, z: Float): Float {
        return when (facing.axis) {
            Direction.Axis.Z -> y
            Direction.Axis.Y -> z
            else -> 0f
        }
    }

    private val rotations = Array(3) { arrayOfNulls<BooleanRotation>(4) }
    fun getOffset(d: Double): AxisDirection? {
        if (d > 0) return AxisDirection.POSITIVE else if (d < 0) return AxisDirection.NEGATIVE
        return null
    }

    enum class BooleanRotation(val axis: Direction.Axis, private val index: Int, private val positiveOne: Boolean, private val positiveTwo: Boolean) {
        // one: y, two: z
        X_PP(Direction.Axis.X, 0, true, true),
        X_NP(Direction.Axis.X, 1, false, true), X_NN(Direction.Axis.X, 2, false, false), X_PN(Direction.Axis.X, 3, true, false),  // one: x, two: z
        Y_PP(Direction.Axis.Y, 0, true, true), Y_PN(Direction.Axis.Y, 1, true, false), Y_NN(Direction.Axis.Y, 2, false, false), Y_NP(Direction.Axis.Y, 3, false, true),  // one: x, two: y
        Z_PP(Direction.Axis.Z, 0, true, true), Z_NP(Direction.Axis.Z, 1, false, true), Z_NN(Direction.Axis.Z, 2, false, false), Z_PN(Direction.Axis.Z, 3, true, false);

        fun clockwise(): BooleanRotation? {
            return if (index == 3) rotations[axis.ordinal][0] else rotations[axis.ordinal][index + 1]
        }

        fun counterClockwise(): BooleanRotation? {
            return if (index == 0) rotations[axis.ordinal][3] else rotations[axis.ordinal][index - 1]
        }

        fun clockwiseMaxFacing(): Direction {
            return getFacingInBetween(clockwise())
        }

        fun counterMaxClockwiseFacing(): Direction {
            return getFacingInBetween(counterClockwise())
        }

        private fun getFacingInBetween(other: BooleanRotation?): Direction {
            return if (positiveOne != other!!.positiveOne) Direction.Companion.getFacingFromAxis(if (positiveTwo) AxisDirection.POSITIVE else AxisDirection.NEGATIVE,
                    getTwo(axis)) else if (positiveTwo != other.positiveTwo) Direction.Companion.getFacingFromAxis(if (positiveOne) AxisDirection.POSITIVE else AxisDirection.NEGATIVE,
                    getOne(axis)) else throw RuntimeException("Impossible to happen!")
        }

        fun `is`(vec: Tensor): Boolean {
            return (positiveOne == get(getOne(axis), vec) >= 0
                    && positiveTwo == get(getTwo(axis), vec) >= 0)
        }

        companion object {
            private fun getOne(axis: Direction.Axis): Direction.Axis? {
                return when (axis) {
                    Direction.Axis.X -> Direction.Axis.Y
                    Direction.Axis.Y, Direction.Axis.Z -> Direction.Axis.X
                    else -> null
                }
            }

            private fun getTwo(axis: Direction.Axis): Direction.Axis? {
                return when (axis) {
                    Direction.Axis.X, Direction.Axis.Y -> Direction.Axis.Z
                    Direction.Axis.Z -> Direction.Axis.Y
                    else -> null
                }
            }

            fun getRotationState(axis: Direction.Axis, vec: Tensor): BooleanRotation? {
                val positiveOne = get(getOne(axis), vec) >= 0
                val positiveTwo = get(getTwo(axis), vec) >= 0
                for (element in rotations[axis.ordinal]) {
                    if (element!!.positiveOne == positiveOne && element.positiveTwo == positiveTwo) return element
                }
                return null
            }
        }

        init {
            rotations[axis.ordinal][index] = this
        }
    }
}