package me.creepinson.creepinoutils.api.util.math;

import me.creepinson.creepinoutils.api.util.ConditionUtil;

import java.util.Random;
import java.util.function.IntPredicate;

import static java.lang.Integer.min;

/**
 * @author Creepinson
 */
public class MathUtils {
    private static final float[] SIN_TABLE = new float[65536];

    /**
     * Returns the greatest common divisor of {@code a, b}. Returns {@code 0} if {@code a == 0 && b ==
     * 0}.
     *
     * @throws IllegalArgumentException if {@code a < 0} or {@code b < 0}
     */
    public static int gcd(int a, int b) {
        /*
         * The reason we require both arguments to be >= 0 is because otherwise, what do you return on
         * gcd(0, Integer.MIN_VALUE)? BigInteger.gcd would return positive 2^31, but positive 2^31 isn't
         * an int.
         */
        ConditionUtil.checkNonNegative("a", a);
        ConditionUtil.checkNonNegative("b", b);
        if (a == 0) {
            // 0 % b == 0, so b divides a, but the converse doesn't hold.
            // BigInteger.gcd is consistent with this decision.
            return b;
        } else if (b == 0) {
            return a; // similar logic
        }
        /*
         * Uses the binary GCD algorithm; see http://en.wikipedia.org/wiki/Binary_GCD_algorithm. This is
         * >40% faster than the Euclidean algorithm in benchmarks.
         */
        int aTwos = Integer.numberOfTrailingZeros(a);
        a >>= aTwos; // divide out all 2s
        int bTwos = Integer.numberOfTrailingZeros(b);
        b >>= bTwos; // divide out all 2s
        while (a != b) { // both a, b are odd
            // The key to the binary GCD algorithm is as follows:
            // Both a and b are odd. Assume a > b; then gcd(a - b, b) = gcd(a, b).
            // But in gcd(a - b, b), a - b is even and b is odd, so we can divide out powers of two.

            // We bend over backwards to avoid branching, adapting a technique from
            // http://graphics.stanford.edu/~seander/bithacks.html#IntegerMinOrMax

            int delta = a - b; // can't overflow, since a and b are nonnegative

            int minDeltaOrZero = delta & (delta >> (Integer.SIZE - 1));
            // equivalent to Math.min(delta, 0)

            a = delta - minDeltaOrZero - minDeltaOrZero; // sets a to Math.abs(a - b)
            // a is now nonnegative and even

            b += minDeltaOrZero; // sets b to min(old a, b)
            a >>= Integer.numberOfTrailingZeros(a); // divide out all 2s, since 2 doesn't divide b
        }
        return a << min(aTwos, bTwos);
    }

    static {
        int j;
        for (j = 0; j < 65536; ++j) {
            SIN_TABLE[j] = (float) Math.sin((double) j * 3.141592653589793D * 2.0D / 65536.0D);
        }
    }

    public static int clampToInt(double d) {
        if (d < Integer.MAX_VALUE) {
            return (int) d;
        }
        return Integer.MAX_VALUE;
    }

    public static int clamp(int num, int min, int max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }


    public static int binarySearch(int min, int max, IntPredicate isTargetBeforeOrAt) {
        int i = max - min;

        while (i > 0) {
            int j = i / 2;
            int k = min + j;
            if (isTargetBeforeOrAt.test(k)) {
                i = j;
            } else {
                min = k + 1;
                i -= j + 1;
            }
        }

        return min;
    }

    public static float lerp(float pct, float start, float end) {
        return start + pct * (end - start);
    }

    public static double lerp(double pct, double start, double end) {
        return start + pct * (end - start);
    }

    public static double lerp2(double p_219804_0_, double p_219804_2_, double p_219804_4_, double p_219804_6_, double p_219804_8_, double p_219804_10_) {
        return lerp(p_219804_2_, lerp(p_219804_0_, p_219804_4_, p_219804_6_), lerp(p_219804_0_, p_219804_8_, p_219804_10_));
    }

