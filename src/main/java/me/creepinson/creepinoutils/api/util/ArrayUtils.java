package me.creepinson.creepinoutils.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public class ArrayUtils {

    public static <T> Iterator<T> forArray(T[] arr) {
        return Arrays.asList(arr).iterator();
    }

    /**
     * Simple helper function to <strong>create</strong> and fill an array with the specified value.
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
     * Simple helper function to <strong>create</strong> and fill an array with the specified value.
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

    public static <T> ArrayList<T> arrayAsList(Object[] a) {
        return new ArrayList<>(Arrays.asList((T[]) a));
    }

    public static <T> LinkedList<T> arrayAsLinkedList(Object[] a) {
        return new LinkedList<T>(Arrays.asList((T[]) a));
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
}
