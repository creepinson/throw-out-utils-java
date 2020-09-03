package dev.throwouterror.util

import java.util.*
import java.util.stream.Collectors

/**
 * @author Creepinson https:/theoparis.com/about
 */
object ArrayUtils {
    fun toLinkedList(vararg array: Int): LinkedList<Int> {
        val list = LinkedList<Int>()
        for (i in array) {
            list.add(i)
        }
        return list
    }

    fun toList(vararg array: Int): ArrayList<Int> {
        val list = ArrayList<Int>()
        for (i in array) {
            list.add(i)
        }
        return list
    }

    fun toLinkedList(vararg array: Float): LinkedList<Float> {
        val list = LinkedList<Float>()
        for (i in array) {
            list.add(i)
        }
        return list
    }

    fun toList(vararg array: Float): ArrayList<Float> {
        val list = ArrayList<Float>()
        for (i in array) {
            list.add(i)
        }
        return list
    }

    fun toLinkedList(vararg array: Double): LinkedList<Double> {
        val list = LinkedList<Double>()
        for (i in array) {
            list.add(i)
        }
        return list
    }

    fun toList(vararg array: Double): ArrayList<Double> {
        val list = ArrayList<Double>()
        for (i in array) {
            list.add(i)
        }
        return list
    }

    fun isArray(obj: Any?): Boolean {
        return obj != null && obj.javaClass.isArray
    }

    fun <T> forArray(arr: Array<T>): MutableIterator<T> {
        return mutableListOf(*arr).iterator()
    }

    /**
     * Simple helper function to **create** and fill an array with the
     * specified value.
     *
     * @return the array being filled
     */
    fun <T> fill(size: Int, value: T): Array<T> {
        return fill(getArray(size), value)
    }

    /**
     * Simple helper function to fill an array with the specified value.
     *
     * @return the array being filled
     */
    fun <T> fillList(a: ArrayList<T>, `val`: T): ArrayList<T> {
        return a.stream().map { `val` }.collect(Collectors.toList()) as ArrayList<T>
    }

    /**
     * Simple helper function to **create** and fill an array with the
     * specified value.
     *
     * @return the array being filled
     */
    fun <T> fillList(size: Int, value: T): ArrayList<T> {
        return fillList(ArrayList(size), value)
    }

    /**
     * Simple helper function to fill an array with the specified value.
     *
     * @return the array being filled
     */
    fun <T> fill(a: Array<T>, `val`: T): Array<T> {
        Arrays.fill(a, `val`)
        return a
    }

    fun <T> getArray(size: Int): Array<T> {
        val arr = arrayOfNulls<Any>(size)
        return arr as Array<T>
    }

    fun toFloatArray(data: DoubleArray): FloatArray {
        val result = FloatArray(data.size)
        for (i in data.indices) {
            result[i] = data[i].toFloat()
        }
        return result
    }

    fun toFloatArray(data: IntArray): FloatArray {
        val result = FloatArray(data.size)
        for (i in data.indices) {
            result[i] = data[i].toFloat()
        }
        return result
    }

    fun toDoubleArray(data: FloatArray): DoubleArray {
        val result = DoubleArray(data.size)
        for (i in data.indices) {
            result[i] = data[i].toDouble()
        }
        return result
    }

    /**
     * Helper function to multiply the values in a list.
     */
    fun multiply(data: List<Int>): Int {
        var result = 1
        for (i in data) {
            result *= i
        }
        return result
    }

    /**
     * Helper function to multiply the values in a list.
     */
    fun multiplyf(data: List<Float>): Float {
        var result = 1f
        for (i in data) {
            result *= i
        }
        return result
    }

    /**
     * Helper function to multiply the values in a list.
     */
    fun multiplyd(data: List<Double>): Double {
        var result = 1.0
        for (i in data) {
            result *= i
        }
        return result
    }
}