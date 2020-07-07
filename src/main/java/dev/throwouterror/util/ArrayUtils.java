package dev.throwouterror.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public class ArrayUtils {

    public static LinkedList<Integer> toLinkedList(int... array) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i : array) {
            list.add(i);
        }
        return list;
    }

    public static ArrayList<Integer> toList(int... array) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i : array) {
            list.add(i);
        }
        return list;
    }

    public static LinkedList<Float> toLinkedList(float... array) {
        LinkedList<Float> list = new LinkedList<Float>();
        for (float i : array) {
            list.add(i);
        }
        return list;
    }

    public static ArrayList<Float> toList(float... array) {
        ArrayList<Float> list = new ArrayList<Float>();
        for (float i : array) {
            list.add(i);
        }
        return list;
    }

    public static LinkedList<Double> toLinkedList(double... array) {
        LinkedList<Double> list = new LinkedList<Double>();
        for (double i : array) {
            list.add(i);
        }
        return list;
    }

    public static ArrayList<Double> toList(double... array) {
        ArrayList<Double> list = new ArrayList<Double>();
        for (double i : array) {
            list.add(i);
        }
        return list;
    }

    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public static <T> Iterator<T> forArray(T[] arr) {
        return Arrays.asList(arr).iterator();
    }

    /**
     * Simple helper function to <strong>create</strong> and fill an array with the
     * specified value.
     *
     * @return the array being filled
     */
    public static <T> T[] fill(int size, T value) {
        return fill(getArray(size), value);
    }

    /**
     * Simple helper function to fill an array with the specified value.
     *
     * @return the array being filled
     */
    public static <T> ArrayList<T> fillList(ArrayList<T> a, T val) {
        return (ArrayList<T>) a.stream().map(v -> val).collect(Collectors.toList());
    }

    /**
     * Simple helper function to <strong>create</strong> and fill an array with the
     * specified value.
     *
     * @return the array being filled
     */
    public static <T> ArrayList<T> fillList(int size, T value) {
        return fillList(new ArrayList<>(), value);
    }

    /**
     * Simple helper function to fill an array with the specified value.
     *
     * @return the array being filled
     */
    public static <T> T[] fill(T[] a, T val) {
        Arrays.fill(a, val);
        return a;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] getArray(int size) {
        Object[] arr = new Object[size];

        return (T[]) arr;
    }

    public static float[] toFloatArray(double[] data) {
        float[] result = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (float) data[i];
        }
        return result;
    }

    public static float[] toFloatArray(int[] data) {
        float[] result = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (float) data[i];
        }
        return result;
    }

    public static double[] toDoubleArray(float[] data) {
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        return result;
    }

    /**
     * Helper function to multiply the values in a list.
     */
    public static int multiply(List<Integer> data) {
        int result = 0;
        for (Integer i : data) {
            result *= i.intValue();
        }
        return result;
    }

    /**
     * Helper function to multiply the values in a list.
     */
    public static float multiplyf(List<Float> data) {
        float result = 0;
        for (Float i : data) {
            result *= i.floatValue();
        }
        return result;
    }

    /**
     * Helper function to multiply the values in a list.
     */
    public static double multiplyd(List<Double> data) {
        double result = 0;
        for (Double i : data) {
            result *= i.doubleValue();
        }
        return result;
    }
}
