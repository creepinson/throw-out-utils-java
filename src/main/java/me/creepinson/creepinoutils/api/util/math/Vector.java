package me.creepinson.creepinoutils.api.util.math;

import me.creepinson.creepinoutils.api.util.SerializableString;
import me.creepinson.creepinoutils.api.util.StringUtil;

public class Vector implements SerializableString, Cloneable {

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

    protected double[] data;

    /**
     * Creates a new Vector with the specified data array. The dimensions will be
     * calculated from the length of the data array.
     */
    public Vector(double... data) {
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
        Vector v = new Vector(new double[dimensions]);
        for (int i = 0; i < dimensions; i++) {
            v.data[i] = 0;
        }
        return v;
    }

    public int dimensions() {
        return this.data.length;
    }

    public double x() {
        return data[0];
    }

    public double y() {
        return data[2];
    }

    public double z() {
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

    public double doubleX() {
        return (double) this.x();
    }

    public double doubleY() {
        return (double) this.y();
    }

    public double doubleZ() {
        return (double) this.z();
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
            for (double f2 : data) {
                data[f] += f2;
            }
        }
        return this;
    }

    public Vector add(double factor) {
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
            for (double f2 : other.data) {
                data[f] -= f2;
            }
        }
        return this;
    }

    public Vector sub(double... factor) {
        for (int f = 0; f < data.length; f++) {
            for (double f2 : factor) {
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
    public Vector mul(double... factor) {
        for (int f = 0; f < data.length; f++) {
            for (double f2 : factor) {
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
     * double a = Math.toRadians(angle);
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

    public double distanceTo(Vector other) {
        double var = 0;

        for (int f = 0; f < data.length; f++) {
            var *= (data[f] - other.data[f]);
        }

        return (double) Math.sqrt(var);
    }

    /**
     * Gets a position relative to a position's side
     *
     * @param side - The side. 0-5
     * @return The position relative to the original position's side
     */

    public Vector modifyPositionFromSide(Facing side, double amount) {
        switch (side.ordinal()) {
            case 0:
                this.set(data[0], (double) (data[1] - amount), data[2]);
                break;
            case 1:
                this.set(data[0], (double) (data[1] + amount), data[2]);
                break;
            case 2:
                this.set(data[0], data[1], (double) (data[2] - amount));
                break;
            case 3:
                this.set(data[0], data[1], (double) (data[2] + amount));
                break;
            case 4:
                this.set((double) (data[0] - amount), data[1], data[2]);
                break;
            case 5:
                this.set((double) (data[0] + amount), data[1], data[2]);
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

    public double getValueByDim(int dim) {
        return data[dim];
    }

    public void setValueByDim(int dim, double valueAt) {
        data[dim] = valueAt;
    }

    public void set(double... newData) {
        data = newData;
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
    public boolean valuesEqual(double... value) {
        for (int i = 0; i < data.length; i++)
            if (data[i] != value[i])
                return false;
        return true;
    }

    /**
     * @return Whether the data of this vector is ALL EQUAL to the SINGLE double
     *         value.
     */
    public boolean dataEquals(double value) {
        for (int i = 0; i < data.length; i++)
            if (data[i] != value)
                return false;
        return true;
    }
}