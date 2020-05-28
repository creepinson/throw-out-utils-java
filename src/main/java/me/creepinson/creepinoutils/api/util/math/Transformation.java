package me.creepinson.creepinoutils.api.util.math;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project creepinoutils
 **/
public class Transformation {

    public Vector center;

    public int rotX;
    public int rotY;
    public int rotZ;

    public Vector doubledRotationCenter;
    public Vector offset;


    public Transformation(int[] array) {
        if (array.length != 13)
            throw new IllegalArgumentException("Invalid array when creating door transformation!");

        center = new Vector(array[0], array[1], array[2]);
        rotX = array[3];
        rotY = array[4];
        rotZ = array[5];
        doubledRotationCenter = new Vector(array[6], array[7], array[8]);
        this.offset = new Vector(array[9], array[10], array[11]);
    }

    public Transformation(Vector center, int rotX, int rotY, int rotZ, Vector doubledRotationCenter, Vector offset) {
        this.center = center;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.doubledRotationCenter = doubledRotationCenter;
        this.offset = offset;
    }

    public Transformation(Vector center, Rotation rotation) {
        this.center = center;
        this.rotX = rotation.axis == Facing.Axis.X ? (rotation.clockwise ? 1 : -1) : 0;
        this.rotY = rotation.axis == Facing.Axis.Y ? (rotation.clockwise ? 1 : -1) : 0;
        this.rotZ = rotation.axis == Facing.Axis.Z ? (rotation.clockwise ? 1 : -1) : 0;
        this.doubledRotationCenter = new Vector(0, 0, 0);
        this.offset = new Vector();

    }

    public Rotation getRotation(Facing.Axis axis) {
        switch (axis) {
            case X:
                if (rotX == 0)
                    return null;
                return Rotation.getRotation(axis, rotX > 0);
            case Y:
                if (rotY == 0)
                    return null;
                return Rotation.getRotation(axis, rotY > 0);
            case Z:
                if (rotZ == 0)
                    return null;
                return Rotation.getRotation(axis, rotZ > 0);
        }
        return null;
    }

    public Vector transform(Vector pos) {
        pos = pos.sub(center);
        if (rotX != 0) {
            Rotation rotation = getRotation(Facing.Axis.X);
            for (int i = 0; i < Math.abs(rotX); i++)
                pos = RotationUtils.rotate(pos, rotation);
        }
        if (rotY != 0) {
            Rotation rotation = getRotation(Facing.Axis.Y);
            for (int i = 0; i < Math.abs(rotY); i++)
                pos = RotationUtils.rotate(pos, rotation);
        }
        if (rotZ != 0) {
            Rotation rotation = getRotation(Facing.Axis.Z);
            for (int i = 0; i < Math.abs(rotZ); i++)
                pos = RotationUtils.rotate(pos, rotation);
        }

        pos = pos.add(center);

        if (offset != null)
            pos = pos.add(offset);
        return pos;
    }

/*    public void transform(LittleAbsolutePreviews previews) {
        if (rotX != 0) {
            Rotation rotation = getRotation(Axis.X);
            for (int i = 0; i < Math.abs(rotX); i++)
                previews.rotatePreviews(rotation, doubledRotationCenter);
        }
        if (rotY != 0) {
            Rotation rotation = getRotation(Axis.Y);
            for (int i = 0; i < Math.abs(rotY); i++)
                previews.rotatePreviews(rotation, doubledRotationCenter);
        }
        if (rotZ != 0) {
            Rotation rotation = getRotation(Axis.Z);
            for (int i = 0; i < Math.abs(rotZ); i++)
                previews.rotatePreviews(rotation, doubledRotationCenter);
        }

        if (offset != null)
            previews.movePreviews(offset.getContext(), offset.getVec());
    }*/

    public int[] array() {
        return new int[]{center.intX(), center.intY(), center.intZ(), rotX, rotY, rotZ, doubledRotationCenter.intX(), doubledRotationCenter.intY(), doubledRotationCenter.intZ(), offset.intX(), offset.intY(), offset.intZ()};
    }

    @Override
    public String toString() {
        return "center:" + center.intX() + "," + center.intY() + "," + center.intZ() + ";rotation:" + rotX + "," + rotY + "," + rotZ + ";offset:" + offset.toString();
    }
}
