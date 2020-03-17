package me.creepinson.creepinoutils.api.util.math;

import java.io.Serializable;

public class Vector3 implements Serializable, Cloneable {

    private static final long serialVersionUID = 4709144018768849633L;
    public static final Vector3 X_AXIS = new Vector3(1, 0, 0);
    public static final Vector3 Y_AXIS = new Vector3(0, 1, 0);
    public static final Vector3 Z_AXIS = new Vector3(0, 0, 1);
    public static final Vector3 X_AXIS_NEG = new Vector3(-1, 0, 0);
    public static final Vector3 Y_AXIS_NEG = new Vector3(0, -1, 0);
    public static final Vector3 Z_AXIS_NEG = new Vector3(0, 0, -1);

    /**
     * Offset this Vector 1 block in the given direction
     */
    public Vector3 offset(Facing facing) {
        return this.offset(facing, 1);
    }

    /**
     * Offsets this Vector n blocks in the given direction
     */
    public Vector3 offset(Facing facing, int n) {
        return n == 0 ? this
                : new Vector3(this.x + facing.getXOffset() * n, this.y + facing.getYOffset() * n,
                this.z + facing.getZOffset() * n);
    }

    /**
     * Calculates the scalar-product of the given vectors.
     */
    public static int scalar(Vector3... vecs) {
        int x = 1, y = 1, z = 1;
        for (Vector3 vec : vecs) {
            x *= vec.x;
            y *= vec.y;
            z *= vec.z;
        }
        return x + y + z;
    }

    /**
     * Calculates the cross-product of the given vectors.
     */
    public static Vector3 cross(Vector3 vec1, Vector3 vec2) {
        return new Vector3(vec1.y * vec2.z - vec1.z * vec2.y, vec1.z * vec2.x - vec1.x * vec2.z,
                vec1.x * vec2.y - vec1.y * vec2.x);
    }

    public float x;
    public float y;
    public float z;


    /**
     * Creates a new Vector3 with x, y, z.
     */
    public Vector3(int x, int y, int z) {
        this((float) x, (float) y, (float) z);
    }

    /**
     * Creates a new Vector3 with x, y, z.
     */
    public Vector3(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    /**
     * Creates a new Vector3 with x, y, z.
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int intX() {
        return (int) this.x;
    }

    public int intY() {
        return (int) this.y;
    }

    public int intZ() {
        return (int) this.z;
    }

    public double doubleX() {
        return (double) this.x;
    }

    public double doubleY() {
        return (double) this.y;
    }

    public double doubleZ() {
        return (double) this.z;
    }


    /**
     * Equivalent to Vector3(0, 0, 0).
     */
    public Vector3() {
        this(0, 0, 0);
    }

    /**
     * @param other to add
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     */
    public Vector3 add(Vector3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * @param other to subtract
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     */
    public Vector3 sub(Vector3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector3 sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * @param factor to multiply with
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     */
    public Vector3 mul(int factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    /**
     * @param factor to multiply with
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     */
    public Vector3 mul(float factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    /**
     * @param factor to multiply with
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     */
    public Vector3 mul(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    /**
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     * Equivalent to mul(-1).
     */
    public Vector3 reverse() {
        mul(-1);
        return this;
    }

    /**
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     * x-Axis = 0, y-Axis = 1, z-Axis = 2. Params
     * are axis and angle.
     */
    public Vector3 rotate(int axis, int angle) {

        double a = Math.toRadians(angle);

        if (axis == 0) {
            this.y = (int) (y * Math.cos(a) + z * -Math.sin(a));
            this.z = (int) (y * Math.sin(a) + z * Math.cos(a));
        } else if (axis == 1) {
            this.x = (int) (x * Math.cos(a) + z * -Math.sin(a));
            this.z = (int) (x * Math.sin(a) + z * Math.cos(a));
        } else if (axis == 2) {
            this.x = (int) (x * Math.cos(a) + y * -Math.sin(a));
            this.y = (int) (x * Math.sin(a) + y * Math.cos(a));
        }

        return this;
    }

    /**
     * Returns the length of the Vector.
     */
    public int amount() {
        return (int) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * @return the current vector that has been changed (<strong>not a new one</strong>).
     * Normalizes the vector to length 1. Note that
     * this vector uses int values for coordinates.
     */
    public Vector3 normalize() {

        int amt = this.amount();
        if (amt == 0)
            return this;
        this.x /= amt;
        this.y /= amt;
        this.z /= amt;

        return this;
    }

    /**
     * @return a <strong>new</strong> vector with the same coordinates.
     */
    public Vector3 clone() {
        return new Vector3(x, y, z);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) x;
        result = prime * result + (int) y;
        result = prime * result + (int) z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3 other = (Vector3) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    public String toString() {
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }

    public float distanceTo(Vector3 vector3) {
        float var2 = vector3.x - this.x;
        float var4 = vector3.y - this.y;
        float var6 = vector3.z - this.z;
        return (float) Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
    }

    /**
     * Gets a position relative to a position's side
     *
     * @param side - The side. 0-5
     * @return The position relative to the original position's side
     */
    public Vector3 modifyPositionFromSide(Facing side, double amount) {
        switch (side.ordinal()) {
            case 0:
                this.y -= amount;
                break;
            case 1:
                this.y += amount;
                break;
            case 2:
                this.z -= amount;
                break;
            case 3:
                this.z += amount;
                break;
            case 4:
                this.x -= amount;
                break;
            case 5:
                this.x += amount;
                break;
        }
        return this;
    }

    public Vector3 modifyPositionFromSide(Facing side) {
        this.modifyPositionFromSide(side, 1);
        return this;
    }

    public boolean intersects(Vector3 other) {
        return this.equals(other);
    }

    public float getValueByDim(int dim) {
        switch (dim) {
            default:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
    }

    public void setValueByDim(int dim, float valueAt) {
        switch (dim) {
            default:
                x = valueAt;
            case 1:
                y = valueAt;
            case 2:
                z = valueAt;
        }
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isInAABB(Vector3 min, Vector3 max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }
}