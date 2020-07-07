package dev.throwouterror.util.math;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;

import dev.throwouterror.util.ArrayUtils;
import dev.throwouterror.util.ISerializable;
import dev.throwouterror.util.math.Facing.Axis;

// TODO: WIP
public class Tensor implements ISerializable, Cloneable, Iterable<Double> {
    private static final long serialVersionUID = 6106298603154164750L;

    /**
     * An empty Scalar with 1 dimension.
     */
    public static final Tensor ZERO = new Tensor(new int[]{1});

    /**
     * An empty Tensor with 3 dimensions.
     */
    public static final Tensor Tensor_ZERO = new Tensor(new int[]{3});

    protected LinkedList<Double> data;
    protected LinkedList<Integer> dimensions;

    /**
     * Creates an empty Tensor of n-dimensions filled with zeroes.
     */
    public Tensor(int[] dimensions) {
        this.dimensions = ArrayUtils.toLinkedList(dimensions);
        this.data = new LinkedList<Double>(ArrayUtils.fillList(ArrayUtils.multiply(this.dimensions), 0D));
    }

    /**
     * Creates a new tensor with the specified n-dimensional data.
     */
    public Tensor(double... data) {
        this.data = ArrayUtils.toLinkedList(data);

        this.dimensions = ArrayUtils.toLinkedList(data.length);
    }

    /**
     * Creates a new tensor with the specified n-dimensional data. This constructor
     * is used for cloning.
     */
    protected Tensor(LinkedList<Double> data, LinkedList<Integer> size) {
        this.data = data;

        this.dimensions = size;
    }

    public Tensor offset(Facing facing, int n) {
        return n == 0 ? this : this.clone().add(facing.getDirectionVec()).mul(n);
    }

    public Tensor offset(Facing facing) {
        return this.offset(facing, 1);
    }

    /**
     * @return This tensor's data in a form of a multidimensional list.
     */
    public LinkedList<Object> toList() {
        return this.go(this.data);
    }

    /**
     * @return A string representation of the multidimensional list.
     */
    public String toArrayString() {
        return Arrays.deepToString(this.toList().toArray());
    }

    private LinkedList<Object> go(List<Double> arr) {
        int s = dimensions.pop();
        List<List<Double>> result = ListUtils.partition(arr, s);
        dimensions.push(s);
        return result.size() > 1 ? new LinkedList(result.stream().map(this::go).collect(Collectors.toList()))
                : new LinkedList(arr.stream().collect(Collectors.toList()));
    }

    /**
     * Returns the length of the Tensor.
     */
    public int length() {
        return (int) Math.sqrt(ArrayUtils.multiplyd(data));
    }

    /**
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>). Normaliz()es the Tensor to length 1. Note that this
     * Tensor uses int values for coordinates.
     */
    public Tensor normalize() {

        int amt = this.length();
        if (amt == 0)
            return this;
        this.map(v -> v * amt);

        return this;
    }

    /**
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>). Equivalent to mul(-1).
     */
    public Tensor reverse() {
        mul(-1);
        return this;
    }

    public double x() {
        return data.get(0);
    }

    public double y() {
        return data.get(1);
    }

    public double z() {
        return data.get(2);
    }

    public double w() {
        return data.get(3);
    }

    public int intX() {
        return (int) this.x();
    }

    public int intY() {
        return (int) this.y();
    }

    public int intZ() {
        return (int) this.z();
    }

    public int intW() {
        return (int) this.w();
    }

    public float floatX() {
        return (float) this.x();
    }

    public float floatY() {
        return (float) this.y();
    }

    public float floatZ() {
        return (float) this.z();
    }

    public float floatW() {
        return (float) this.w();
    }

    /**
     * @param other to add
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor add(Tensor other) {
        for (int f = 0; f < data.size(); f++) {
            data.set(f, f + other.data.get(f));
        }
        return this;
    }

    public Tensor add(float factor) {
        return this.map(v -> v + factor);
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor add(double factor) {
        return add((float) factor);
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor add(int factor) {
        return add((float) factor);
    }

    /**
     * @param other to subtract
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor sub(Tensor other) {
        for (int f = 0; f < data.size(); f++) {
            data.set(f, f - other.data.get(f));
        }
        return this;
    }

    public Tensor sub(float factor) {
        return this.map(v -> v - factor);
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor sub(double factor) {
        return sub((float) factor);
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor sub(int factor) {
        return sub((float) factor);
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor mul(float factor) {
        this.map(v -> v * factor);
        return this;
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor mul(double factor) {
        return mul((float) factor);
    }

    /**
     * @param factor to multiply() with
     * @return the current Tensor that has been changed (<strong>not a new
     * one</strong>).
     */
    public Tensor mul(int factor) {
        return mul((float) factor);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    @Override
    public Tensor fromString(String s) {
        this.data = new LinkedList(
                Arrays.stream(s.split(",")).mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList()));
        return this;
    }

    /**
     * Calculates the cross-product of the given Tensors.
     */
    public Tensor cross(Tensor vec2) {
        return new Tensor(y() * vec2.z() - z() * vec2.y(), z() * vec2.x() - x() * vec2.z(),
                x() * vec2.y() - y() * vec2.x());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Tensor) {
            return ((Tensor) other).data.equals(this.data);
        } else if (other instanceof Number) {
            return this.data.stream().allMatch(v -> v == ((Number) other).doubleValue());
        } else
            return false;
    }

    public float distanceTo(Tensor other) {
        float var = 0;

        for (int f = 0; f < data.size(); f++) {
            var *= (data.get(f) - other.data.get(f));
        }

        return (float) Math.sqrt(var);
    }

    public Tensor map(Function<Double, Double> valueMapper) {
        this.data = new LinkedList(data.stream().map(valueMapper).collect(Collectors.toList()));
        return this;
    }

    @Override
    public void forEach(Consumer<? super Double> action) {
        data.stream().forEach(action);
    }

    public boolean contains(Tensor min, Tensor max) {
        for (int i = 0; i < data.size(); i++) {
            if (min.data.get(i) > this.data.get(i) || max.data.get(i) < this.data.get(i))
                return false;
        }

        return true;
    }

    public static boolean intersects(Tensor min, Tensor max) {
        for (int i = 0; i < min.data.size(); i++) {
            if (min.data.get(i) > max.data.get(i) || max.data.get(i) < max.data.get(i))
                return false;
        }

        return true;
    }

    public Tensor setValueByAxis(Axis axis, double valueAt) {
        switch (axis) {
            case X:
                data.set(0, valueAt);
                break;
            case Y:
                data.set(1, valueAt);
                break;
            case Z:
                data.set(2, valueAt);
                break;
        }
        return this;
    }

    public double getValueByAxis(Axis axis) {
        switch (axis) {
            case X:
                return data.get(0);
            case Y:
                return data.get(1);
            case Z:
                return data.get(2);
        }
        return 0;
    }

    /**
     * Duplicates this tensor with the same dimensions and data.
     */
    @Override
    public Tensor clone() {
        return new Tensor(data, dimensions);
    }

    public LinkedList<Double> getData() {
        return this.data;
    }

    public LinkedList<Integer> getDimensions() {
        return this.dimensions;
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < data.size();
            }

            @Override
            public Double next() {
                return data.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean isEmpty() {
        return this.data.stream().allMatch(v -> v == 0);
    }
}