//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package dev.throwouterror.util.math;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dev.throwouterror.util.ArrayUtils;
import dev.throwouterror.util.ISerializable;

/**
 * @author mojang https://minecraft.net
 */
public enum Facing implements Serializable {
    DOWN(0, 1, -1, "down", Facing.AxisDirection.NEGATIVE, Facing.Axis.Y, new Tensor(0, -1, 0)),
    UP(1, 0, -1, "up", Facing.AxisDirection.POSITIVE, Facing.Axis.Y, new Tensor(0, 1, 0)),
    NORTH(2, 3, 2, "north", Facing.AxisDirection.NEGATIVE, Facing.Axis.Z, new Tensor(0, 0, -1)),
    SOUTH(3, 2, 0, "south", Facing.AxisDirection.POSITIVE, Facing.Axis.Z, new Tensor(0, 0, 1)),
    WEST(4, 5, 1, "west", Facing.AxisDirection.NEGATIVE, Facing.Axis.X, new Tensor(-1, 0, 0)),
    EAST(5, 4, 3, "east", Facing.AxisDirection.POSITIVE, Facing.Axis.X, new Tensor(1, 0, 0));

    private final int index;
    private final int opposite;
    private final int horizontalIndex;
    private final String name;
    private final Facing.Axis axis;
    private final Facing.AxisDirection axisDirection;
    private final Tensor FacingVec;
    private static final Facing[] VALUES = values();
    private static final Map<String, Facing> NAME_LOOKUP = Arrays.stream(VALUES)
            .collect(Collectors.toMap(Facing::getName2, (p_199787_0_) -> p_199787_0_));
    private static final Facing[] BY_INDEX = Arrays.stream(VALUES)
            .sorted(Comparator.comparingInt((p_199790_0_) -> p_199790_0_.index))
            .toArray((p_199788_0_) -> new Facing[p_199788_0_]);
    private static final Facing[] BY_HORIZONTAL_INDEX = Arrays.stream(VALUES)
            .filter((p_199786_0_) -> p_199786_0_.getAxis().isHorizontal())
            .sorted(Comparator.comparingInt((p_199789_0_) -> p_199789_0_.horizontalIndex))
            .toArray((p_199791_0_) -> new Facing[p_199791_0_]);

    Facing(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, Facing.AxisDirection AxisDirectionIn,
           Facing.Axis axisIn, Tensor FacingVecIn) {
        this.index = indexIn;
        this.horizontalIndex = horizontalIndexIn;
        this.opposite = oppositeIn;
        this.name = nameIn;
        this.axis = axisIn;
        this.axisDirection = AxisDirectionIn;
        this.FacingVec = FacingVecIn;
    }

