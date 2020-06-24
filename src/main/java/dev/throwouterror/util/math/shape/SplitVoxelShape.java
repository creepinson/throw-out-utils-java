package dev.throwouterror.util.math.shape;

import dev.throwouterror.util.math.DoubleRangeList;
import dev.throwouterror.util.math.Facing;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class SplitVoxelShape extends VoxelShape {
    private final VoxelShape shape;
    private final Facing.Axis axis;
    private static final DoubleList field_223415_d = new DoubleRangeList(1);

    public SplitVoxelShape(VoxelShape shapeIn, Facing.Axis axis, int p_i47682_3_) {
        super(makeShapePart(shapeIn.part, axis, p_i47682_3_));
        this.shape = shapeIn;
        this.axis = axis;
    }

    private static VoxelShapePart makeShapePart(VoxelShapePart shapePartIn, Facing.Axis axis, int p_197775_2_) {
        return new PartSplitVoxelShape(shapePartIn, axis.getCoordinate(p_197775_2_, 0, 0), axis.getCoordinate(0, p_197775_2_, 0), axis.getCoordinate(0, 0, p_197775_2_), axis.getCoordinate(p_197775_2_ + 1, shapePartIn.xSize, shapePartIn.xSize), axis.getCoordinate(shapePartIn.ySize, p_197775_2_ + 1, shapePartIn.ySize), axis.getCoordinate(shapePartIn.zSize, shapePartIn.zSize, p_197775_2_ + 1));
    }

    protected DoubleList getValues(Facing.Axis axis) {
        return axis == this.axis ? field_223415_d : this.shape.getValues(axis);
    }
}