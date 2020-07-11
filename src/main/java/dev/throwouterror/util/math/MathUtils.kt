package dev.throwouterror.util.math

import dev.throwouterror.util.ConditionUtil
import java.util.*
import java.util.function.IntPredicate
import kotlin.math.min
import kotlin.math.withSign

/**
 * @author Creepinson
 */
object MathUtils {
    private val SIN_TABLE = FloatArray(65536)
    fun floatsToDoubles(input: FloatArray?): DoubleArray? {
        if (input == null) {
            return null // Or throw an exception - your choice
        }
        val output = DoubleArray(input.size)
        for (i in input.indices) {
            output[i] = input[i].toDouble()
        }
        return output
    }

    fun doublesToFloats(input: DoubleArray?): FloatArray? {
        if (input == null) {
            return null // Or throw an exception - your choice
        }
        val output = FloatArray(input.size)
        for (i in input.indices) {
            output[i] = input[i].toFloat()
        }
        return output
    }

    fun doublesToInts(input: DoubleArray?): IntArray? {
        if (input == null) {
            return null // Or throw an exception - your choice
        }
        val output = IntArray(input.size)
        for (i in input.indices) {
            output[i] = input[i].toInt()
        }
        return output
    }

    /**
     * Returns the greatest common divisor of `a, b`. Returns `0` if
     * `a == 0 && b ==
     * 0`.
     *
     * @throws IllegalArgumentException if `a < 0` or `b < 0`
     */
    fun gcd(a: Int, b: Int): Int { /*
         * The reason we require both arguments to be >= 0 is because otherwise, what do
         * you return on gcd(0, Integer.MIN_VALUE)? BigInteger.gcd would return positive
         * 2^31, but positive 2^31 isn't an int.
         */
        var a = a
        var b = b
        ConditionUtil.checkNonNegative("a", a)
        ConditionUtil.checkNonNegative("b", b)
        if (a == 0) { // 0 % b == 0, so b divides a, but the converse doesn't hold.
            // BigInteger.gcd is consistent with this decision.
            return b
        } else if (b == 0) {
            return a // similar logic
        }
        /*
         * Uses the binary GCD algorithm; see
         * http://en.wikipedia.org/wiki/Binary_GCD_algorithm. This is >40% faster than
         * the Euclidean algorithm in benchmarks.
         */
        val aTwos = Integer.numberOfTrailingZeros(a)
        a = a shr aTwos // divide out all 2s
        val bTwos = Integer.numberOfTrailingZeros(b)
        b = b shr bTwos // divide out all 2s
        while (a != b) { // both a, b are odd
            // The key to the binary GCD algorithm is as follows:
            // Both a and b are odd. Assume a > b; then gcd(a - b, b) = gcd(a, b).
            // But in gcd(a - b, b), a - b is even and b is odd, so we can divide out powers
            // of two.
            // We bend over backwards to avoid branching, adapting a technique from
            // http://graphics.stanford.edu/~seander/bithacks.html#IntegerMinOrMax
            val delta = a - b // can't overflow, since a and b are nonnegative
            val minDeltaOrZero = delta and (delta shr Integer.SIZE - 1)
            // equivalent to Math.min(delta, 0)
            a = delta - minDeltaOrZero - minDeltaOrZero // sets a to Math.abs(a - b)
            // a is now nonnegative and even
            b += minDeltaOrZero // sets b to min(old a, b)
            a = a shr Integer.numberOfTrailingZeros(a) // divide out all 2s, since 2 doesn't divide b
        }
        return a shl Integer.min(aTwos, bTwos)
    }

    fun clampToInt(d: Double): Int {
        return if (d < Int.MAX_VALUE) {
            d.toInt()
        } else Int.MAX_VALUE
    }

    fun clamp(num: Int, min: Int, max: Int): Int {
        return if (num < min) {
            min
        } else {
            min(num, max)
        }
    }

    fun binarySearch(min: Int, max: Int, isTargetBeforeOrAt: IntPredicate): Int {
        var min = min
        var i = max - min
        while (i > 0) {
            val j = i / 2
            val k = min + j
            if (isTargetBeforeOrAt.test(k)) {
                i = j
            } else {
                min = k + 1
                i -= j + 1
            }
        }
        return min
    }

    fun lerp(pct: Float, start: Float, end: Float): Float {
        return start + pct * (end - start)
    }

    fun lerp(pct: Double, start: Double, end: Double): Double {
        return start + pct * (end - start)
    }

    fun lerp2(p_219804_0_: Double, p_219804_2_: Double, p_219804_4_: Double, p_219804_6_: Double,
              p_219804_8_: Double, p_219804_10_: Double): Double {
        return lerp(p_219804_2_, lerp(p_219804_0_, p_219804_4_, p_219804_6_),
                lerp(p_219804_0_, p_219804_8_, p_219804_10_))
    }

