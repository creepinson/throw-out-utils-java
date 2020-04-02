package me.creepinson.creepinoutils.api.util.math.shape;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import me.creepinson.creepinoutils.api.util.math.DoubleRangeList;
import me.creepinson.creepinoutils.api.util.math.Facing;
import me.creepinson.creepinoutils.api.util.math.MathUtils;

public final class VoxelShapeCube extends VoxelShape {
    protected VoxelShapeCube(VoxelShapePart p_i48182_1_) {
        super(p_i48182_1_);
    }

    protected DoubleList getValues(Facing.Axis axis) {
        return new DoubleRangeList(this.part.getSize(axis));
    }

    protected int getClosestIndex(Facing.Axis axis, double position) {
        int i = this.part.getSize(axis);
        return MathUtils.clamp(MathUtils.floor(position * (double) i), -1, i);
    }
}