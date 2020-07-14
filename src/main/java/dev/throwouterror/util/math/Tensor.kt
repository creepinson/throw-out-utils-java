package dev.throwouterror.util.math

import dev.throwouterror.util.ArrayUtils
import dev.throwouterror.util.ISerializable
import dev.throwouterror.util.math.rotation.RotationMatrix
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors
import kotlin.math.sqrt

class Tensor : ISerializable<Tensor>, Cloneable, Iterable<Double> {
    var data: LinkedList<Double>
        protected set
    var dimensions: LinkedList<Int>
        protected set

    /**
     * Creates an empty Tensor of n-dimensions filled with zeroes.
     */
    constructor(dimensions: IntArray) {
        this.dimensions = ArrayUtils.toLinkedList(*dimensions)
        data = LinkedList(ArrayUtils.fillList(ArrayUtils.multiply(this.dimensions), 0.0))
    }

    /**
     * Creates a new tensor with the specified n-dimensional data.
     */
    constructor(vararg data: Double) {
        this.data = ArrayUtils.toLinkedList(*data)
        dimensions = ArrayUtils.toLinkedList(data.size)
    }

    /**
     * Creates a new tensor with the specified n-dimensional data.
     */
    constructor(vararg data: Float) {
        this.data = LinkedList(data.map { it.toDouble() })
        dimensions = ArrayUtils.toLinkedList(data.size)
    }

    /**
     * Creates a new tensor with the specified n-dimensional data. This constructor
     * is used for cloning.
     */
    constructor(data: DoubleArray, size: IntArray) {
        this.data = ArrayUtils.toLinkedList(*data)
        dimensions = ArrayUtils.toLinkedList(*size)
    }

    /**
     * Creates a new tensor with the specified n-dimensional data. This constructor
     * is used for cloning.
     */
    constructor(data: LinkedList<Double>, size: LinkedList<Int>) {
        this.data = data
        dimensions = size
    }


    var x: Double
        get() = data[0]
        set(value) {
            data[0] = value
        }

    var y: Double
        get() = data[1]
        set(value) {
            data[1] = value
        }

    var z: Double
        get() = data[2]
        set(value) {
            data[2] = value
        }

    var w: Double
        get() = data[3]
        set(value) {
            data[3] = value
        }

    @JvmOverloads
    fun offset(facing: Direction, n: Int = 1): Tensor {
        return if (n == 0) this else clone().add(facing.directionVec).mul(n)
    }

    /**
     * @return This tensor's data in a form of a multidimensional list.
     */
    fun toChunkedList(): LinkedList<*> =
            go(data)

    /**
     * @return A string representation of the multidimensional list.
     */
    fun toChunkedString(): String =
            this.toList().toTypedArray().contentDeepToString()

    /**
     * @return This tensor's data in a form of a multidimensional array.
     */
    fun toChunkedArray(): Array<*> =
            go(data).toArray()

    fun toDoubleArray(): DoubleArray =
            data.toDoubleArray()

    fun toFloatArray(): FloatArray =
            data.map { it.toFloat() }.toFloatArray()

    fun toIntArray(): IntArray =
            data.map { it.toInt() }.toIntArray()

    private fun go(arr: List<Double>): LinkedList<*> {
        val s = dimensions.pop()
        val result = arr.chunked(s)
        dimensions.push(s)
        return if (result.size > 1) LinkedList<Any?>(result.stream().map { arr: List<Double> -> go(arr) }.collect(Collectors.toList())) else LinkedList<Any?>(arr)
    }

    /**
     * Returns the length of the Tensor.
     */
    fun length(): Int {
        return Math.sqrt(lengthSquared().toDouble()).toInt()
    }

    /**
     * Returns the length of the Tensor.
     */
    fun lengthSquared(): Int {
        return ArrayUtils.multiplyd(data).toInt()
    }

    /**
     * @return the current Tensor that has been changed (**not a new
     * one**). Normaliz()es the Tensor to length 1. Note that this
     * Tensor uses int values for coordinates.
     */
    fun normalize(): Tensor {
        val amt = length()
        if (amt == 0) return this
        this.map(Function { v: Double -> v * amt })
        return this
    }

    /**
     * @return the current Tensor that has been changed (**not a new
     * one**). Equivalent to mul(-1).
     */
    fun reverse(): Tensor {
        mul(-1)
        return this
    }

    /**
     * @param other to add
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun add(other: Tensor): Tensor {
        for (f in data.indices) {
            data[f] = f + other.data[f]
        }
        return this
    }

    fun add(factor: Float): Tensor {
        return this.map(Function { v: Double -> v + factor })
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun add(factor: Double): Tensor {
        return add(factor.toFloat())
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun add(factor: Int): Tensor {
        return add(factor.toFloat())
    }

    /**
     * @param other to subtract
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun sub(other: Tensor): Tensor {
        for (f in data.indices) {
            data[f] = f - other.data[f]
        }
        return this
    }

    fun sub(factor: Float): Tensor {
        return this.map(Function { v: Double -> v - factor })
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun sub(factor: Double): Tensor {
        return sub(factor.toFloat())
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun sub(factor: Int): Tensor {
        return sub(factor.toFloat())
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun mul(factor: Float): Tensor {
        this.map(Function { v: Double -> v * factor })
        return this
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun mul(factor: Double): Tensor {
        return mul(factor.toFloat())
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (**not a new
     * one**).
     */
    fun mul(factor: Int): Tensor {
        return mul(factor.toFloat())
    }

