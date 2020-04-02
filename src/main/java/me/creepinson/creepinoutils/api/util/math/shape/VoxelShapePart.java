package me.creepinson.creepinoutils.api.util.math.shape;

import me.creepinson.creepinoutils.api.util.math.AxisRotation;
import me.creepinson.creepinoutils.api.util.math.Facing;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * @author mojang https://minecraft.net
 **/
public abstract class VoxelShapePart {
    private static final Facing.Axis[] AXIS_VALUES = Facing.Axis.values();
    protected final int xSize;
    protected final int ySize;
    protected final int zSize;

    protected VoxelShapePart(int xIn, int yIn, int zIn) {
        this.xSize = xIn;
        this.ySize = yIn;
        this.zSize = zIn;
    }

    public boolean containsWithRotation(AxisRotation p_197824_1_, int x, int y, int z) {
        return this.contains(p_197824_1_.getCoordinate(x, y, z, Facing.Axis.X), p_197824_1_.getCoordinate(x, y, z, Facing.Axis.Y), p_197824_1_.getCoordinate(x, y, z, Facing.Axis.Z));
    }

    public boolean contains(int x, int y, int z) {
        if (x >= 0 && y >= 0 && z >= 0) {
            return x < this.xSize && y < this.ySize && z < this.zSize ? this.isFilled(x, y, z) : false;
        } else {
            return false;
        }
    }

    public boolean isFilledWithRotation(AxisRotation rotationIn, int x, int y, int z) {
        return this.isFilled(rotationIn.getCoordinate(x, y, z, Facing.Axis.X), rotationIn.getCoordinate(x, y, z, Facing.Axis.Y), rotationIn.getCoordinate(x, y, z, Facing.Axis.Z));
    }

    public abstract boolean isFilled(int x, int y, int z);

    public abstract void setFilled(int x, int y, int z, boolean expandBounds, boolean filled);

    public boolean isEmpty() {
        for (Facing.Axis Facing$axis : AXIS_VALUES) {
            if (this.getStart(Facing$axis) >= this.getEnd(Facing$axis)) {
                return true;
            }
        }

        return false;
    }

    public abstract int getStart(Facing.Axis axis);

    public abstract int getEnd(Facing.Axis axis);

    public int firstFilled(Facing.Axis p_197826_1_, int p_197826_2_, int p_197826_3_) {
        int i = this.getSize(p_197826_1_);
        if (p_197826_2_ >= 0 && p_197826_3_ >= 0) {
            Facing.Axis Facing$axis = AxisRotation.FORWARD.rotate(p_197826_1_);
            Facing.Axis Facing$axis1 = AxisRotation.BACKWARD.rotate(p_197826_1_);
            if (p_197826_2_ < this.getSize(Facing$axis) && p_197826_3_ < this.getSize(Facing$axis1)) {
                AxisRotation axisrotation = AxisRotation.from(Facing.Axis.X, p_197826_1_);

                for (int j = 0; j < i; ++j) {
                    if (this.isFilledWithRotation(axisrotation, j, p_197826_2_, p_197826_3_)) {
                        return j;
                    }
                }

                return i;
            } else {
                return i;
            }
        } else {
            return i;
        }
    }