    private static Facing[] compose(Facing first, Facing second, Facing third) {
        return new Facing[]{first, second, third, third.getOpposite(), second.getOpposite(), first.getOpposite()};
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

    public Facing rotateY() {
        switch (this) {
            case NORTH:
                return EAST;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            case EAST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public Facing rotateYCCW() {
        switch (this) {
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            case EAST:
                return NORTH;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int getXOffset() {
        return this.FacingVec.intX();
    }

    public int getYOffset() {
        return this.FacingVec.intY();
    }

    public int getZOffset() {
        return this.FacingVec.intZ();
    }

    public Tensor getDirectionVec() {
        return new Tensor((float) this.getXOffset(), (float) this.getYOffset(), (float) this.getZOffset());
    }

    public String getName2() {
        return this.name;
    }

    public Facing.Axis getAxis() {
        return this.axis;
    }

    public static Facing byName(String name) {
        return name == null ? null : NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
    }

    public static Facing byIndex(int index) {
        return BY_INDEX[MathUtils.abs(index % BY_INDEX.length)];
    }

    public static Facing byHorizontalIndex(int horizontalIndexIn) {
        return BY_HORIZONTAL_INDEX[MathUtils.abs(horizontalIndexIn % BY_HORIZONTAL_INDEX.length)];
    }

    public static Facing fromAngle(double angle) {
        return byHorizontalIndex(MathUtils.floor(angle / 90.0D + 0.5D) & 3);
    }

    public static Facing getFacingFromAxisDirection(Facing.Axis axisIn, Facing.AxisDirection AxisDirectionIn) {
        switch (axisIn) {
            case X:
                return AxisDirectionIn == Facing.AxisDirection.POSITIVE ? EAST : WEST;
            case Y:
                return AxisDirectionIn == Facing.AxisDirection.POSITIVE ? UP : DOWN;
            case Z:
            default:
                return AxisDirectionIn == Facing.AxisDirection.POSITIVE ? SOUTH : NORTH;
        }
    }

    public float getHorizontalAngle() {
        return (float) ((this.horizontalIndex & 3) * 90);
    }

    public static Facing random(Random rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static Facing getFacingFromTensor(double x, double y, double z) {
        return getFacingFromTensor((float) x, (float) y, (float) z);
    }

    public static Facing getFacingFromTensor(float x, float y, float z) {
        Facing Facing = NORTH;
        float f = Float.MIN_VALUE;

        for (Facing Facing1 : VALUES) {
            float f1 = x * (float) Facing1.FacingVec.intX() + y * (float) Facing1.FacingVec.intY()
                    + z * (float) Facing1.FacingVec.intZ();
            if (f1 > f) {
                f = f1;
                Facing = Facing1;
            }
        }

        return Facing;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public static Facing getFacingFromAxis(Facing.AxisDirection AxisDirectionIn, Facing.Axis axisIn) {
        for (Facing Facing : values()) {
            if (Facing.getAxisDirection() == AxisDirectionIn && Facing.getAxis() == axisIn) {
                return Facing;
            }
        }

        throw new IllegalArgumentException("No such Facing: " + AxisDirectionIn + " " + axisIn);
    }

    public Tensor getFacingVec() {
        return this.FacingVec;
    }

    public enum Axis implements Serializable, java.util.function.Predicate<Facing> {
        X("x") {
            public int getCoordinate(int x, int y, int z) {
                return x;
            }

            public double getCoordinate(double x, double y, double z) {
                return x;
            }
        },
        Y("y") {
            public int getCoordinate(int x, int y, int z) {
                return y;
            }

            public double getCoordinate(double x, double y, double z) {
                return y;
            }
        },
        Z("z") {
            public int getCoordinate(int x, int y, int z) {
                return z;
            }

            public double getCoordinate(double x, double y, double z) {
                return z;
            }
        };

        private static final Map<String, Facing.Axis> NAME_LOOKUP = Arrays.stream(values())
                .collect(Collectors.toMap(Facing.Axis::getName2, (p_199785_0_) -> {
                    return p_199785_0_;
                }));
        private final String name;

        Axis(String nameIn) {
            this.name = nameIn;
        }
        // TODO: Facing axis from Tensor
        /* 
        public static Facing.Axis fromTensor(Tensor v) {
            
        } */

        public static Facing.Axis byName(String name) {
            return NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
        }

        public String getName2() {
            return this.name;
        }

        public boolean isVertical() {
            return this == Y;
        }

        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.name;
        }
        
        public static Facing.Axis random(Random p_218393_0_) {
            return values()[p_218393_0_.nextInt(values().length)];
        }

        public boolean test(Facing p_test_1_) {
            return p_test_1_ != null && p_test_1_.getAxis() == this;
        }

        public Facing.Plane getPlane() {
            switch (this) {
                case X:
                case Z:
                    return Facing.Plane.HORIZONTAL;
                case Y:
                    return Facing.Plane.VERTICAL;
                default:
                    throw new Error("Someone's been tampering with the universe!");
            }
        }

        public String getName() {
            return this.name;
        }

        public abstract int getCoordinate(int x, int y, int z);

        public abstract double getCoordinate(double x, double y, double z);
    }

    public enum AxisDirection {
        POSITIVE(1, "Towards positive"), NEGATIVE(-1, "Towards negative");

        private final int offset;
        private final String description;

        AxisDirection(int offset, String description) {
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

    public enum Plane implements Iterable<Facing>, Predicate<Facing> {
        HORIZONTAL(new Facing[]{Facing.NORTH, Facing.EAST, Facing.SOUTH, Facing.WEST},
                new Facing.Axis[]{Facing.Axis.X, Facing.Axis.Z}),
        VERTICAL(new Facing[]{Facing.UP, Facing.DOWN}, new Facing.Axis[]{Facing.Axis.Y});

        private final Facing[] facingValues;
        private final Facing.Axis[] axisValues;

        Plane(Facing[] facingValuesIn, Facing.Axis[] axisValuesIn) {
            this.facingValues = facingValuesIn;
            this.axisValues = axisValuesIn;
        }

        public Facing random(Random rand) {
            return this.facingValues[rand.nextInt(this.facingValues.length)];
        }

        public boolean test(Facing p_test_1_) {
            return p_test_1_ != null && p_test_1_.getAxis().getPlane() == this;
        }

        public Iterator<Facing> iterator() {
            return ArrayUtils.forArray(this.facingValues);
        }
    }
}