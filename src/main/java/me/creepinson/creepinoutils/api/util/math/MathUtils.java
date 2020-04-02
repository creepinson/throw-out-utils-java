package me.creepinson.creepinoutils.api.util.math;

import java.util.Random;
import java.util.function.IntPredicate;

/**
 * @author Creepinson
 */
public class MathUtils {
    private static final float[] SIN_TABLE = new float[65536];

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
