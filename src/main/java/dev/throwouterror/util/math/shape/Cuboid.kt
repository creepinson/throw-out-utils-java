package dev.throwouterror.util.math.shape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dev.throwouterror.util.math.Tensor;

import java.util.Arrays;

public class Cuboid implements Cloneable, java.io.Serializable {
    public static final Cuboid AIR = new Cuboid(0, 0, 0, 0, 0, 0);
    public static final Cuboid FULL_CUBE = new Cuboid(0, 0, 0, 1, 1, 1);

    public int minX() {
        return minimumPoint.intX();
    }

    public int minY() {
        return minimumPoint.intY();
    }

    public int minZ() {
        return minimumPoint.intZ();
    }

    public int maxX() {
        return minimumPoint.intX();
    }

    public int maxY() {
        return minimumPoint.intY();
    }

    public int maxZ() {
        return minimumPoint.intZ();
    }

    public static class Serializer implements JsonSerializer<Cuboid> {
        @Override
        public JsonElement serialize(Cuboid src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("minX", src.minX());
            object.addProperty("minY", src.minY());
            object.addProperty("minZ", src.minZ());
            object.addProperty("maxX", src.maxX());
            object.addProperty("maxY", src.maxY());
            object.addProperty("maxZ", src.maxZ());
            object.addProperty("size", src.getType().toString());
            JsonObject centerObj = new JsonObject();
            centerObj.addProperty("x", src.getCenter().x());
            centerObj.addProperty("y", src.getCenter().y());
            centerObj.addProperty("z", src.getCenter().z());
            object.add("center", centerObj);
            return object;
        }
    }

    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    public SizeType getType() {
        SizeType type = this.isEmpty() ? SizeType.AIR : SizeType.OTHER;
        if (this.maximumPoint.equals(1))
            type = SizeType.FULL;
        return type;
    }

    /**
     * @return Returns whether or not the maximum point's values are 0.
     */
    public boolean isEmpty() {
        return this.maximumPoint.isEmpty();
    }

    /**
     * '
     *
     * @return the Tensor containing the minimum point of this cuboid
     */
    public Tensor getMinPoint() {
        return this.minimumPoint;
    }

    /**
     * '
     *
     * @return the Tensor containing the maximum point of this cuboid
     */
    public Tensor getMaxPoint() {
        return this.maximumPoint;
    }

    protected final Tensor minimumPoint, maximumPoint;

    /**
     * Creates a cuboid with a minimum and maximum point.
     */
    public Cuboid(Tensor p1, Tensor p2) {
        this.minimumPoint = p1.clone();
        this.maximumPoint = p2.clone();
    }

    public Cuboid(Tensor loc) {
        this(loc, loc);
    }

    public Cuboid(Cuboid other) {
        this(other.minimumPoint, other.maximumPoint);
    }

    public Cuboid(float x1, float y1, float z1, float x2, float y2, float z2) {
        this.minimumPoint = new Tensor(x1, y1, z1);
        this.maximumPoint = new Tensor(x2, y2, z2);
    }

