package dev.throwouterror.util

import java.util.*

class RangedBitSet(var min: Int, var max: Int) {
    protected var set: BitSet

    fun add(value: Int) {
        if (value >= min && value <= max) set[value - min] = true
    }

    val ranges: List<BitRange>
        get() {
            val ranges: MutableList<BitRange> = ArrayList()
            var index = 0
            while (index < max - min) {
                var nextIndex = set.nextSetBit(index + 1)
                if (nextIndex == -1) nextIndex = max - min
                ranges.add(BitRange(index + min, nextIndex + min))
                index = nextIndex
            }
            return ranges
        }

    class BitRange(val min: Int, val max: Int)

    init {
        require(min < max) { "min has to be smaller than max!" }
        set = BitSet(max - min)
    }
}