    public int lastFilled(Facing.Axis p_197836_1_, int p_197836_2_, int p_197836_3_) {
        if (p_197836_2_ >= 0 && p_197836_3_ >= 0) {
            Facing.Axis Facing$axis = AxisRotation.FORWARD.rotate(p_197836_1_);
            Facing.Axis Facing$axis1 = AxisRotation.BACKWARD.rotate(p_197836_1_);
            if (p_197836_2_ < this.getSize(Facing$axis) && p_197836_3_ < this.getSize(Facing$axis1)) {
                int i = this.getSize(p_197836_1_);
                AxisRotation axisrotation = AxisRotation.from(Facing.Axis.X, p_197836_1_);

                for (int j = i - 1; j >= 0; --j) {
                    if (this.isFilledWithRotation(axisrotation, j, p_197836_2_, p_197836_3_)) {
                        return j + 1;
                    }
                }

                return 0;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int getSize(Facing.Axis axis) {
        return axis.getCoordinate(this.xSize, this.ySize, this.zSize);
    }

    public int getXSize() {
        return this.getSize(Facing.Axis.X);
    }

    public int getYSize() {
        return this.getSize(Facing.Axis.Y);
    }

    public int getZSize() {
        return this.getSize(Facing.Axis.Z);
    }

    public void forEachEdge(VoxelShapePart.ILineConsumer consumer, boolean combine) {
        this.forEachEdgeOnAxis(consumer, AxisRotation.NONE, combine);
        this.forEachEdgeOnAxis(consumer, AxisRotation.FORWARD, combine);
        this.forEachEdgeOnAxis(consumer, AxisRotation.BACKWARD, combine);
    }

    private void forEachEdgeOnAxis(VoxelShapePart.ILineConsumer p_197832_1_, AxisRotation p_197832_2_, boolean p_197832_3_) {
        AxisRotation axisrotation = p_197832_2_.reverse();
        int j = this.getSize(axisrotation.rotate(Facing.Axis.X));
        int k = this.getSize(axisrotation.rotate(Facing.Axis.Y));
        int l = this.getSize(axisrotation.rotate(Facing.Axis.Z));

        for (int i1 = 0; i1 <= j; ++i1) {
            for (int j1 = 0; j1 <= k; ++j1) {
                int i = -1;

                for (int k1 = 0; k1 <= l; ++k1) {
                    int l1 = 0;
                    int i2 = 0;

                    for (int j2 = 0; j2 <= 1; ++j2) {
                        for (int k2 = 0; k2 <= 1; ++k2) {
                            if (this.containsWithRotation(axisrotation, i1 + j2 - 1, j1 + k2 - 1, k1)) {
                                ++l1;
                                i2 ^= j2 ^ k2;
                            }
                        }
                    }

                    if (l1 == 1 || l1 == 3 || l1 == 2 && (i2 & 1) == 0) {
                        if (p_197832_3_) {
                            if (i == -1) {
                                i = k1;
                            }
                        } else {
                            p_197832_1_.consume(axisrotation.getCoordinate(i1, j1, k1, Facing.Axis.X), axisrotation.getCoordinate(i1, j1, k1, Facing.Axis.Y), axisrotation.getCoordinate(i1, j1, k1, Facing.Axis.Z), axisrotation.getCoordinate(i1, j1, k1 + 1, Facing.Axis.X), axisrotation.getCoordinate(i1, j1, k1 + 1, Facing.Axis.Y), axisrotation.getCoordinate(i1, j1, k1 + 1, Facing.Axis.Z));
                        }
                    } else if (i != -1) {
                        p_197832_1_.consume(axisrotation.getCoordinate(i1, j1, i, Facing.Axis.X), axisrotation.getCoordinate(i1, j1, i, Facing.Axis.Y), axisrotation.getCoordinate(i1, j1, i, Facing.Axis.Z), axisrotation.getCoordinate(i1, j1, k1, Facing.Axis.X), axisrotation.getCoordinate(i1, j1, k1, Facing.Axis.Y), axisrotation.getCoordinate(i1, j1, k1, Facing.Axis.Z));
                        i = -1;
                    }
                }
            }
        }

    }

    protected boolean isZAxisLineFull(int fromZ, int toZ, int x, int y) {
        for (int i = fromZ; i < toZ; ++i) {
            if (!this.contains(x, y, i)) {
                return false;
            }
        }

        return true;
    }

    protected void setZAxisLine(int fromZ, int toZ, int x, int y, boolean filled) {
        for (int i = fromZ; i < toZ; ++i) {
            this.setFilled(x, y, i, false, filled);
        }

    }

    protected boolean isXZRectangleFull(int fromX, int toX, int fromZ, int toZ, int x) {
        for (int i = fromX; i < toX; ++i) {
            if (!this.isZAxisLineFull(fromZ, toZ, i, x)) {
                return false;
            }
        }

        return true;
    }

    public void forEachBox(VoxelShapePart.ILineConsumer consumer, boolean combine) {
        VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(this);

        for (int i = 0; i <= this.xSize; ++i) {
            for (int j = 0; j <= this.ySize; ++j) {
                int k = -1;

                for (int l = 0; l <= this.zSize; ++l) {
                    if (voxelshapepart.contains(i, j, l)) {
                        if (combine) {
                            if (k == -1) {
                                k = l;
                            }
                        } else {
                            consumer.consume(i, j, l, i + 1, j + 1, l + 1);
                        }
                    } else if (k != -1) {
                        int i1 = i;
                        int j1 = i;
                        int k1 = j;
                        int l1 = j;
                        voxelshapepart.setZAxisLine(k, l, i, j, false);

                        while (voxelshapepart.isZAxisLineFull(k, l, i1 - 1, k1)) {
                            voxelshapepart.setZAxisLine(k, l, i1 - 1, k1, false);
                            --i1;
                        }

                        while (voxelshapepart.isZAxisLineFull(k, l, j1 + 1, k1)) {
                            voxelshapepart.setZAxisLine(k, l, j1 + 1, k1, false);
                            ++j1;
                        }

                        while (voxelshapepart.isXZRectangleFull(i1, j1 + 1, k, l, k1 - 1)) {
                            for (int i2 = i1; i2 <= j1; ++i2) {
                                voxelshapepart.setZAxisLine(k, l, i2, k1 - 1, false);
                            }

                            --k1;
                        }

                        while (voxelshapepart.isXZRectangleFull(i1, j1 + 1, k, l, l1 + 1)) {
                            for (int j2 = i1; j2 <= j1; ++j2) {
                                voxelshapepart.setZAxisLine(k, l, j2, l1 + 1, false);
                            }

                            ++l1;
                        }

                        consumer.consume(i1, k1, k, j1 + 1, l1 + 1, l);
                        k = -1;
                    }
                }
            }
        }

    }

    public void forEachFace(BitSetVoxelShapePart.IFaceConsumer faceConsumer) {
        this.forEachFaceOnAxis(faceConsumer, AxisRotation.NONE);
        this.forEachFaceOnAxis(faceConsumer, AxisRotation.FORWARD);
        this.forEachFaceOnAxis(faceConsumer, AxisRotation.BACKWARD);
    }

    private void forEachFaceOnAxis(BitSetVoxelShapePart.IFaceConsumer faceConsumer, AxisRotation axisRotationIn) {
        AxisRotation axisrotation = axisRotationIn.reverse();
        Facing.Axis Facing$axis = axisrotation.rotate(Facing.Axis.Z);
        int i = this.getSize(axisrotation.rotate(Facing.Axis.X));
        int j = this.getSize(axisrotation.rotate(Facing.Axis.Y));
        int k = this.getSize(Facing$axis);
        Facing facing = Facing.getFacingFromAxis(Facing.AxisDirection.NEGATIVE, Facing$axis);
        Facing Facing1 = Facing.getFacingFromAxis(Facing.AxisDirection.POSITIVE, Facing$axis);

        for (int l = 0; l < i; ++l) {
            for (int i1 = 0; i1 < j; ++i1) {
                boolean flag = false;

                for (int j1 = 0; j1 <= k; ++j1) {
                    boolean flag1 = j1 != k && this.isFilledWithRotation(axisrotation, l, i1, j1);
                    if (!flag && flag1) {
                        faceConsumer.consume(facing, axisrotation.getCoordinate(l, i1, j1, Facing.Axis.X), axisrotation.getCoordinate(l, i1, j1, Facing.Axis.Y), axisrotation.getCoordinate(l, i1, j1, Facing.Axis.Z));
                    }

                    if (flag && !flag1) {
                        faceConsumer.consume(Facing1, axisrotation.getCoordinate(l, i1, j1 - 1, Facing.Axis.X), axisrotation.getCoordinate(l, i1, j1 - 1, Facing.Axis.Y), axisrotation.getCoordinate(l, i1, j1 - 1, Facing.Axis.Z));
                    }

                    flag = flag1;
                }
            }
        }

    }

    public interface IFaceConsumer {
        void consume(Facing p_consume_1_, int p_consume_2_, int p_consume_3_, int p_consume_4_);
    }

    public interface ILineConsumer {
        void consume(int p_consume_1_, int p_consume_2_, int p_consume_3_, int p_consume_4_, int p_consume_5_, int p_consume_6_);
    }
}