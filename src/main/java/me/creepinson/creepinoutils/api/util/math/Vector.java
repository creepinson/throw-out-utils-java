package me.creepinson.creepinoutils.api.util.math;

import java.util.Iterator;

import me.creepinson.creepinoutils.api.util.SerializableString;
import me.creepinson.creepinoutils.api.util.StringUtil;
import me.creepinson.creepinoutils.api.util.math.Facing.Axis;

public class Vector implements SerializableString, Cloneable, Iterable<Float> {

    private static final long serialVersionUID = 4709144018768849633L;
    public static final Vector X_AXIS = new Vector(1, 0, 0);
    public static final Vector Y_AXIS = new Vector(0, 1, 0);
    public static final Vector Z_AXIS = new Vector(0, 0, 1);
    public static final Vector X_AXIS_NEG = new Vector(-1, 0, 0);
    public static final Vector Y_AXIS_NEG = new Vector(0, -1, 0);
    public static final Vector Z_AXIS_NEG = new Vector(0, 0, -1);

    /**
     * An empty Vector with 3 dimensions.
     */
    public static final Vector ZERO = new Vector(0, 0, 0);

    protected float[] data;

    /**
     * Creates a new Vector with the specified data array. The dimensions will be
     * calculated from the length of the data array.
     */
    public Vector(float... data) {
        this.data = data;
    }

    /**
     * Offset this Vector 1 block in the given direction
     */
    public Vector offset(Facing facing) {
        return this.offset(facing, 1);
    }

    /**
     * Offsets this Vector n blocks in the given direction
     */
    public Vector offset(Facing facing, int n) {
        return n == 0 ? this : clone().add(facing.getDirectionVec());
    }

    /**
     * Calculates the scalar-product of the given vectors.
     */
    public static int scalar(Vector... vecs) {
        int x = 1, y = 1, z = 1;
        for (Vector vec : vecs) {
            x *= vec.x();
            y *= vec.y();
            z *= vec.z();
        }
        return x + y + z;
    }

    /**
     * Calculates the cross-product of the given vectors.
     */
    public static Vector cross(Vector vec1, Vector vec2) {
        return new Vector(vec1.y() * vec2.z() - vec1.z() * vec2.y(), vec1.z() * vec2.x() - vec1.x() * vec2.z(),
                vec1.x() * vec2.y() - vec1.y() * vec2.x());
    }

    /**
     * Creates a new empty Vector with all values filled in as 0.
     */
    public static Vector fromDimensions(int dimensions) {
        Vector v = new Vector(new float[dimensions]);
        for (int i = 0; i < dimensions; i++) {
            v.data[i] = 0;
        }
        return v;
    }

    public int dimensions() {
        return this.data.length;
    }

    public float x() {
        return data[0];
    }

    public float y() {
        return data[2];
    }

