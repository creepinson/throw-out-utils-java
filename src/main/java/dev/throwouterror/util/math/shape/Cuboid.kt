package dev.throwouterror.util.math.shape

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import dev.throwouterror.util.math.Tensor
import dev.throwouterror.util.math.Tensor.Companion.intersects
import java.io.Serializable
import java.lang.reflect.Type
import kotlin.math.max
import kotlin.math.min

class Cuboid : Cloneable, Serializable {
    var minX: Double
        get() = minPoint.x
        set(value) {
            minPoint.x = value
        }
    var minY: Double
        get() = minPoint.y
        set(value) {
            minPoint.y = value
        }
    var minZ: Double
        get() = minPoint.z
        set(value) {
            minPoint.z = value
        }

    var maxX: Double
        get() = maxPoint.x
        set(value) {
            maxPoint.x = value
        }
    var maxY: Double
        get() = maxPoint.y
        set(value) {
            maxPoint.y = value
        }
    var maxZ: Double
        get() = maxPoint.z
        set(value) {
            maxPoint.z = value
        }


    class Serializer : JsonSerializer<Cuboid> {
        override fun serialize(src: Cuboid, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            val `object` = JsonObject()
            `object`.addProperty("minX", src.minX)
            `object`.addProperty("minY", src.minY)
            `object`.addProperty("minZ", src.minZ)
            `object`.addProperty("maxX", src.maxX)
            `object`.addProperty("maxY", src.maxY)
            `object`.addProperty("maxZ", src.maxZ)
            `object`.addProperty("size", src.type.toString())
            val centerObj = JsonObject()
            centerObj.addProperty("x", src.center.x)
            centerObj.addProperty("y", src.center.y)
            centerObj.addProperty("z", src.center.z)
            `object`.add("center", centerObj)
            return `object`
        }
    }

    public override fun clone(): Cuboid {
        return Cuboid(this)
    }

    val type: SizeType
        get() {
            var type = if (isEmpty) SizeType.AIR else SizeType.OTHER
            if (maxPoint.equals(1)) type = SizeType.FULL
            return type
        }

    /**
     * @return Returns whether or not the maximum point's values are 0.
     */
    val isEmpty: Boolean
        get() = maxPoint.isEmpty

    /**
     * '
     *
     * @return the Tensor containing the minimum point of this cuboid
     */
    val minPoint: Tensor

    /**
     * '
     *
     * @return the Tensor containing the maximum point of this cuboid
     */
    val maxPoint: Tensor

    /**
     * Creates a cuboid with a minimum and maximum point.
     */
    constructor(p1: Tensor, p2: Tensor) {
        minPoint = p1.clone()
        maxPoint = p2.clone()
    }

    constructor(loc: Tensor) : this(loc, loc)
    constructor(other: Cuboid) : this(other.minPoint, other.maxPoint)

    constructor(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float) {
        minPoint = Tensor(x1.toDouble(), y1.toDouble(), z1.toDouble())
        maxPoint = Tensor(x2.toDouble(), y2.toDouble(), z2.toDouble())
    }

    constructor(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int) {
        minPoint = Tensor(x1.toDouble(), y1.toDouble(), z1.toDouble())
        maxPoint = Tensor(x2.toDouble(), y2.toDouble(), z2.toDouble())
    }

