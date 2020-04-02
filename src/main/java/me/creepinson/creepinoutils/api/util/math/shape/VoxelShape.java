package me.creepinson.creepinoutils.api.util.math.shape;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import me.creepinson.creepinoutils.api.util.math.*;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/

public abstract class VoxelShape {
    protected final VoxelShapePart part;
    @Nullable
    private VoxelShape[] projectionCache;

    VoxelShape(VoxelShapePart part) {
        this.part = part;
    }

    public double getStart(Facing.Axis axis) {
        int i = this.part.getStart(axis);
        return i >= this.part.getSize(axis) ? Double.POSITIVE_INFINITY : this.getValueUnchecked(axis, i);
    }

    public double getEnd(Facing.Axis axis) {
        int i = this.part.getEnd(axis);
        return i <= 0 ? Double.NEGATIVE_INFINITY : this.getValueUnchecked(axis, i);
    }

    public BoundingBox getBoundingBox() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("No bounds for empty shape.");
        } else {
            return new BoundingBox(this.getStart(Facing.Axis.X), this.getStart(Facing.Axis.Y), this.getStart(Facing.Axis.Z), this.getEnd(Facing.Axis.X), this.getEnd(Facing.Axis.Y), this.getEnd(Facing.Axis.Z));
        }
    }

    protected double getValueUnchecked(Facing.Axis axis, int index) {
        return this.getValues(axis).getDouble(index);
    }

    protected abstract DoubleList getValues(Facing.Axis axis);

    public boolean isEmpty() {
        return this.part.isEmpty();
    }

    public VoxelShape withOffset(double xOffset, double yOffset, double zOffset) {
        return (VoxelShape) (this.isEmpty() ? VoxelShapes.empty() : new VoxelShapeArray(this.part, (DoubleList) (new OffsetDoubleList(this.getValues(Facing.Axis.X), xOffset)), (DoubleList) (new OffsetDoubleList(this.getValues(Facing.Axis.Y), yOffset)), (DoubleList) (new OffsetDoubleList(this.getValues(Facing.Axis.Z), zOffset))));
    }

    public VoxelShape simplify() {
        VoxelShape[] aVoxelShape = new VoxelShape[]{VoxelShapes.empty()};
        this.forEachBox((p_197763_1_, p_197763_3_, p_197763_5_, p_197763_7_, p_197763_9_, p_197763_11_) -> {
            aVoxelShape[0] = VoxelShapes.combine(aVoxelShape[0], VoxelShapes.create(p_197763_1_, p_197763_3_, p_197763_5_, p_197763_7_, p_197763_9_, p_197763_11_), IBooleanFunction.OR);
        });
        return aVoxelShape[0];
    }

    public void forEachEdge(VoxelShapes.ILineConsumer action) {
        this.part.forEachEdge((p_197750_2_, p_197750_3_, p_197750_4_, p_197750_5_, p_197750_6_, p_197750_7_) -> {
            action.consume(this.getValueUnchecked(Facing.Axis.X, p_197750_2_), this.getValueUnchecked(Facing.Axis.Y, p_197750_3_), this.getValueUnchecked(Facing.Axis.Z, p_197750_4_), this.getValueUnchecked(Facing.Axis.X, p_197750_5_), this.getValueUnchecked(Facing.Axis.Y, p_197750_6_), this.getValueUnchecked(Facing.Axis.Z, p_197750_7_));
        }, true);
    }

    public void forEachBox(VoxelShapes.ILineConsumer action) {
        DoubleList doublelist = this.getValues(Facing.Axis.X);
        DoubleList doublelist1 = this.getValues(Facing.Axis.Y);
        DoubleList doublelist2 = this.getValues(Facing.Axis.Z);
        this.part.forEachBox((p_224789_4_, p_224789_5_, p_224789_6_, p_224789_7_, p_224789_8_, p_224789_9_) -> {
            action.consume(doublelist.getDouble(p_224789_4_), doublelist1.getDouble(p_224789_5_), doublelist2.getDouble(p_224789_6_), doublelist.getDouble(p_224789_7_), doublelist1.getDouble(p_224789_8_), doublelist2.getDouble(p_224789_9_));
        }, true);
    }

    public List<BoundingBox> toBoundingBoxList() {
        List<BoundingBox> list = Lists.newArrayList();
        this.forEachBox((p_203431_1_, p_203431_3_, p_203431_5_, p_203431_7_, p_203431_9_, p_203431_11_) -> {
            list.add(new BoundingBox(p_203431_1_, p_203431_3_, p_203431_5_, p_203431_7_, p_203431_9_, p_203431_11_));
        });
        return list;
    }

    public double min(Facing.Axis axis, double p_197764_2_, double p_197764_4_) {
        Facing.Axis Facing$axis = AxisRotation.FORWARD.rotate(axis);
        Facing.Axis Facing$axis1 = AxisRotation.BACKWARD.rotate(axis);
        int i = this.getClosestIndex(Facing$axis, p_197764_2_);
        int j = this.getClosestIndex(Facing$axis1, p_197764_4_);
        int k = this.part.firstFilled(axis, i, j);
        return k >= this.part.getSize(axis) ? Double.POSITIVE_INFINITY : this.getValueUnchecked(axis, k);
    }

    public double max(Facing.Axis p_197760_1_, double p_197760_2_, double p_197760_4_) {
        Facing.Axis Facing$axis = AxisRotation.FORWARD.rotate(p_197760_1_);
        Facing.Axis Facing$axis1 = AxisRotation.BACKWARD.rotate(p_197760_1_);
        int i = this.getClosestIndex(Facing$axis, p_197760_2_);
        int j = this.getClosestIndex(Facing$axis1, p_197760_4_);
        int k = this.part.lastFilled(p_197760_1_, i, j);
        return k <= 0 ? Double.NEGATIVE_INFINITY : this.getValueUnchecked(p_197760_1_, k);
    }

    protected int getClosestIndex(Facing.Axis axis, double position) {
        return MathUtils.binarySearch(0, this.part.getSize(axis) + 1, (p_197761_4_) -> {
            if (p_197761_4_ < 0) {
                return false;
            } else if (p_197761_4_ > this.part.getSize(axis)) {
                return true;
            } else {
                return position < this.getValueUnchecked(axis, p_197761_4_);
            }
        }) - 1;
    }

    protected boolean contains(double x, double y, double z) {
        return this.part.contains(this.getClosestIndex(Facing.Axis.X, x), this.getClosestIndex(Facing.Axis.Y, y), this.getClosestIndex(Facing.Axis.Z, z));
    }

    public VoxelShape project(Facing side) {
        if (!this.isEmpty() && this != VoxelShapes.fullCube()) {
            if (this.projectionCache != null) {
                VoxelShape VoxelShape = this.projectionCache[side.ordinal()];
                if (VoxelShape != null) {
                    return VoxelShape;
                }
            } else {
                this.projectionCache = new VoxelShape[6];
            }

            VoxelShape VoxelShape1 = this.doProject(side);
            this.projectionCache[side.ordinal()] = VoxelShape1;
            return VoxelShape1;
        } else {
            return this;
        }
    }

    private VoxelShape doProject(Facing side) {
        Facing.Axis Facing$axis = side.getAxis();
        Facing.AxisDirection Facing$AxisDirection = side.getAxisDirection();
        DoubleList doublelist = this.getValues(Facing$axis);
        if (doublelist.size() == 2 && DoubleMath.fuzzyEquals(doublelist.getDouble(0), 0.0D, 1.0E-7D) && DoubleMath.fuzzyEquals(doublelist.getDouble(1), 1.0D, 1.0E-7D)) {
            return this;
        } else {
            int i = this.getClosestIndex(Facing$axis, Facing$AxisDirection == Facing.AxisDirection.POSITIVE ? 0.9999999D : 1.0E-7D);
            return new SplitVoxelShape(this, Facing$axis, i);
        }
    }

    public double getAllowedOffset(Facing.Axis movementAxis, BoundingBox collisionBox, double desiredOffset) {
        return this.getAllowedOffset(AxisRotation.from(movementAxis, Facing.Axis.X), collisionBox, desiredOffset);
    }

    protected double getAllowedOffset(AxisRotation movementAxis, BoundingBox collisionBox, double desiredOffset) {
        if (this.isEmpty()) {
            return desiredOffset;
        } else if (Math.abs(desiredOffset) < 1.0E-7D) {
            return 0.0D;
        } else {
            AxisRotation axisrotation = movementAxis.reverse();
            Facing.Axis Facing$axis = axisrotation.rotate(Facing.Axis.X);
            Facing.Axis Facing$axis1 = axisrotation.rotate(Facing.Axis.Y);
            Facing.Axis Facing$axis2 = axisrotation.rotate(Facing.Axis.Z);
            double d0 = collisionBox.getMax(Facing$axis);
            double d1 = collisionBox.getMin(Facing$axis);
            int i = this.getClosestIndex(Facing$axis, d1 + 1.0E-7D);
            int j = this.getClosestIndex(Facing$axis, d0 - 1.0E-7D);
            int k = Math.max(0, this.getClosestIndex(Facing$axis1, collisionBox.getMin(Facing$axis1) + 1.0E-7D));
            int l = Math.min(this.part.getSize(Facing$axis1), this.getClosestIndex(Facing$axis1, collisionBox.getMax(Facing$axis1) - 1.0E-7D) + 1);
            int i1 = Math.max(0, this.getClosestIndex(Facing$axis2, collisionBox.getMin(Facing$axis2) + 1.0E-7D));
            int j1 = Math.min(this.part.getSize(Facing$axis2), this.getClosestIndex(Facing$axis2, collisionBox.getMax(Facing$axis2) - 1.0E-7D) + 1);
            int k1 = this.part.getSize(Facing$axis);
            if (desiredOffset > 0.0D) {
                for (int l1 = j + 1; l1 < k1; ++l1) {
                    for (int i2 = k; i2 < l; ++i2) {
                        for (int j2 = i1; j2 < j1; ++j2) {
                            if (this.part.containsWithRotation(axisrotation, l1, i2, j2)) {
                                double d2 = this.getValueUnchecked(Facing$axis, l1) - d0;
                                if (d2 >= -1.0E-7D) {
                                    desiredOffset = Math.min(desiredOffset, d2);
                                }

                                return desiredOffset;
                            }
                        }
                    }
                }
            } else if (desiredOffset < 0.0D) {
                for (int k2 = i - 1; k2 >= 0; --k2) {
                    for (int l2 = k; l2 < l; ++l2) {
                        for (int i3 = i1; i3 < j1; ++i3) {
                            if (this.part.containsWithRotation(axisrotation, k2, l2, i3)) {
                                double d3 = this.getValueUnchecked(Facing$axis, k2 + 1) - d1;
                                if (d3 <= 1.0E-7D) {
                                    desiredOffset = Math.max(desiredOffset, d3);
                                }

                                return desiredOffset;
                            }
                        }
                    }
                }
            }

            return desiredOffset;
        }
    }

    public String toString() {
        return this.isEmpty() ? "EMPTY" : "VoxelShape[" + this.getBoundingBox() + "]";
    }
}