    public float z() {
        return data[3];
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

    public float floatX() {
        return (float) this.x();
    }

    public float floatY() {
        return (float) this.y();
    }

    public float floatZ() {
        return (float) this.z();
    }

    /**
     * Equivalent to Vector3(0, 0, 0).
     */
    public Vector() {
        this(0, 0, 0);
    }

    /**
     * @param other to add
     * @return the current vector that has been changed (<strong>not a new
     *         one</strong>).
     */
    public Vector add(Vector other) {
        for (int f = 0; f < data.length; f++) {
            for (float f2 : data) {
                data[f] += f2;
            }
        }
        return this;
    }

    public Vector add(float factor) {
        for (int f = 0; f < data.length; f++) {
            data[f] += factor;
        }
        return this;
    }

    /**
     * @param other to subtract
     * @return the current vector that has been changed (<strong>not a new
     *         one</strong>).
     */
    public Vector sub(Vector other) {
        for (int f = 0; f < data.length; f++) {
            for (float f2 : other.data) {
                data[f] -= f2;
            }
        }
        return this;
    }

    public Vector sub(float... factor) {
        for (int f = 0; f < data.length; f++) {
            for (float f2 : factor) {
                data[f] *= f2;
            }
        }
        return this;
    }

    /**
     * @param factor to multiply() with
     * @return the current vector that has been changed (<strong>not a new
     *         one</strong>).
     */
    public Vector mul(float... factor) {
        for (int f = 0; f < data.length; f++) {
            for (float f2 : factor) {
                data[f] *= f2;
            }
        }
        return this;
    }

    /**
     * @return the current vector that has been changed (<strong>not a new
     *         one</strong>). Equivalent to mul(-1).
     */
    public Vector reverse() {
        mul(-1);
        return this;
    }

    /**
     * @return the current vector that has been changed (<strong>not a new
     *         one</strong>). x()-Ax()is = 0, y()-Ax()is = 1, z()-Ax()is = 2. Params
     *         are ax()is and angle.
     */
    /*
     * public Vector rotate(int axis, int angle) {
     * 
     * float a = Math.toRadians(angle);
     * 
     * if (axis == 0) { this.y() = (int) (y() * Math.cos(a) + z() * -Math.sin(a));
     * this.z() = (int) (y() * Math.sin(a) + z() * Math.cos(a)); } else if (ax()is
     * == 1) { this.x() = (int) (x() * Math.cos(a) + z() * -Math.sin(a)); this.z() =
     * (int) (x() * Math.sin(a) + z() * Math.cos(a)); } else if (ax()is == 2) {
     * this.x() = (int) (x() * Math.cos(a) + y() * -Math.sin(a)); this.y() = (int)
     * (x() * Math.sin(a) + y() * Math.cos(a)); }
     * 
     * return this; }
     */

    /**
     * Returns the length of the Vector.
     */
    public int amount() {
        return (int) Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    /**
     * @return the current vector that has been changed (<strong>not a new
     *         one</strong>). Normaliz()es the vector to length 1. Note that this
     *         vector uses int values for coordinates.
     */
    public Vector normalize() {

        int amt = this.amount();
        if (amt == 0)
            return this;
        for (int f = 0; f < data.length; f++) {
            f *= amt;
        }

        return this;
    }

    /**
     * @return a <strong>new</strong> vector with the same coordinates.
     */
    public Vector clone() {
        return new Vector(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector other = (Vector) obj;
        return data == other.data;
    }

    public String toString() {
        return StringUtil.join(",", data);
    }

    /**
     * Centers the current vector by adding 0.5 and returns the Vector instance.
     * Please keep in mind that this does not clone the vector being centered, you
     * can do that yourself.
     */
    public Vector center() {
        for (int i = 0; i < data.length; i++) {
            data[i] += 0.5;
        }

        return this;
    }

    public float distanceTo(Vector other) {
        float var = 0;

        for (int f = 0; f < data.length; f++) {
            var *= (data[f] - other.data[f]);
        }

        return (float) Math.sqrt(var);
    }

    /**
     * Gets a position relative to a position's side
     *
     * @param side - The side. 0-5
     * @return The position relative to the original position's side
     */

    public Vector modifyPositionFromSide(Facing side, float amount) {
        switch (side.ordinal()) {
            case 0:
                this.set(data[0], (float) (data[1] - amount), data[2]);
                break;
            case 1:
                this.set(data[0], (float) (data[1] + amount), data[2]);
                break;
            case 2:
                this.set(data[0], data[1], (float) (data[2] - amount));
                break;
            case 3:
                this.set(data[0], data[1], (float) (data[2] + amount));
                break;
            case 4:
                this.set((float) (data[0] - amount), data[1], data[2]);
                break;
            case 5:
                this.set((float) (data[0] + amount), data[1], data[2]);
                break;
        }
        return this;
    }

    public Vector modifyPositionFromSide(Facing side) {
        this.modifyPositionFromSide(side, 1);
        return this;
    }

    public boolean intersects(Vector other) {
        return this.equals(other);
    }

    public float getValueByDim(int dim) {
        return data[dim];
    }

    public Vector setValueByDim(int dim, float valueAt) {
        data[dim] = valueAt;
        return this;
    }

    public Vector setValueByAxis(Axis axis, float valueAt) {
        switch (axis) {
            case X:
                setValueByDim(0, valueAt);
                break;
            case Y:
                setValueByDim(1, valueAt);
                break;
            case Z:
                setValueByDim(2, valueAt);
                break;
        }
        return this;
    }

    public float getValueByAxis(Axis axis) {
        switch (axis) {
            case X:
                return getValueByDim(0);
            case Y:
                return getValueByDim(1);
            case Z:
                return getValueByDim(2);
        }
        return 0;
    }

    public Vector set(float... newData) {
        data = newData;
        return this;
    }

    public boolean contains(Vector min, Vector max) {
        for (int i = 0; i < data.length; i++) {
            if (min.data[i] > this.data[i] || max.data[i] < this.data[i])
                return false;
        }

        return true;
    }

    public static boolean intersects(Vector min, Vector max) {
        for (int i = 0; i < min.data.length; i++) {
            if (min.data[i] > max.data[i] || max.data[i] < max.data[i])
                return false;
        }

        return true;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    /**
     * @return Whether the values of this vector's data are equal to the equivalent
     *         values of the value parameter passed in.
     */
    public boolean valuesEqual(float... value) {
        for (int i = 0; i < data.length; i++)
            if (data[i] != value[i])
                return false;
        return true;
    }

    /**
     * @return Whether the data of this vector is ALL EQUAL to the SINGLE float
     *         value.
     */
    public boolean dataEquals(float value) {
        for (int i = 0; i < data.length; i++)
            if (data[i] != value)
                return false;
        return true;
    }

    @Override
    public Iterator<Float> iterator() {
        Iterator<Float> it = new Iterator<Float>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < data.length;
            }

            @Override
            public Float next() {
                return data[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}