    constructor(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double) {
        minPoint = Tensor(x1, y1, z1)
        maxPoint = Tensor(x2, y2, z2)
    }

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> {
                true
            }
            other !is Cuboid -> {
                false
            }
            else -> {
                other.minPoint == minPoint && other.maxPoint == maxPoint
            }
        }
    }

    /**
     * Creates a new [Cuboid] that has been contracted by the given amount,
     * with positive changes decreasing max values and negative changes increasing
     * min values. <br></br>
     * If the amount to contract by is larger than the length of a side, then the
     * side will wrap (still creating a valid Cuboid - see last sample).
     *
     * <h3>See Also:</h3>
     *
     *  * [.expand] - like this, except for
     * expanding.
     *  * [.grow] and [.grow] - expands in
     * all directions.
     *  * [.shrink] - contracts in all directions (like
     * [.grow])
     *
     *
     * @return A new modified bounding box.
     */
    fun contract(x: Float, y: Float, z: Float): Cuboid {
        var d0 = minX.toFloat()
        var d1 = minY.toFloat()
        var d2 = minZ.toFloat()
        var d3 = maxX.toFloat()
        var d4 = maxY.toFloat()
        var d5 = maxZ.toFloat()
        if (x < 0.0) {
            d0 -= x
        } else if (x > 0.0) {
            d3 -= x
        }
        if (y < 0.0) {
            d1 -= y
        } else if (y > 0.0) {
            d4 -= y
        }
        if (z < 0.0) {
            d2 -= z
        } else if (z > 0.0) {
            d5 -= z
        }
        return Cuboid(d0, d1, d2, d3, d4, d5)
    }

    /**
     * Creates a new [Cuboid] that has been expanded by the given amount, with
     * positive changes increasing max values and negative changes decreasing min
     * values.
     *
     * <h3>See Also:</h3>
     *
     *  * [.contract] - like this, except for
     * shrinking.
     *  * [.grow] and [.grow] - expands in
     * all directions.
     *  * [.shrink] - contracts in all directions (like
     * [.grow])
     *
     *
     * @return A modified bounding box that will always be equal or greater in
     * volume to this bounding box.
     */
    fun expand(x: Float, y: Float, z: Float): Cuboid {
        var d0 = minX.toFloat()
        var d1 = minY.toFloat()
        var d2 = minZ.toFloat()
        var d3 = maxX.toFloat()
        var d4 = maxY.toFloat()
        var d5 = maxZ.toFloat()
        if (x < 0.0) {
            d0 += x
        } else if (x > 0.0) {
            d3 += x
        }
        if (y < 0.0) {
            d1 += y
        } else if (y > 0.0) {
            d4 += y
        }
        if (z < 0.0) {
            d2 += z
        } else if (z > 0.0) {
            d5 += z
        }
        return Cuboid(d0, d1, d2, d3, d4, d5)
    }

    /**
     * Creates a new [Cuboid] that has been contracted by the given amount in
     * both directions. Negative values will shrink the Cuboid instead of expanding
     * it. <br></br>
     * Side lengths will be increased by 2 times the value of the parameters, since
     * both min and max are changed. <br></br>
     * If contracting and the amount to contract by is larger than the length of a
     * side, then the side will wrap (still creating a valid Cuboid - see last ample)
     *
     * <h3>See Also:</h3>
     *
     *  * [.expand] - expands in only one
     * direction.
     *  * [.contract] - contracts in only one
     * direction. <lu>[.grow] - version of this that expands in
     * all directions from one parameter.
     *  * [.shrink] - contracts in all directions
    </lu> *
     *
     * @return A modified bounding box.
     */
    fun grow(x: Float, y: Float, z: Float): Cuboid {
        val d0 = minX - x
        val d1 = minY - y
        val d2 = minZ - z
        val d3 = maxX + x
        val d4 = maxY + y
        val d5 = maxZ + z
        return Cuboid(d0, d1, d2, d3, d4, d5)
    }

    /**
     * Creates a new [Cuboid] that is expanded by the given value in all
     * directions. Equivalent to [.grow] with the given
     * value for all 3 params. Negative values will shrink the Cuboid. <br></br>
     * Side lengths will be increased by 2 times the value of the parameter, since
     * both min and max are changed. <br></br>
     * If contracting and the amount to contract by is larger than the length of a
     * side, then the side will wrap (still creating a valid Cuboid - see samples on
     * [.grow]).
     *
     * @return A modified Cuboid.
     */
    fun grow(value: Float): Cuboid {
        return this.grow(value, value, value)
    }

    fun intersect(other: Cuboid): Cuboid {
        val d0 = max(minX, other.minX).toFloat()
        val d1 = max(minY, other.minY).toFloat()
        val d2 = max(minZ, other.minZ).toFloat()
        val d3 = min(maxX, other.maxX).toFloat()
        val d4 = min(maxY, other.maxY).toFloat()
        val d5 = min(maxZ, other.maxZ).toFloat()
        return Cuboid(d0, d1, d2, d3, d4, d5)
    }

    fun union(other: Cuboid): Cuboid {
        val d0 = min(minX, other.minX).toFloat()
        val d1 = min(minY, other.minY).toFloat()
        val d2 = min(minZ, other.minZ).toFloat()
        val d3 = max(maxX, other.maxX).toFloat()
        val d4 = max(maxY, other.maxY).toFloat()
        val d5 = max(maxZ, other.maxZ).toFloat()
        return Cuboid(d0, d1, d2, d3, d4, d5)
    }

    /**
     * Offsets the current bounding box by the specified amount.
     */
    fun offset(x: Float, y: Float, z: Float): Cuboid {
        return Cuboid(minX + x, minY + y, minZ + z, maxX + x, maxY + y,
                maxZ + z)
    }

    fun offset(vec: Tensor?): Cuboid {
        return Cuboid(minPoint.clone().add(vec!!), maxPoint.clone().add(vec))
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z
     * dimensions, calculate the offset between them in the X dimension. return var2
     * if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset. Otherwise return the calculated offset.
     */
    fun calculateXOffset(other: Cuboid, xOffset: Double): Double {
        var offsetX = xOffset
        return if (other.maxY > minY && other.minY < maxY && other.maxZ > minZ && other.minZ < maxZ) {
            if (offsetX > 0.0 && other.maxX <= minX) {
                val d1 = minX - other.maxX.toFloat()
                if (d1 < offsetX) {
                    offsetX = d1
                }
            } else if (offsetX < 0.0 && other.minX >= maxX) {
                val d0 = maxX - other.minX.toFloat()
                if (d0 > offsetX) {
                    offsetX = d0
                }
            }
            offsetX
        } else {
            offsetX
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z
     * dimensions, calculate the offset between them in the Y dimension. return var2
     * if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset. Otherwise return the calculated offset.
     */
    fun calculateYOffset(other: Cuboid, yOffset: Double): Double {
        var offsetY = yOffset
        return if (other.maxX > minX && other.minX < maxX && other.maxZ > minZ && other.minZ < maxZ) {
            if (offsetY > 0.0 && other.maxY <= minY) {
                val d1 = minY - other.maxY.toFloat()
                if (d1 < offsetY) {
                    offsetY = d1
                }
            } else if (offsetY < 0.0 && other.minY >= maxY) {
                val d0 = maxY - other.minY.toFloat()
                if (d0 > offsetY) {
                    offsetY = d0
                }
            }
            offsetY
        } else {
            offsetY
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X
     * dimensions, calculate the offset between them in the Z dimension. return var2
     * if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset. Otherwise return the calculated offset.
     */
    fun calculateZOffset(other: Cuboid, zOffset: Double): Double {
        var offsetZ = zOffset
        return if (other.maxX > minX && other.minX < maxX && other.maxY > minY && other.minY < maxY) {
            if (offsetZ > 0.0 && other.maxZ <= minZ) {
                val d1 = minZ - other.maxZ.toFloat()
                if (d1 < offsetZ) {
                    offsetZ = d1
                }
            } else if (offsetZ < 0.0 && other.minZ >= maxZ) {
                val d0 = maxZ - other.minZ.toFloat()
                if (d0 > offsetZ) {
                    offsetZ = d0
                }
            }
            offsetZ
        } else {
            offsetZ
        }
    }

    /**
     * Checks if the bounding box intersects with another.
     */
    fun intersects(other: Cuboid?): Boolean {
        return intersects(minPoint, maxPoint)
    }

    /**
     * Returns if the supplied Tensor is completely inside the bounding box
     */
    operator fun contains(t: Tensor): Boolean {
        return t.contains(minPoint, maxPoint)
    }

    fun contains(vararg data: Double): Boolean {
        return contains(Tensor(*data))
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    val averageEdgeLength: Double
        get() {
            val d0 = maxX - minX.toFloat()
            val d1 = maxY - minY.toFloat()
            val d2 = maxZ - minZ.toFloat()
            return (d0 + d1 + d2) / 3.0
        }

    val xSize: Float
        get() = (maxX - minX).toFloat()

    val ySize: Float
        get() = (maxY - minY).toFloat()

    val zSize: Float
        get() = (maxZ - minZ).toFloat()

    /**
     * Creates a new [Cuboid] that is expanded by the given value in all
     * directions. Equivalent to [.grow] with value set to the negative
     * of the value provided here. Passing a negative value to this method values
     * will grow the Cuboid. <br></br>
     * Side lengths will be decreased by 2 times the value of the parameter, since
     * both min and max are changed. <br></br>
     * If contracting and the amount to contract by is larger than the length of a
     * side, then the side will wrap (still creating a valid Cuboid - see samples on
     * [.grow]).
     *
     * @return A modified Cuboid.
     */
    fun shrink(value: Float): Cuboid {
        return this.grow(-value)
    }

    override fun toString(): String {
        return ("" + minX + "," + minY + "," + minZ + ":" + maxX + "," + maxY + ","
                + maxZ + "")
    }

    fun hasNaN(): Boolean {
        return (java.lang.Double.isNaN(minX.toDouble()) || java.lang.Double.isNaN(minY.toDouble()) || java.lang.Double.isNaN(minZ.toDouble())
                || java.lang.Double.isNaN(maxX.toDouble()) || java.lang.Double.isNaN(maxY.toDouble()) || java.lang.Double.isNaN(maxZ.toDouble()))
    }

    val center: Tensor
        get() = Tensor((minX + (maxX - minX) * 0.5f).toDouble(),
                (minY + (maxY - minY) * 0.5f).toDouble(), (minZ + (maxZ - minZ) * 0.5f).toDouble())

    enum class SizeType {
        FULL, AIR, OTHER;

        override fun toString(): String {
            return name.toLowerCase()
        }
    }

    companion object {
        val ZERO = Cuboid(0, 0, 0, 0, 0, 0)
        val FULL_CUBE = Cuboid(0, 0, 0, 1, 1, 1)
    }
}