package dev.throwouterror.util

/**
 * @author Theo Paris https:/theoparis.com/about
 */
object PairListUtils {
    fun loadPairListDouble(array: IntArray): PairList<Int, Double> {
        val list = PairList<Int, Double>()
        var i = 0
        while (i < array.size) {
            list.add(array[i], java.lang.Double.longBitsToDouble(array[i + 1].toLong() shl 32 or ((array[i + 2] and 0xffffffffL.toInt()).toLong())))
            i += 3
        }
        return list
    }

    fun loadPairListInteger(array: IntArray): PairList<Int, Double> {
        val list = PairList<Int, Double>()
        var i = 0
        while (i < array.size) {
            list.add(array[i], array[i + 1].toDouble())
            i += 2
        }
        return list
    }

    fun loadPairListDouble(array: IntArray, from: Int, length: Int): PairList<Int, Double> {
        val list = PairList<Int, Double>()
        var i = from
        while (i < from + length) {
            list.add(array[i], java.lang.Double.longBitsToDouble(array[i + 1].toLong() shl 32 or ((array[i + 2] and 0xffffffffL.toInt()).toLong())))
            i += 3
        }
        return list
    }

    fun savePairListDouble(list: PairList<Int?, Double?>?): IntArray? {
        if (list == null) return null
        val array = IntArray(list.size * 3)
        for (i in list.indices) {
            val pair = list[i]
            array[i * 3] = pair!!.key!!
            val value = java.lang.Double.doubleToLongBits(pair.value!!)
            array[i * 3 + 1] = (value shr 32).toInt()
            array[i * 3 + 2] = value.toInt()
        }
        return array
    }

    fun savePairListInteger(list: PairList<Int?, Int?>?): IntArray? {
        if (list == null) return null
        val array = IntArray(list.size * 2)
        for (i in list.indices) {
            val pair = list[i]
            array[i * 2] = pair!!.key!!
            array[i * 2 + 1] = pair.value!!
        }
        return array
    }
}