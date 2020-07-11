//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package dev.throwouterror.util.math

import dev.throwouterror.util.ArrayUtils
import java.io.Serializable
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors.toMap

/**
 * @author mojang https://minecraft.net
 */
enum class Direction(val index: Int, private val opposite: Int, val horizontalIndex: Int, val dirName: String, val axisDirection: AxisDirection,
                     val axis: Axis, val facingVec: Tensor) : Serializable {
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, Tensor(0.0, (-1).toDouble(), 0.0)), UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, Tensor(0.0, 1.0, 0.0)), NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, Tensor(0.0, 0.0, (-1).toDouble())), SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, Tensor(0.0, 0.0, 1.0)), WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, Tensor((-1).toDouble(), 0.0, 0.0)), EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, Tensor(1.0, 0.0, 0.0));

    fun getOpposite(): Direction {
        return byIndex(opposite)
    }

    fun rotateY(): Direction {
        return when (this) {
            NORTH -> EAST
            SOUTH -> WEST
            WEST -> NORTH
            EAST -> SOUTH
            else -> throw IllegalStateException("Unable to get Y-rotated facing of $this")
        }
    }

    fun rotateYCCW(): Direction {
        return when (this) {
            NORTH -> WEST
            SOUTH -> EAST
            WEST -> SOUTH
            EAST -> NORTH
            else -> throw IllegalStateException("Unable to get CCW facing of $this")
        }
    }

    val xOffset: Int
        get() = facingVec.z.toInt()

    val yOffset: Int
        get() = facingVec.z.toInt()

    val zOffset: Int
        get() = facingVec.z.toInt()

    val directionVec: Tensor
        get() = Tensor(xOffset.toFloat(), yOffset.toFloat(), zOffset.toFloat())

    val horizontalAngle: Float
        get() = ((horizontalIndex and 3) * 90).toFloat()

    override fun toString(): String {
        return dirName
    }

    enum class Axis(val axisName: String) : Serializable, Predicate<Direction?> {
        X("x") {
            override fun getCoordinate(x: Int, y: Int, z: Int): Int {
                return x
            }

            override fun getCoordinate(x: Double, y: Double, z: Double): Double {
                return x
            }
        },
        Y("y") {
            override fun getCoordinate(x: Int, y: Int, z: Int): Int {
                return y
            }

            override fun getCoordinate(x: Double, y: Double, z: Double): Double {
                return y
            }
        },
        Z("z") {
            override fun getCoordinate(x: Int, y: Int, z: Int): Int {
                return z
            }

            override fun getCoordinate(x: Double, y: Double, z: Double): Double {
                return z
            }
        };

        val isVertical: Boolean
            get() = this === Y

        val isHorizontal: Boolean
            get() = this === X || this === Z

        override fun toString(): String {
            return axisName
        }

        override fun test(p_test_1_: Direction?): Boolean {
            return p_test_1_ != null && p_test_1_.axis === this
        }

        val plane: Plane
            get() = when (this) {
                X, Z -> Plane.HORIZONTAL
                Y -> Plane.VERTICAL
                else -> throw Error("Someone's been tampering with the universe!")
            }

        abstract fun getCoordinate(x: Int, y: Int, z: Int): Int
        abstract fun getCoordinate(x: Double, y: Double, z: Double): Double

        companion object {
            private val NAME_LOOKUP: MutableMap<String, Axis> = Arrays.stream(values())
                    .collect(toMap({ it.axisName }, { p_199785_0_: Axis -> p_199785_0_ }))

            // TODO: Facing axis from Tensor
/*
        public static Facing.Axis fromTensor(Tensor v) {

        } */
            fun byName(name: String): Axis? {
                return NAME_LOOKUP[name.toLowerCase(Locale.ROOT)]
            }

            fun random(p_218393_0_: Random): Axis {
                return values()[p_218393_0_.nextInt(values().size)]
            }
        }

    }

    enum class AxisDirection(val offset: Int, private val description: String) {
        POSITIVE(1, "Towards positive"), NEGATIVE(-1, "Towards negative");

        override fun toString(): String {
            return description
        }

    }

    enum class Plane(private val facingValues: Array<Direction>, private val axisValues: Array<Axis>) : Iterable<Direction?>, Predicate<Direction?> {
        HORIZONTAL(arrayOf<Direction>(NORTH, EAST, SOUTH, WEST), arrayOf<Axis>(Axis.X, Axis.Z)), VERTICAL(arrayOf<Direction>(UP, DOWN), arrayOf<Axis>(Axis.Y));

        fun random(rand: Random): Direction {
            return facingValues[rand.nextInt(facingValues.size)]
        }

        override fun test(p_test_1_: Direction?): Boolean {
            return p_test_1_ != null && p_test_1_.axis.plane == this
        }

        override fun iterator(): MutableIterator<Direction> {
            return ArrayUtils.forArray(facingValues)
        }

    }

    companion object {
        private val VALUES = values()
        private val NAME_LOOKUP: MutableMap<String, Direction> = Arrays.stream(VALUES)
                .collect(toMap({ obj: Direction -> obj.dirName }) { p_199787_0_: Direction -> p_199787_0_ })
        private val BY_INDEX: Array<Direction> = Arrays.stream(VALUES)
                .sorted(Comparator.comparingInt { p_199790_0_: Direction -> p_199790_0_.index })
                .toArray { Array(it) { NORTH } }
        private val BY_HORIZONTAL_INDEX: Array<Direction> = Arrays.stream(VALUES)
                .filter { p_199786_0_: Direction -> p_199786_0_.axis.isHorizontal }
                .sorted(Comparator.comparingInt { p_199789_0_: Direction -> p_199789_0_.horizontalIndex })
                .toArray { Array(it) { NORTH } }

        private fun compose(first: Direction, second: Direction, third: Direction): Array<Direction> {
            return arrayOf(first, second, third, third.getOpposite(), second.getOpposite(), first.getOpposite())
        }

        fun byName(name: String?): Direction? {
            return if (name == null) null else NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT))
        }

        fun byIndex(index: Int): Direction {
            return BY_INDEX[MathUtils.abs(index % BY_INDEX.size)]
        }

        fun byHorizontalIndex(horizontalIndexIn: Int): Direction {
            return BY_HORIZONTAL_INDEX[MathUtils.abs(horizontalIndexIn % BY_HORIZONTAL_INDEX.size)]
        }

        fun fromAngle(angle: Double): Direction {
            return byHorizontalIndex(MathUtils.floor(angle / 90.0 + 0.5) and 3)
        }

        fun getFacingFromAxisDirection(axisIn: Axis?, AxisDirectionIn: AxisDirection): Direction {
            return when (axisIn) {
                Axis.X -> if (AxisDirectionIn == AxisDirection.POSITIVE) EAST else WEST
                Axis.Y -> if (AxisDirectionIn == AxisDirection.POSITIVE) UP else DOWN
                Axis.Z -> if (AxisDirectionIn == AxisDirection.POSITIVE) SOUTH else NORTH
                else -> if (AxisDirectionIn == AxisDirection.POSITIVE) SOUTH else NORTH
            }
        }

        fun random(rand: Random): Direction {
            return values()[rand.nextInt(values().size)]
        }

        fun getFacingFromTensor(x: Double, y: Double, z: Double): Direction {
            return getFacingFromTensor(Tensor(x, y, z))
        }

        fun getFacingFromTensor(t: Tensor): Direction {
            var facing = NORTH
            var f = Float.MIN_VALUE
            for (facing1 in VALUES) {
                val f1 = t.clone().mul(facing1.facingVec).reduce { v1, v2 -> v1 + v2 }
                if (f1 > f) {
                    f = f1.toFloat()
                    facing = facing1
                }
            }
            return facing
        }

        fun getFacingFromAxis(AxisDirectionIn: AxisDirection, axisIn: Axis?): Direction {
            for (Facing in values()) {
                if (Facing.axisDirection == AxisDirectionIn && Facing.axis === axisIn) {
                    return Facing
                }
            }
            throw IllegalArgumentException("No such Facing: $AxisDirectionIn $axisIn")
        }
    }

}