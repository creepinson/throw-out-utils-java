package me.creepinson.creepinoutils.api.util.math.shape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.creepinson.creepinoutils.api.util.math.Vector3;

public class Cuboid implements Cloneable, java.io.Serializable {
    public static final Cuboid AIR = new Cuboid(0, 0, 0, 0, 0, 0);
    public static final Cuboid FULL_CUBE = new Cuboid(0, 0, 0, 1, 1, 1);

    public int minX() {
        return (int) minimumPoint.x;
    }

    public int minY() {
        return (int) minimumPoint.y;
    }

    public int minZ() {
        return (int) minimumPoint.z;
    }

    public int maxX() {
        return (int) minimumPoint.x;
    }

    public int maxY() {
        return (int) minimumPoint.y;
    }

    public int maxZ() {
        return (int) minimumPoint.z;
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
            centerObj.addProperty("x", src.getCenter().x);
            centerObj.addProperty("y", src.getCenter().y);
            centerObj.addProperty("z", src.getCenter().z);
            object.add("center", centerObj);
            return object;
        }
    }

    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    public SizeType getType() {
        SizeType type = this.isAir() ? SizeType.AIR : SizeType.OTHER;
        if (this.maxX() == 1 && this.maxY() == 1 && this.maxZ() == 1) type = SizeType.FULL;
        return type;
    }

    public boolean isAir() {
        return (this.maxX() == 0 && this.maxY() == 0 && this.maxZ() == 0);
    }

    /**
     * '
     *
     * @return the vector containing the minimum point of this cuboid
     */
    public Vector3 getMinPoint() {
        return this.minimumPoint;
    }

    /**
     * '
     *
     * @return the vector containing the maximum point of this cuboid
     */
    public Vector3 getMaxPoint() {
        return this.maximumPoint;
    }


    protected final Vector3 minimumPoint, maximumPoint;

    public Cuboid(Vector3 p1, Vector3 p2) {
        double xPos1 = Math.min(p1.x, p2.x);
        double yPos1 = Math.min(p1.y, p2.y);
        double zPos1 = Math.min(p1.z, p2.z);
        double xPos2 = Math.max(p1.x, p2.x);
        double yPos2 = Math.max(p1.y, p2.y);
        double zPos2 = Math.max(p1.z, p2.z);
        this.minimumPoint = new Vector3(xPos1, yPos1, zPos1);
        this.maximumPoint = new Vector3(xPos2, yPos2, zPos2);
    }

    public Cuboid(Vector3 loc) {
        this(loc, loc);
    }

    public Cuboid(Cuboid other) {
        this(other.minimumPoint.clone(), other.maximumPoint.clone());
    }

    public Cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        double xPos1 = Math.min(x1, x2);
        double xPos2 = Math.max(x1, x2);
        double yPos1 = Math.min(y1, y2);
        double yPos2 = Math.max(y1, y2);
        double zPos1 = Math.min(z1, z2);
        double zPos2 = Math.max(z1, z2);
        this.minimumPoint = new Vector3(xPos1, yPos1, zPos1);
        this.maximumPoint = new Vector3(xPos2, yPos2, zPos2);
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

    public int hashCode() {
        long i = Double.doubleToLongBits(this.minX());
        int j = (int) (i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minY());
        j = 31 * j + (int) (i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minZ());
        j = 31 * j + (int) (i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxX());
        j = 31 * j + (int) (i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxY());
        j = 31 * j + (int) (i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxZ());
        j = 31 * j + (int) (i ^ i >>> 32);
        return j;
    }

    /**
     * Creates a new {@link Cuboid} that has been contracted by the given amount, with positive changes
     * decreasing max values and negative changes increasing min values.
     * <br/>
     * If the amount to contract by is larger than the length of a side, then the side will wrap (still creating a valid
     * AABB - see last sample).
     *
     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(2, 2, 2)</code></pre></td><td><pre><samp>box[0.0,
     * 0.0, 0.0 -> 2.0, 2.0, 2.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(-2, -2, -
     * 2)</code></pre></td><td><pre><samp>box[2.0, 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).contract(0, 1, -
     * 1)</code></pre></td><td><pre><samp>box[5.0, 5.0, 6.0 -> 7.0, 6.0, 7.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(-2, -2, -2, 2, 2, 2).contract(4, -4,
     * 0)</code></pre></td><td><pre><samp>box[-8.0, 2.0, -2.0 -> -2.0, 8.0, 2.0]</samp></pre></td></tr>
     * </table>
     *
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(double, double, double)} - like this, except for expanding.</li>
     * <li>{@link #grow(double, double, double)} and {@link #grow(double)} - expands in all directions.</li>
     * <li>{@link #shrink(double)} - contracts in all directions (like {@link #grow(double)})</li>
     * </ul>
     *
     * @return A new modified bounding box.
     */
    public Cuboid contract(double x, double y, double z) {
        double d0 = this.minX();
        double d1 = this.minY();
        double d2 = this.minZ();
        double d3 = this.maxX();
        double d4 = this.maxY();
        double d5 = this.maxZ();

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
     * Creates a new {@link Cuboid} that has been expanded by the given amount, with positive changes increasing
     * max values and negative changes decreasing min values.
     *
     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).expand(2, 2, 2)</code></pre></td><td><pre><samp>box[0, 0,
     * 0 -> 3, 3, 3]</samp></pre></td><td>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).expand(-2, -2, -2)</code></pre></td><td><pre><samp>box[-2,
     * -2, -2 -> 1, 1, 1]</samp></pre></td><td>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).expand(0, 1, -1)</code></pre></td><td><pre><samp>box[5, 5,
     * 4, 7, 8, 7]</samp></pre></td><td>
     * </table>
     *
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #contract(double, double, double)} - like this, except for shrinking.</li>
     * <li>{@link #grow(double, double, double)} and {@link #grow(double)} - expands in all directions.</li>
     * <li>{@link #shrink(double)} - contracts in all directions (like {@link #grow(double)})</li>
     * </ul>
     *
     * @return A modified bounding box that will always be equal or greater in volume to this bounding box.
     */
    public Cuboid expand(double x, double y, double z) {
        double d0 = this.minX();
        double d1 = this.minY();
        double d2 = this.minZ();
        double d3 = this.maxX();
        double d4 = this.maxY();
        double d5 = this.maxZ();

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
     * Creates a new {@link Cuboid} that has been contracted by the given amount in both directions. Negative
     * values will shrink the AABB instead of expanding it.
     * <br/>
     * Side lengths will be increased by 2 times the value of the parameters, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see last ample).
     *
     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 1, 1, 1).grow(2, 2, 2)</code></pre></td><td><pre><samp>box[-2.0, -
     * 2.0, -2.0 -> 3.0, 3.0, 3.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 6, 6, 6).grow(-2, -2, -2)</code></pre></td><td><pre><samp>box[2.0,
     * 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).grow(0, 1, -1)</code></pre></td><td><pre><samp>box[5.0,
     * 4.0, 6.0 -> 7.0, 8.0, 6.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(1, 1, 1, 3, 3, 3).grow(-4, -2, -3)</code></pre></td><td><pre><samp>box[-1.0,
     * 1.0, 0.0 -> 5.0, 3.0, 4.0]</samp></pre></td></tr>
     * </table>
     *
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(double, double, double)} - expands in only one direction.</li>
     * <li>{@link #contract(double, double, double)} - contracts in only one direction.</li>
     * <lu>{@link #grow(double)} - version of this that expands in all directions from one parameter.</li>
     * <li>{@link #shrink(double)} - contracts in all directions</li>
     * </ul>
     *
     * @return A modified bounding box.
     */
    public Cuboid grow(double x, double y, double z) {
        double d0 = this.minX() - x;
        double d1 = this.minY() - y;
        double d2 = this.minZ() - z;
        double d3 = this.maxX() + x;
        double d4 = this.maxY() + y;
        double d5 = this.maxZ() + z;
        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new {@link Cuboid} that is expanded by the given value in all directions. Equivalent to {@link
     * #grow(double, double, double)} with the given value for all 3 params. Negative values will shrink the AABB.
     * <br/>
     * Side lengths will be increased by 2 times the value of the parameter, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see samples on {@link #grow(double, double, double)}).
     *
     * @return A modified AABB.
     */
    public Cuboid grow(double value) {
        return this.grow(value, value, value);
    }

    public Cuboid intersect(Cuboid other) {
        double d0 = Math.max(this.minX(), other.minX());
        double d1 = Math.max(this.minY(), other.minY());
        double d2 = Math.max(this.minZ(), other.minZ());
        double d3 = Math.min(this.maxX(), other.maxX());
        double d4 = Math.min(this.maxY(), other.maxY());
        double d5 = Math.min(this.maxZ(), other.maxZ());
        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    public Cuboid union(Cuboid other) {
        double d0 = Math.min(this.minX(), other.minX());
        double d1 = Math.min(this.minY(), other.minY());
        double d2 = Math.min(this.minZ(), other.minZ());
        double d3 = Math.max(this.maxX(), other.maxX());
        double d4 = Math.max(this.maxY(), other.maxY());
        double d5 = Math.max(this.maxZ(), other.maxZ());
        return new Cuboid(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Offsets the current bounding box by the specified amount.
     */
    public Cuboid offset(double x, double y, double z) {
        return new Cuboid(this.minX() + x, this.minY() + y, this.minZ() + z, this.maxX() + x, this.maxY() + y, this.maxZ() + z);
    }


    public Cuboid offset(Vector3 vec) {
        return this.offset(vec.x, vec.y, vec.z);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateXOffset(Cuboid other, double offsetX) {
        if (other.maxY() > this.minY() && other.minY() < this.maxY() && other.maxZ() > this.minZ() && other.minZ() < this.maxZ()) {
            if (offsetX > 0.0D && other.maxX() <= this.minX()) {
                double d1 = this.minX() - other.maxX();

                if (d1 < offsetX) {
                    offsetX = d1;
                }
            } else if (offsetX < 0.0D && other.minX() >= this.maxX()) {
                double d0 = this.maxX() - other.minX();

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
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateYOffset(Cuboid other, double offsetY) {
        if (other.maxX() > this.minX() && other.minX() < this.maxX() && other.maxZ() > this.minZ() && other.minZ() < this.maxZ()) {
            if (offsetY > 0.0D && other.maxY() <= this.minY()) {
                double d1 = this.minY() - other.maxY();

                if (d1 < offsetY) {
                    offsetY = d1;
                }
            } else if (offsetY < 0.0D && other.minY() >= this.maxY()) {
                double d0 = this.maxY() - other.minY();

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
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateZOffset(Cuboid other, double offsetZ) {
        if (other.maxX() > this.minX() && other.minX() < this.maxX() && other.maxY() > this.minY() && other.minY() < this.maxY()) {
            if (offsetZ > 0.0D && other.maxZ() <= this.minZ()) {
                double d1 = this.minZ() - other.maxZ();

                if (d1 < offsetZ) {
                    offsetZ = d1;
                }
            } else if (offsetZ < 0.0D && other.minZ() >= this.maxZ()) {
                double d0 = this.maxZ() - other.minZ();

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
        return this.intersects(other.minX(), other.minY(), other.minZ(), other.maxX(), other.maxY(), other.maxZ());
    }

    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
        return this.minX() < x2 && this.maxX() > x1 && this.minY() < y2 && this.maxY() > y1 && this.minZ() < z2 && this.maxZ() > z1;
    }

    public boolean intersects(Vector3 min, Vector3 max) {
        return this.intersects(Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z), Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
    }

    /**
     * Returns if the supplied vector is completely inside the bounding box
     */
    public boolean contains(Vector3 vector) {
        return vector != null && vector.isInAABB(getMinPoint(), getMaxPoint());
    }

    public boolean contains(double x, double y, double z) {
        return x >= this.minX() && x < this.maxX() && y >= this.minY() && y < this.maxY() && z >= this.minZ() && z < this.maxZ();
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public double getAverageEdgeLength() {
        double d0 = this.maxX() - this.minX();
        double d1 = this.maxY() - this.minY();
        double d2 = this.maxZ() - this.minZ();
        return (d0 + d1 + d2) / 3.0D;
    }

    public double getXSize() {
        return this.maxX() - this.minX();
    }

    public double getYSize() {
        return this.maxY() - this.minY();
    }

    public double getZSize() {
        return this.maxZ() - this.minZ();
    }

    /**
     * Creates a new {@link Cuboid} that is expanded by the given value in all directions. Equivalent to {@link
     * #grow(double)} with value set to the negative of the value provided here. Passing a negative value to this method
     * values will grow the AABB.
     * <br/>
     * Side lengths will be decreased by 2 times the value of the parameter, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see samples on {@link #grow(double, double, double)}).
     *
     * @return A modified AABB.
     */
    public Cuboid shrink(double value) {
        return this.grow(-value);
    }

    public String toString() {
        return "" + this.minX() + "," + this.minY() + "," + this.minZ() + ":" + this.maxX() + "," + this.maxY() + "," + this.maxZ() + "";
    }

    public boolean hasNaN() {
        return Double.isNaN(this.minX()) || Double.isNaN(this.minY()) || Double.isNaN(this.minZ()) || Double.isNaN(this.maxX()) || Double.isNaN(this.maxY()) || Double.isNaN(this.maxZ());
    }


    public Vector3 getCenter() {
        return new Vector3(this.minX() + (this.maxX() - this.minX()) * 0.5D, this.minY() + (this.maxY() - this.minY()) * 0.5D, this.minZ() + (this.maxZ() - this.minZ()) * 0.5D);
    }

    public enum SizeType {
        FULL, AIR, OTHER;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}