    public Cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minimumPoint = new Tensor((float) x1, (float) y1, (float) z1);
        this.maximumPoint = new Tensor((float) x2, (float) y2, (float) z2);
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (!(p_equals_1_ instanceof Cuboid)) {
            return false;
        } else {
            Cuboid c = (Cuboid) p_equals_1_;

            return c.minimumPoint.equals(this.minimumPoint) && c.maximumPoint.equals(this.maximumPoint);
        }
    }

    /**
     * Creates a new {@link Cuboid} that has been contracted by the given amount,
     * with positive changes decreasing max values and negative changes increasing
     * min values. <br/>
     * If the amount to contract by is larger than the length of a side, then the
     * side will wrap (still creating a valid AABB - see last sample).
     *
     * <h3>Samples:</h3>
     * <table>
     * <tr>
     * <th>Input</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(2, 2, 2)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[0.0,
     * 0.0, 0.0 -> 2.0, 2.0, 2.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(-2, -2, -
     * 2)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[2.0, 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).contract(0, 1, -
     * 1)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[5.0, 5.0, 6.0 -> 7.0, 6.0, 7.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(-2, -2, -2, 2, 2, 2).contract(4, -4,
     * 0)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[-8.0, 2.0, -2.0 -> -2.0, 8.0, 2.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * </table>
     *
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(float, float, float)} - like this, except for
     * expanding.</li>
     * <li>{@link #grow(float, float, float)} and {@link #grow(float)} - expands in
     * all directions.</li>
     * <li>{@link #shrink(float)} - contracts in all directions (like
     * {@link #grow(float)})</li>
     * </ul>
     *
     * @return A new modified bounding box.
     */
    public Cuboid contract(float x, float y, float z) {
        float d0 = this.minX();
        float d1 = this.minY();
        float d2 = this.minZ();
        float d3 = this.maxX();
        float d4 = this.maxY();
        float d5 = this.maxZ();

        if (x < 0.0D) {
            d0 -= x;
        } else if (x > 0.0D) {
            d3 -= x;
        }

        if (y < 0.0D) {
            d1 -= y;
        } else if (y > 0.0D) {
            d4 -= y;
        }

        if (z < 0.0D) {
            d2 -= z;
        } else if (z > 0.0D) {
            d5 -= z;
        }

        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link Cuboid} that has been expanded by the given amount, with
     * positive changes increasing max values and negative changes decreasing min
     * values.
     *
     * <h3>Samples:</h3>
     * <table>
     * <tr>
     * <th>Input</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).expand(2, 2, 2)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[0, 0,
     * 0 -> 3, 3, 3]</samp>
     * </pre>
     *
     * </td>
     * <td>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).expand(-2, -2, -2)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[-2,
     * -2, -2 -> 1, 1, 1]</samp>
     * </pre>
     *
     * </td>
     * <td>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).expand(0, 1, -1)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[5, 5,
     * 4, 7, 8, 7]</samp>
     * </pre>
     *
     * </td>
     * <td>
     * </table>
     *
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #contract(float, float, float)} - like this, except for
     * shrinking.</li>
     * <li>{@link #grow(float, float, float)} and {@link #grow(float)} - expands in
     * all directions.</li>
     * <li>{@link #shrink(float)} - contracts in all directions (like
     * {@link #grow(float)})</li>
     * </ul>
     *
     * @return A modified bounding box that will always be equal or greater in
     * volume to this bounding box.
     */
    public Cuboid expand(float x, float y, float z) {
        float d0 = this.minX();
        float d1 = this.minY();
        float d2 = this.minZ();
        float d3 = this.maxX();
        float d4 = this.maxY();
        float d5 = this.maxZ();

        if (x < 0.0D) {
            d0 += x;
        } else if (x > 0.0D) {
            d3 += x;
        }

        if (y < 0.0D) {
            d1 += y;
        } else if (y > 0.0D) {
            d4 += y;
        }

        if (z < 0.0D) {
            d2 += z;
        } else if (z > 0.0D) {
            d5 += z;
        }

        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link Cuboid} that has been contracted by the given amount in
     * both directions. Negative values will shrink the AABB instead of expanding
     * it. <br/>
     * Side lengths will be increased by 2 times the value of the parameters, since
     * both min and max are changed. <br/>
     * If contracting and the amount to contract by is larger than the length of a
     * side, then the side will wrap (still creating a valid AABB - see last ample).
     *
     * <h3>Samples:</h3>
     * <table>
     * <tr>
     * <th>Input</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).grow(2, 2, 2)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[-2.0, -
     * 2.0, -2.0 -> 3.0, 3.0, 3.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(0, 0, 0, 6, 6, 6).grow(-2, -2, -2)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[2.0,
     * 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).grow(0, 1, -1)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[5.0,
     * 4.0, 6.0 -> 7.0, 8.0, 6.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * <tr>
     * <td>
     *
     * <pre>
     * <code>new AxisAlignedBB(1, 1, 1, 3, 3, 3).grow(-4, -2, -3)</code>
     * </pre>
     *
     * </td>
     * <td>
     *
     * <pre>
     * <samp>box[-1.0,
     * 1.0, 0.0 -> 5.0, 3.0, 4.0]</samp>
     * </pre>
     *
     * </td>
     * </tr>
     * </table>
     *
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(float, float, float)} - expands in only one
     * direction.</li>
     * <li>{@link #contract(float, float, float)} - contracts in only one
     * direction.</li> <lu>{@link #grow(float)} - version of this that expands in
     * all directions from one parameter.</li>
     * <li>{@link #shrink(float)} - contracts in all directions</li>
     * </ul>
     *
     * @return A modified bounding box.
     */
    public Cuboid grow(float x, float y, float z) {
        float d0 = this.minX() - x;
        float d1 = this.minY() - y;
        float d2 = this.minZ() - z;
        float d3 = this.maxX() + x;
        float d4 = this.maxY() + y;
        float d5 = this.maxZ() + z;
        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link Cuboid} that is expanded by the given value in all
     * directions. Equivalent to {@link #grow(float, float, float)} with the given
     * value for all 3 params. Negative values will shrink the AABB. <br/>
     * Side lengths will be increased by 2 times the value of the parameter, since
     * both min and max are changed. <br/>
     * If contracting and the amount to contract by is larger than the length of a
     * side, then the side will wrap (still creating a valid AABB - see samples on
     * {@link #grow(float, float, float)}).
     *
     * @return A modified AABB.
     */
    public Cuboid grow(float value) {
        return this.grow(value, value, value);
    }

    public Cuboid intersect(Cuboid other) {
        float d0 = Math.max(this.minX(), other.minX());
        float d1 = Math.max(this.minY(), other.minY());
        float d2 = Math.max(this.minZ(), other.minZ());
        float d3 = Math.min(this.maxX(), other.maxX());
        float d4 = Math.min(this.maxY(), other.maxY());
        float d5 = Math.min(this.maxZ(), other.maxZ());
        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    public Cuboid union(Cuboid other) {
        float d0 = Math.min(this.minX(), other.minX());
        float d1 = Math.min(this.minY(), other.minY());
        float d2 = Math.min(this.minZ(), other.minZ());
        float d3 = Math.max(this.maxX(), other.maxX());
        float d4 = Math.max(this.maxY(), other.maxY());
        float d5 = Math.max(this.maxZ(), other.maxZ());
        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Offsets the current bounding box by the specified amount.
     */
    public Cuboid offset(float x, float y, float z) {
        return new Cuboid(this.minX() + x, this.minY() + y, this.minZ() + z, this.maxX() + x, this.maxY() + y,
                this.maxZ() + z);
    }

    public Cuboid offset(Tensor vec) {
        return new Cuboid(minimumPoint.clone().add(vec), maximumPoint.clone().add(vec));
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z
     * dimensions, calculate the offset between them in the X dimension. return var2
     * if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset. Otherwise return the calculated offset.
     */
    public float calculateXOffset(Cuboid other, float offsetX) {
        if (other.maxY() > this.minY() && other.minY() < this.maxY() && other.maxZ() > this.minZ()
                && other.minZ() < this.maxZ()) {
            if (offsetX > 0.0D && other.maxX() <= this.minX()) {
                float d1 = this.minX() - other.maxX();

                if (d1 < offsetX) {
                    offsetX = d1;
                }
            } else if (offsetX < 0.0D && other.minX() >= this.maxX()) {
                float d0 = this.maxX() - other.minX();

                if (d0 > offsetX) {
                    offsetX = d0;
                }
            }

            return offsetX;
        } else {
            return offsetX;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z
     * dimensions, calculate the offset between them in the Y dimension. return var2
     * if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset. Otherwise return the calculated offset.
     */
    public float calculateYOffset(Cuboid other, float offsetY) {
        if (other.maxX() > this.minX() && other.minX() < this.maxX() && other.maxZ() > this.minZ()
                && other.minZ() < this.maxZ()) {
            if (offsetY > 0.0D && other.maxY() <= this.minY()) {
                float d1 = this.minY() - other.maxY();

                if (d1 < offsetY) {
                    offsetY = d1;
                }
            } else if (offsetY < 0.0D && other.minY() >= this.maxY()) {
                float d0 = this.maxY() - other.minY();

                if (d0 > offsetY) {
                    offsetY = d0;
                }
            }

            return offsetY;
        } else {
            return offsetY;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X
     * dimensions, calculate the offset between them in the Z dimension. return var2
     * if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset. Otherwise return the calculated offset.
     */
    public float calculateZOffset(Cuboid other, float offsetZ) {
        if (other.maxX() > this.minX() && other.minX() < this.maxX() && other.maxY() > this.minY()
                && other.minY() < this.maxY()) {
            if (offsetZ > 0.0D && other.maxZ() <= this.minZ()) {
                float d1 = this.minZ() - other.maxZ();

                if (d1 < offsetZ) {
                    offsetZ = d1;
                }
            } else if (offsetZ < 0.0D && other.minZ() >= this.maxZ()) {
                float d0 = this.maxZ() - other.minZ();

                if (d0 > offsetZ) {
                    offsetZ = d0;
                }
            }

            return offsetZ;
        } else {
            return offsetZ;
        }
    }

    /**
     * Checks if the bounding box intersects with another.
     */
    public boolean intersects(Cuboid other) {
        return Tensor.intersects(minimumPoint, maximumPoint);
    }

    /**
     * Returns if the supplied Tensor is completely inside the bounding box
     */
    public boolean contains(Tensor Tensor) {
        return Tensor != null && Tensor.contains(getMinPoint(), getMaxPoint());
    }

    public boolean contains(double... data) {
        return contains(new Tensor(data));
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public float getAverageEdgeLength() {
        float d0 = this.maxX() - this.minX();
        float d1 = this.maxY() - this.minY();
        float d2 = this.maxZ() - this.minZ();
        return (d0 + d1 + d2) / 3.0F;
    }

    public float getXSize() {
        return this.maxX() - this.minX();
    }

    public float getYSize() {
        return this.maxY() - this.minY();
    }

    public float getZSize() {
        return this.maxZ() - this.minZ();
    }

    /**
     * Creates a new {@link Cuboid} that is expanded by the given value in all
     * directions. Equivalent to {@link #grow(float)} with value set to the negative
     * of the value provided here. Passing a negative value to this method values
     * will grow the AABB. <br/>
     * Side lengths will be decreased by 2 times the value of the parameter, since
     * both min and max are changed. <br/>
     * If contracting and the amount to contract by is larger than the length of a
     * side, then the side will wrap (still creating a valid AABB - see samples on
     * {@link #grow(float, float, float)}).
     *
     * @return A modified AABB.
     */
    public Cuboid shrink(float value) {
        return this.grow(-value);
    }

    public String toString() {
        return "" + this.minX() + "," + this.minY() + "," + this.minZ() + ":" + this.maxX() + "," + this.maxY() + ","
                + this.maxZ() + "";
    }

    public boolean hasNaN() {
        return Double.isNaN(this.minX()) || Double.isNaN(this.minY()) || Double.isNaN(this.minZ())
                || Double.isNaN(this.maxX()) || Double.isNaN(this.maxY()) || Double.isNaN(this.maxZ());
    }

    public Tensor getCenter() {
        return new Tensor(this.minX() + (this.maxX() - this.minX()) * 0.5F,
                this.minY() + (this.maxY() - this.minY()) * 0.5F, this.minZ() + (this.maxZ() - this.minZ()) * 0.5F);
    }

    public enum SizeType {
        FULL, AIR, OTHER;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}