    fun mul(other: Tensor): Tensor {
        for (f in data.indices) {
            data[f] = f * other.data[f]
        }
        return this
    }

    override fun toString(): String {
        return data.toString()
    }

    override fun fromString(s: String): Tensor {
        data = LinkedList(
                Arrays.stream(s.split(",").toTypedArray()).mapToDouble { s: String -> s.toDouble() }.boxed().collect(Collectors.toList()))
        return this
    }

    /**
     * Calculates the cross-product of the given Tensors.
     */
    fun cross(vec2: Tensor): Tensor {
        return Tensor(y * vec2.z - z * vec2.y, z * vec2.x - x * vec2.z,
                x * vec2.y - y * vec2.x)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Tensor -> {
                other.data == data
            }
            is Number -> {
                data.stream().allMatch { v: Double -> v == other.toDouble() }
            }
            else -> false
        }
    }

    fun distanceTo(other: Tensor): Float {
        var result = 0f
        for (f in data.indices) {
            result *= (data[f] - other.data[f]).toFloat()
        }
        return sqrt(result.toDouble()).toFloat()
    }

    fun map(valueMapper: Function<Double, Double?>?): Tensor {
        data = LinkedList(data.stream().map(valueMapper).collect(Collectors.toList()))
        return this
    }

    fun forEach(action: Consumer<in Double?>) {
        data.stream().forEach(action)
    }

    fun contains(min: Tensor, max: Tensor): Boolean {
        for (i in data.indices) {
            if (min.data[i] > data[i] || max.data[i] < data[i]) return false
        }
        return true
    }

    fun setValueByAxis(axis: Direction.Axis?, valueAt: Double): Tensor {
        when (axis) {
            Direction.Axis.X -> data[0] = valueAt
            Direction.Axis.Y -> data[1] = valueAt
            Direction.Axis.Z -> data[2] = valueAt
        }
        return this
    }

    fun getValueByAxis(axis: Direction.Axis?): Double {
        when (axis) {
            Direction.Axis.X -> return data[0]
            Direction.Axis.Y -> return data[1]
            Direction.Axis.Z -> return data[2]
        }
        return 0.0
    }

    operator fun set(index: Int, value: Double): Tensor {
        data[index] = value
        return this
    }

    fun setValues(vararg values: Double): Tensor {
        for (i in values.indices) {
            data[i] = values[i]
        }
        return this
    }

    operator fun get(index: Int): Double {
        return data[index]
    }

    /**
     * Multiply the given matrix with this Vector3f and store the result in `this`.
     *
     * @param mat the matrix
     * @return this
     */
    fun mul(mat: RotationMatrix): Tensor {
        this.setValues(
                MathUtils.fma(mat.m00, x, MathUtils.fma(mat.m10, y, mat.m20 * z)),
                MathUtils.fma(mat.m01, x, MathUtils.fma(mat.m11, y, mat.m21 * z)),
                MathUtils.fma(mat.m02, x, MathUtils.fma(mat.m12, y, mat.m22 * z))
        )
        return this
    }

    fun mul(mat: RotationMatrix, dest: Tensor): Tensor {
        dest.setValues(
                MathUtils.fma(mat.m00, x, MathUtils.fma(mat.m10, y, mat.m20 * z)),
                MathUtils.fma(mat.m01, x, MathUtils.fma(mat.m11, y, mat.m21 * z)),
                MathUtils.fma(mat.m02, x, MathUtils.fma(mat.m12, y, mat.m22 * z))
        )
        return dest
    }

    fun getNumber(index: Int): Number {
        return data[index]
    }

    /**
     * Duplicates this tensor with the same dimensions and data.
     */
    public override fun clone(): Tensor {
        return Tensor(data, dimensions)
    }

    override fun iterator(): MutableIterator<Double> {
        return object : MutableIterator<Double> {
            private var currentIndex = 0
            override fun hasNext(): Boolean {
                return currentIndex < data.size
            }

            override fun next(): Double {
                return data[currentIndex++]
            }

            override fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + dimensions.hashCode()
        return result
    }

    val isEmpty: Boolean
        get() = data.stream().allMatch { v: Double -> v == 0.0 }

    companion object {
        private const val serialVersionUID = 6106298603154164750L
        /**
         * An empty Scalar with 1 dimension.
         */
        val ZERO = Tensor(intArrayOf(1))
        /**
         * An empty Tensor with 3 dimensions.
         */
        val ZERO_VECTOR = Tensor(intArrayOf(3))

        @JvmStatic
        fun intersects(min: Tensor, max: Tensor): Boolean {
            for (i in min.data.indices) {
                if (min.data[i] > max.data[i] || max.data[i] < max.data[i]) return false
            }
            return true
        }
    }
}

fun List<Number>.toTensor(): Tensor = Tensor(*this.map { it.toDouble() }.toDoubleArray())