    fun lerp3(p_219807_0_: Double, p_219807_2_: Double, p_219807_4_: Double, p_219807_6_: Double,
              p_219807_8_: Double, p_219807_10_: Double, p_219807_12_: Double, p_219807_14_: Double, p_219807_16_: Double,
              p_219807_18_: Double, p_219807_20_: Double): Double {
        return lerp(p_219807_4_, lerp2(p_219807_0_, p_219807_2_, p_219807_6_, p_219807_8_, p_219807_10_, p_219807_12_),
                lerp2(p_219807_0_, p_219807_2_, p_219807_14_, p_219807_16_, p_219807_18_, p_219807_20_))
    }

    fun perlinFade(p_219801_0_: Double): Double {
        return p_219801_0_ * p_219801_0_ * p_219801_0_ * (p_219801_0_ * (p_219801_0_ * 6.0 - 15.0) + 10.0)
    }

    fun signum(p_219802_0_: Double): Int {
        return if (p_219802_0_ == 0.0) {
            0
        } else {
            if (p_219802_0_ > 0.0) 1 else -1
        }
    }

    fun randomInUnitCircle(rn: Random): FloatArray {
        val t = Math.PI.toFloat() * (2 * rn.nextFloat())
        val u = rn.nextFloat() + rn.nextFloat()
        val r = if (u > 1) 2 - u else u
        return floatArrayOf(r * kotlin.math.cos(t.toDouble()).toFloat(), r * kotlin.math.sin(t.toDouble()).toFloat())
    }

    fun chance(percent: Double): Boolean {
        return Math.random() < percent / 100
    }

    /**
     * Returns `true` if `a` and `b` are within `tolerance`
     * of each other.
     *
     *
     *
     * Technically speaking, this is equivalent to
     * `Math.abs(a - b) <= tolerance ||
     * Double.valueOf(a).equals(Double.valueOf(b))`.
     *
     *
     *
     * Notable special cases include:
     *
     *
     *  * All NaNs are fuzzily equal.
     *  * If `a == b`, then `a` and `b` are always fuzzily equal.
     *  * Positive and negative zero are always fuzzily equal.
     *  * If `tolerance` is zero, and neither `a` nor `b` is NaN,
     * then `a` and `b` are fuzzily equal if and only if `a == b`.
     *  * With [Double.POSITIVE_INFINITY] tolerance, all non-NaN values are
     * fuzzily equal.
     *  * With finite tolerance, `Double.POSITIVE_INFINITY` and `Double.NEGATIVE_INFINITY` are fuzzily equal only to themselves.
     *
     *
     *
     *
     * This is reflexive and symmetric, but *not* transitive, so it is
     * *not* an equivalence relation and *not* suitable for use in
     * [Object.equals] implementations.
     *
     * @throws IllegalArgumentException if `tolerance` is `< 0` or NaN
     * @since 13.0
     */
    fun fuzzyEquals(a: Double, b: Double, tolerance: Double): Boolean {
        ConditionUtil.checkNonNegative("tolerance", tolerance)
        return ((a - b).withSign(1.0) <= tolerance // copySign(x, 1.0) is a branch-free version of abs(x), but with different NaN
// semantics
                || a == b // needed to ensure that infinities equal themselves
                || java.lang.Double.isNaN(a) && java.lang.Double.isNaN(b))
    }

    fun abs(value: Float): Float {
        return if (value >= 0.0f) value else -value
    }

    fun abs(value: Int): Int {
        return if (value >= 0) value else -value
    }

    fun sin(value: Float): Float {
        return SIN_TABLE[(value * 10430.378f).toInt() and '\uffff'.toInt()]
    }

    fun cos(value: Float): Float {
        return SIN_TABLE[(value * 10430.378f + 16384.0f).toInt() and '\uffff'.toInt()]
    }

    fun sqrt(value: Float): Float {
        return kotlin.math.sqrt(value.toDouble()).toFloat()
    }

    fun sqrt(value: Double): Float {
        return kotlin.math.sqrt(value).toFloat()
    }

    fun floor(value: Float): Int {
        val i = value.toInt()
        return if (value < i.toFloat()) i - 1 else i
    }

    fun floor(value: Double): Int {
        val i = value.toInt()
        return if (value < i.toDouble()) i - 1 else i
    }

    fun lfloor(value: Double): Long {
        val i = value.toLong()
        return if (value < i.toDouble()) i - 1L else i
    }

    fun fma(a: Double, b: Double, c: Double): Double {
        return a * b + c
    }

    fun fma(a: Float, b: Float, c: Float): Float {
        return a * b + c
    }

    init {
        var j: Int = 0
        while (j < 65536) {
            SIN_TABLE[j] = kotlin.math.sin(j.toDouble() * 3.141592653589793 * 2.0 / 65536.0).toFloat()
            ++j
        }
    }
}