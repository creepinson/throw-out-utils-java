package me.creepinson.creepinoutils.api.util.math;

import java.util.Random;

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
