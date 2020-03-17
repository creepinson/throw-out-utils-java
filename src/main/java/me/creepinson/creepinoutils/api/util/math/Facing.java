//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.creepinson.creepinoutils.api.util.math;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * @author mojang https://minecraft.net
 */
public enum Facing implements Serializable {
    DOWN(0, 1, -1, "down", Facing.AxisDirection.NEGATIVE, Facing.Axis.Y, new Vector3(0, -1, 0)),
    UP(1, 0, -1, "up", Facing.AxisDirection.POSITIVE, Facing.Axis.Y, new Vector3(0, 1, 0)),
    NORTH(2, 3, 2, "north", Facing.AxisDirection.NEGATIVE, Facing.Axis.Z, new Vector3(0, 0, -1)),
    SOUTH(3, 2, 0, "south", Facing.AxisDirection.POSITIVE, Facing.Axis.Z, new Vector3(0, 0, 1)),
    WEST(4, 5, 1, "west", Facing.AxisDirection.NEGATIVE, Facing.Axis.X, new Vector3(-1, 0, 0)),
    EAST(5, 4, 3, "east", Facing.AxisDirection.POSITIVE, Facing.Axis.X, new Vector3(1, 0, 0));

    private final int index;
    private final int opposite;
    private final int horizontalIndex;
    private final String name;
    private final Facing.Axis axis;
    private final Facing.AxisDirection axisDirection;
    private final Vector3 directionVec;
    public static final Facing[] VALUES = new Facing[6];
    public static final Facing[] HORIZONTALS = new Facing[4];
    private static final Map<String, Facing> NAME_LOOKUP = Maps.newHashMap();

    private Facing(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, Facing.AxisDirection axisDirectionIn, Facing.Axis axisIn, Vector3 directionVecIn) {
        this.index = indexIn;
        this.horizontalIndex = horizontalIndexIn;
        this.opposite = oppositeIn;
        this.name = nameIn;
        this.axis = axisIn;
        this.axisDirection = axisDirectionIn;
        this.directionVec = directionVecIn;
    }

    public int getIndex() {
        return this.index;
    }

    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    public Facing.AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public Facing getOpposite() {
        return byIndex(this.opposite);
    }

