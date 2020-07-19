package dev.throwouterror.util.math.rotation

import dev.throwouterror.util.math.Direction

/**
 * @author Creepinson https:/theoparis.com/about
 */
enum class AxisRotation {
    NONE {
        override fun getCoordinate(x: Int, y: Int, z: Int, axis: Direction.Axis): Int {
            return axis.getCoordinate(x, y, z)
        }

        override fun rotate(axisIn: Direction.Axis): Direction.Axis {
            return axisIn
        }

        override fun reverse(): AxisRotation {
            return this
        }
    },
    FORWARD {
        override fun getCoordinate(x: Int, y: Int, z: Int, axis: Direction.Axis): Int {
            return axis.getCoordinate(z, x, y)
        }

        override fun rotate(axisIn: Direction.Axis): Direction.Axis {
            return AXES[Math.floorMod(axisIn.ordinal + 1, 3)]
        }

        override fun reverse(): AxisRotation {
            return BACKWARD
        }
    },
    BACKWARD {
        override fun getCoordinate(x: Int, y: Int, z: Int, axis: Direction.Axis): Int {
            return axis.getCoordinate(y, z, x)
        }

        override fun rotate(axisIn: Direction.Axis): Direction.Axis {
            return AXES[Math.floorMod(axisIn.ordinal - 1, 3)]
        }

        override fun reverse(): AxisRotation {
            return FORWARD
        }
    };

    abstract fun getCoordinate(x: Int, y: Int, z: Int, axis: Direction.Axis): Int
    abstract fun rotate(axisIn: Direction.Axis): Direction.Axis
    abstract fun reverse(): AxisRotation

    companion object {
        val AXES = Direction.Axis.values()
        val AXIS_ROTATIONS = values()
        fun from(axis1: Direction.Axis, axis2: Direction.Axis): AxisRotation {
            return AXIS_ROTATIONS[Math.floorMod(axis2.ordinal - axis1.ordinal, 3)]
        }
    }
}