    public static double lerp3(double p_219807_0_, double p_219807_2_, double p_219807_4_, double p_219807_6_, double p_219807_8_, double p_219807_10_, double p_219807_12_, double p_219807_14_, double p_219807_16_, double p_219807_18_, double p_219807_20_) {
        return lerp(p_219807_4_, lerp2(p_219807_0_, p_219807_2_, p_219807_6_, p_219807_8_, p_219807_10_, p_219807_12_), lerp2(p_219807_0_, p_219807_2_, p_219807_14_, p_219807_16_, p_219807_18_, p_219807_20_));
    }

    public static double perlinFade(double p_219801_0_) {
        return p_219801_0_ * p_219801_0_ * p_219801_0_ * (p_219801_0_ * (p_219801_0_ * 6.0D - 15.0D) + 10.0D);
    }

    public static int signum(double p_219802_0_) {
        if (p_219802_0_ == 0.0D) {
            return 0;
        } else {
            return p_219802_0_ > 0.0D ? 1 : -1;
        }
    }

    public static float[] randomInUnitCircle(Random rn) {
        float t = (float) Math.PI * (2 * rn.nextFloat());
        float u = rn.nextFloat() + rn.nextFloat();
        float r = (u > 1) ? 2 - u : u;

        return new float[]{r * (float) Math.cos(t), r * (float) Math.sin(t)};
    }

    public static boolean chance(double percent) {
        if (Math.random() < percent / 100) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns {@code true} if {@code a} and {@code b} are within {@code tolerance} of each other.
     *
     * <p>Technically speaking, this is equivalent to {@code Math.abs(a - b) <= tolerance ||
     * Double.valueOf(a).equals(Double.valueOf(b))}.
     *
     * <p>Notable special cases include:
     *
     * <ul>
     *   <li>All NaNs are fuzzily equal.
     *   <li>If {@code a == b}, then {@code a} and {@code b} are always fuzzily equal.
     *   <li>Positive and negative zero are always fuzzily equal.
     *   <li>If {@code tolerance} is zero, and neither {@code a} nor {@code b} is NaN, then {@code a}
     *       and {@code b} are fuzzily equal if and only if {@code a == b}.
     *   <li>With {@link Double#POSITIVE_INFINITY} tolerance, all non-NaN values are fuzzily equal.
     *   <li>With finite tolerance, {@code Double.POSITIVE_INFINITY} and {@code
     *       Double.NEGATIVE_INFINITY} are fuzzily equal only to themselves.
     * </ul>
     *
     * <p>This is reflexive and symmetric, but <em>not</em> transitive, so it is <em>not</em> an
     * equivalence relation and <em>not</em> suitable for use in {@link Object#equals}
     * implementations.
     *
     * @throws IllegalArgumentException if {@code tolerance} is {@code < 0} or NaN
     * @since 13.0
     */
    public static boolean fuzzyEquals(double a, double b, double tolerance) {
        ConditionUtil.checkNonNegative("tolerance", tolerance);
        return Math.copySign(a - b, 1.0) <= tolerance
                // copySign(x, 1.0) is a branch-free version of abs(x), but with different NaN semantics
                || (a == b) // needed to ensure that infinities equal themselves
                || (Double.isNaN(a) && Double.isNaN(b));
    }

    public static float abs(float value) {
        return value >= 0.0F ? value : -value;
    }

    public static int abs(int value) {
        return value >= 0 ? value : -value;
    }

    public static float sin(float value) {
        return SIN_TABLE[(int) (value * 10430.378F) & '\uffff'];
    }

    public static float cos(float value) {
        return SIN_TABLE[(int) (value * 10430.378F + 16384.0F) & '\uffff'];
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt((double) value);
    }

    public static float sqrt(double value) {
        return (float) Math.sqrt(value);
    }

    public static int floor(float value) {
        int i = (int) value;
        return value < (float) i ? i - 1 : i;
    }

    public static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

    public static long lfloor(double value) {
        long i = (long) value;
        return value < (double) i ? i - 1L : i;
    }

}