    public Facing rotateAround(Facing.Axis axis) {
        switch (axis) {
            case X:
                if (this != WEST && this != EAST) {
                    return this.rotateX();
                }

                return this;
            case Y:
                if (this != UP && this != DOWN) {
                    return this.rotateY();
                }

                return this;
            case Z:
                if (this != NORTH && this != SOUTH) {
                    return this.rotateZ();
                }

                return this;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    public Facing rotateY() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    private Facing rotateX() {
        switch (this) {
            case NORTH:
                return DOWN;
            case EAST:
            case WEST:
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            case SOUTH:
                return UP;
            case UP:
                return NORTH;
            case DOWN:
                return SOUTH;
        }
    }

    private Facing rotateZ() {
        switch (this) {
            case EAST:
                return DOWN;
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                return UP;
            case UP:
                return EAST;
            case DOWN:
                return WEST;
        }
    }

    public Facing rotateYCCW() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int getXOffset() {
        return this.axis == Facing.Axis.X ? this.axisDirection.getOffset() : 0;
    }

    public int getYOffset() {
        return this.axis == Facing.Axis.Y ? this.axisDirection.getOffset() : 0;
    }

    public int getZOffset() {
        return this.axis == Facing.Axis.Z ? this.axisDirection.getOffset() : 0;
    }

    public String getName2() {
        return this.name;
    }

    public Facing.Axis getAxis() {
        return this.axis;
    }

    @Nullable
    public static Facing byName(String name) {
        return name == null ? null : (Facing) NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
    }

    public static Facing byIndex(int index) {
        return VALUES[MathUtils.abs(index % VALUES.length)];
    }

    public static Facing byHorizontalIndex(int horizontalIndexIn) {
        return HORIZONTALS[MathUtils.abs(horizontalIndexIn % HORIZONTALS.length)];
    }

    public static Facing fromAngle(double angle) {
        return byHorizontalIndex(MathUtils.floor(angle / 90.0D + 0.5D) & 3);
    }

    public float getHorizontalAngle() {
        return (float) ((this.horizontalIndex & 3) * 90);
    }

    public static Facing random(Random rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static Facing getFacingFromVector(float x, float y, float z) {
        Facing enumfacing = NORTH;
        float f = 1.4E-45F;
        Facing[] var5 = values();
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Facing enumfacing1 = var5[var7];
            float f1 = x * (float) enumfacing1.directionVec.x + y * (float) enumfacing1.directionVec.y + z * (float) enumfacing1.directionVec.z;
            if (f1 > f) {
                f = f1;
                enumfacing = enumfacing1;
            }
        }

        return enumfacing;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public static Facing getFacingFromAxis(Facing.AxisDirection axisDirectionIn, Facing.Axis axisIn) {
        Facing[] var2 = values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Facing enumfacing = var2[var4];
            if (enumfacing.getAxisDirection() == axisDirectionIn && enumfacing.getAxis() == axisIn) {
                return enumfacing;
            }
        }

        throw new IllegalArgumentException("No such direction: " + axisDirectionIn + " " + axisIn);
    }

    public Vector3 getDirectionVec() {
        return this.directionVec;
    }

    static {
        Facing[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            Facing enumfacing = var0[var2];
            VALUES[enumfacing.index] = enumfacing;
            if (enumfacing.getAxis().isHorizontal()) {
                HORIZONTALS[enumfacing.horizontalIndex] = enumfacing;
            }

            NAME_LOOKUP.put(enumfacing.getName2().toLowerCase(Locale.ROOT), enumfacing);
        }

    }

    public static enum Plane implements Predicate<Facing>, Iterable<Facing> {
        HORIZONTAL,
        VERTICAL;

        private Plane() {
        }

        public Facing[] facings() {
            switch (this) {
                case HORIZONTAL:
                    return new Facing[]{Facing.NORTH, Facing.EAST, Facing.SOUTH, Facing.WEST};
                case VERTICAL:
                    return new Facing[]{Facing.UP, Facing.DOWN};
                default:
                    throw new Error("Someone's been tampering with the universe!");
            }
        }

        public Facing random(Random rand) {
            Facing[] aenumfacing = this.facings();
            return aenumfacing[rand.nextInt(aenumfacing.length)];
        }

        public boolean apply(@Nullable Facing p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.getAxis().getPlane() == this;
        }

        public Iterator<Facing> iterator() {
            return Iterators.forArray(this.facings());
        }
    }

    public static enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int offset;
        private final String description;

        private AxisDirection(int offset, String description) {
            this.offset = offset;
            this.description = description;
        }

        public int getOffset() {
            return this.offset;
        }

        public String toString() {
            return this.description;
        }
    }

    public static enum Axis implements Predicate<Facing>, Serializable {
        X("x", Facing.Plane.HORIZONTAL),
        Y("y", Facing.Plane.VERTICAL),
        Z("z", Facing.Plane.HORIZONTAL);

        private static final Map<String, Axis> NAME_LOOKUP = Maps.newHashMap();
        private final String name;
        private final Facing.Plane plane;

        private Axis(String name, Facing.Plane plane) {
            this.name = name;
            this.plane = plane;
        }

        @Nullable
        public static Facing.Axis byName(String name) {
            return name == null ? null : (Facing.Axis) NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
        }

        public String getName2() {
            return this.name;
        }

        public boolean isVertical() {
            return this.plane == Facing.Plane.VERTICAL;
        }

        public boolean isHorizontal() {
            return this.plane == Facing.Plane.HORIZONTAL;
        }

        public String toString() {
            return this.name;
        }

        public boolean apply(@Nullable Facing p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.getAxis() == this;
        }

        public Facing.Plane getPlane() {
            return this.plane;
        }

        public String getName() {
            return this.name;
        }

        static {
            Facing.Axis[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                Facing.Axis enumfacing$axis = var0[var2];
                NAME_LOOKUP.put(enumfacing$axis.getName2().toLowerCase(Locale.ROOT), enumfacing$axis);
            }

        }
    }
}
