package me.creepinson.creepinoutils.api.util.math;

/**
 * @author Creepinson https:/theoparis.com/about
 **/

public enum AxisRotation {
    NONE {
        public int getCoordinate(int x, int y, int z, Facing.Axis axis) {
            return axis.getCoordinate(x, y, z);
        }

        public Facing.Axis rotate(Facing.Axis axisIn) {
            return axisIn;
        }

        public AxisRotation reverse() {
            return this;
        }
    },
    FORWARD {
        public int getCoordinate(int x, int y, int z, Facing.Axis axis) {
            return axis.getCoordinate(z, x, y);
        }

        public Facing.Axis rotate(Facing.Axis axisIn) {
            return AXES[Math.floorMod(axisIn.ordinal() + 1, 3)];
        }

        public AxisRotation reverse() {
            return BACKWARD;
        }
    },
    BACKWARD {
        public int getCoordinate(int x, int y, int z, Facing.Axis axis) {
            return axis.getCoordinate(y, z, x);
        }

        public Facing.Axis rotate(Facing.Axis axisIn) {
            return AXES[Math.floorMod(axisIn.ordinal() - 1, 3)];
        }

        public AxisRotation reverse() {
            return FORWARD;
        }
    };

    public static final Facing.Axis[] AXES = Facing.Axis.values();
    public static final AxisRotation[] AXIS_ROTATIONS = values();

    AxisRotation() {
    }

    public abstract int getCoordinate(int x, int y, int z, Facing.Axis axis);

    public abstract Facing.Axis rotate(Facing.Axis axisIn);

    public abstract AxisRotation reverse();

    public static AxisRotation from(Facing.Axis axis1, Facing.Axis axis2) {
        return AXIS_ROTATIONS[Math.floorMod(axis2.ordinal() - axis1.ordinal(), 3)];
    }
}