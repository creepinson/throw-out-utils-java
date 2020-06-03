package me.creepinson.creepinoutils.api.util.math.shape;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import me.creepinson.creepinoutils.api.util.TransformUtil;
import me.creepinson.creepinoutils.api.util.math.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public final class VoxelShapes {
    private static final VoxelShape FULL_CUBE = TransformUtil.make(() -> {
        VoxelShapePart Voxelshapepart = new BitSetVoxelShapePart(1, 1, 1);
        Voxelshapepart.setFilled(0, 0, 0, true, true);
        return new VoxelShapeCube(Voxelshapepart);
    });
    public static final VoxelShape INFINITY = create(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private static final VoxelShape EMPTY = new VoxelShapeArray(new BitSetVoxelShapePart(0, 0, 0),
            new DoubleArrayList(new double[]{0.0D}),
            new DoubleArrayList(new double[]{0.0D}),
            new DoubleArrayList(new double[]{0.0D}));

    public static VoxelShape empty() {
        return EMPTY;
    }

    public static VoxelShape fullCube() {
        return FULL_CUBE;
    }

    public static VoxelShape create(double x1, double y1, double z1, double x2, double y2, double z2) {
        return create(new Cuboid(x1, y1, z1, x2, y2, z2));
    }

    public static VoxelShape create(Cuboid aabb) {
        int i = getPrecisionBits(aabb.minX(), aabb.maxX());
        int j = getPrecisionBits(aabb.minY(), aabb.maxY());
        int k = getPrecisionBits(aabb.minZ(), aabb.maxZ());
        if (i >= 0 && j >= 0 && k >= 0) {
            if (i == 0 && j == 0 && k == 0) {
                return aabb.contains(0.5D, 0.5D, 0.5D) ? fullCube() : empty();
            } else {
                int l = 1 << i;
                int i1 = 1 << j;
                int j1 = 1 << k;
                int k1 = (int) Math.round(aabb.minX() * (double) l);
                int l1 = (int) Math.round(aabb.maxX() * (double) l);
                int i2 = (int) Math.round(aabb.minY() * (double) i1);
                int j2 = (int) Math.round(aabb.maxY() * (double) i1);
                int k2 = (int) Math.round(aabb.minZ() * (double) j1);
                int l2 = (int) Math.round(aabb.maxZ() * (double) j1);
                BitSetVoxelShapePart bitsetVoxelshapepart = new BitSetVoxelShapePart(l, i1, j1, k1, i2, k2, l1, j2, l2);

                for (long i3 = k1; i3 < (long) l1; ++i3) {
                    for (long j3 = i2; j3 < (long) j2; ++j3) {
                        for (long k3 = k2; k3 < (long) l2; ++k3) {
                            bitsetVoxelshapepart.setFilled((int) i3, (int) j3, (int) k3, false, true);
                        }
                    }
                }

                return new VoxelShapeCube(bitsetVoxelshapepart);
            }
        } else {
            return new VoxelShapeArray(FULL_CUBE.part, new double[]{aabb.minX(), aabb.maxX()},
                    new double[]{aabb.minY(), aabb.maxY()}, new double[]{aabb.minZ(), aabb.maxZ()});
        }
    }

    private static int getPrecisionBits(double p_197885_0_, double p_197885_2_) {
        if (!(p_197885_0_ < -1.0E-7D) && !(p_197885_2_ > 1.0000001D)) {
            for (int i = 0; i <= 3; ++i) {
                double d0 = p_197885_0_ * (double) (1 << i);
                double d1 = p_197885_2_ * (double) (1 << i);
                boolean flag = Math.abs(d0 - Math.floor(d0)) < 1.0E-7D;
                boolean flag1 = Math.abs(d1 - Math.floor(d1)) < 1.0E-7D;
                if (flag && flag1) {
                    return i;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static long lcm(int aa, int bb) {
        return (long) aa * (long) (bb / MathUtils.gcd(aa, bb));
    }

    public static VoxelShape or(VoxelShape shape1, VoxelShape shape2) {
        return combineAndSimplify(shape1, shape2, IBooleanFunction.OR);
    }

    public static VoxelShape or(VoxelShape p_216384_0_, VoxelShape... p_216384_1_) {
        return Arrays.stream(p_216384_1_).reduce(p_216384_0_, VoxelShapes::or);
    }

    public static VoxelShape combineAndSimplify(VoxelShape shape1, VoxelShape shape2, IBooleanFunction function) {
        return combine(shape1, shape2, function).simplify();
    }

    public static VoxelShape combine(VoxelShape shape1, VoxelShape shape2, IBooleanFunction function) {
        if (function.apply(false, false)) {
            throw new IllegalArgumentException();
        } else if (shape1 == shape2) {
            return function.apply(true, true) ? shape1 : empty();
        } else {
            boolean flag = function.apply(true, false);
            boolean flag1 = function.apply(false, true);
            if (shape1.isEmpty()) {
                return flag1 ? shape2 : empty();
            } else if (shape2.isEmpty()) {
                return flag ? shape1 : empty();
            } else {
                IDoubleListMerger idoublelistmerger = makeListMerger(1, shape1.getValues(Facing.Axis.X),
                        shape2.getValues(Facing.Axis.X), flag, flag1);
                IDoubleListMerger idoublelistmerger1 = makeListMerger(idoublelistmerger.func_212435_a().size() - 1,
                        shape1.getValues(Facing.Axis.Y), shape2.getValues(Facing.Axis.Y), flag, flag1);
                IDoubleListMerger idoublelistmerger2 = makeListMerger(
                        (idoublelistmerger.func_212435_a().size() - 1)
                                * (idoublelistmerger1.func_212435_a().size() - 1),
                        shape1.getValues(Facing.Axis.Z), shape2.getValues(Facing.Axis.Z), flag, flag1);
                BitSetVoxelShapePart bitsetVoxelshapepart = BitSetVoxelShapePart.func_197852_a(shape1.part, shape2.part,
                        idoublelistmerger, idoublelistmerger1, idoublelistmerger2, function);
                return idoublelistmerger instanceof DoubleCubeMergingList
                        && idoublelistmerger1 instanceof DoubleCubeMergingList
                        && idoublelistmerger2 instanceof DoubleCubeMergingList
                        ? new VoxelShapeCube(bitsetVoxelshapepart)
                        : new VoxelShapeArray(bitsetVoxelshapepart, idoublelistmerger.func_212435_a(),
                        idoublelistmerger1.func_212435_a(), idoublelistmerger2.func_212435_a());
            }
        }
    }

    public static boolean compare(VoxelShape shape1, VoxelShape shape2, IBooleanFunction function) {
        if (function.apply(false, false)) {
            throw new IllegalArgumentException();
        } else if (shape1 == shape2) {
            return function.apply(true, true);
        } else if (shape1.isEmpty()) {
            return function.apply(false, !shape2.isEmpty());
        } else if (shape2.isEmpty()) {
            return function.apply(!shape1.isEmpty(), false);
        } else {
            boolean flag = function.apply(true, false);
            boolean flag1 = function.apply(false, true);

            for (Facing.Axis Facing$axis : AxisRotation.AXES) {
                if (shape1.getEnd(Facing$axis) < shape2.getStart(Facing$axis) - 1.0E-7D) {
                    return flag || flag1;
                }

                if (shape2.getEnd(Facing$axis) < shape1.getStart(Facing$axis) - 1.0E-7D) {
                    return flag || flag1;
                }
            }

            IDoubleListMerger idoublelistmerger = makeListMerger(1, shape1.getValues(Facing.Axis.X),
                    shape2.getValues(Facing.Axis.X), flag, flag1);
            IDoubleListMerger idoublelistmerger1 = makeListMerger(idoublelistmerger.func_212435_a().size() - 1,
                    shape1.getValues(Facing.Axis.Y), shape2.getValues(Facing.Axis.Y), flag, flag1);
            IDoubleListMerger idoublelistmerger2 = makeListMerger(
                    (idoublelistmerger.func_212435_a().size() - 1) * (idoublelistmerger1.func_212435_a().size() - 1),
                    shape1.getValues(Facing.Axis.Z), shape2.getValues(Facing.Axis.Z), flag, flag1);
            return func_197874_a(idoublelistmerger, idoublelistmerger1, idoublelistmerger2, shape1.part, shape2.part,
                    function);
        }
    }

    private static boolean func_197874_a(IDoubleListMerger p_197874_0_, IDoubleListMerger p_197874_1_,
                                         IDoubleListMerger p_197874_2_, VoxelShapePart p_197874_3_, VoxelShapePart p_197874_4_,
                                         IBooleanFunction p_197874_5_) {
        return !p_197874_0_.forMergedIndexes((p_199861_5_, p_199861_6_,
                                              p_199861_7_) -> p_197874_1_.forMergedIndexes((p_199860_6_, p_199860_7_,
                                                                                            p_199860_8_) -> p_197874_2_.forMergedIndexes((p_199862_7_, p_199862_8_,
                                                                                                                                          p_199862_9_) -> !p_197874_5_.apply(
                p_197874_3_.contains(p_199861_5_, p_199860_6_, p_199862_7_),
                p_197874_4_.contains(p_199861_6_, p_199860_7_, p_199862_8_)))));
    }

    public static double getAllowedOffset(Facing.Axis movementAxis, Cuboid collisionVoxel,
                                          Stream<VoxelShape> possibleHits, double desiredOffset) {
        for (Iterator<VoxelShape> iterator = possibleHits.iterator(); iterator.hasNext(); desiredOffset = iterator
                .next().getAllowedOffset(movementAxis, collisionVoxel, desiredOffset)) {
            if (Math.abs(desiredOffset) < 1.0E-7D) {
                return 0.0D;
            }
        }

        return desiredOffset;
    }

    private static int getDifferenceFloored(double desiredOffset, double min, double max) {
        return desiredOffset > 0.0D ? MathUtils.floor(max + desiredOffset) + 1
                : MathUtils.floor(min + desiredOffset) - 1;
    }

    public static boolean isCubeSideCovered(VoxelShape shape, VoxelShape adjacentShape, Facing side) {
        if (shape == fullCube() && adjacentShape == fullCube()) {
            return true;
        } else if (adjacentShape.isEmpty()) {
            return false;
        } else {
            Facing.Axis Facing$axis = side.getAxis();
            Facing.AxisDirection Facing$AxisDirection = side.getAxisDirection();
            VoxelShape Voxelshape = Facing$AxisDirection == Facing.AxisDirection.POSITIVE ? shape : adjacentShape;
            VoxelShape Voxelshape1 = Facing$AxisDirection == Facing.AxisDirection.POSITIVE ? adjacentShape : shape;
            IBooleanFunction ibooleanfunction = Facing$AxisDirection == Facing.AxisDirection.POSITIVE
                    ? IBooleanFunction.ONLY_FIRST
                    : IBooleanFunction.ONLY_SECOND;
            return MathUtils.fuzzyEquals(Voxelshape.getEnd(Facing$axis), 1.0D, 1.0E-7D)
                    && MathUtils.fuzzyEquals(Voxelshape1.getStart(Facing$axis), 0.0D, 1.0E-7D)
                    && !compare(new SplitVoxelShape(Voxelshape, Facing$axis, Voxelshape.part.getSize(Facing$axis) - 1),
                    new SplitVoxelShape(Voxelshape1, Facing$axis, 0), ibooleanfunction);
        }
    }

    public static VoxelShape func_216387_a(VoxelShape p_216387_0_, Facing p_216387_1_) {
        if (p_216387_0_ == fullCube()) {
            return fullCube();
        } else {
            Facing.Axis Facing$axis = p_216387_1_.getAxis();
            boolean flag;
            int i;
            if (p_216387_1_.getAxisDirection() == Facing.AxisDirection.POSITIVE) {
                flag = MathUtils.fuzzyEquals(p_216387_0_.getEnd(Facing$axis), 1.0D, 1.0E-7D);
                i = p_216387_0_.part.getSize(Facing$axis) - 1;
            } else {
                flag = MathUtils.fuzzyEquals(p_216387_0_.getStart(Facing$axis), 0.0D, 1.0E-7D);
                i = 0;
            }

            return !flag ? empty() : new SplitVoxelShape(p_216387_0_, Facing$axis, i);
        }
    }

    public static boolean doAdjacentCubeSidesFillSquare(VoxelShape shape, VoxelShape adjacentShape, Facing side) {
        if (shape != fullCube() && adjacentShape != fullCube()) {
            Facing.Axis Facing$axis = side.getAxis();
            Facing.AxisDirection Facing$AxisDirection = side.getAxisDirection();
            VoxelShape Voxelshape = Facing$AxisDirection == Facing.AxisDirection.POSITIVE ? shape : adjacentShape;
            VoxelShape Voxelshape1 = Facing$AxisDirection == Facing.AxisDirection.POSITIVE ? adjacentShape : shape;
            if (!MathUtils.fuzzyEquals(Voxelshape.getEnd(Facing$axis), 1.0D, 1.0E-7D)) {
                Voxelshape = empty();
            }

            if (!MathUtils.fuzzyEquals(Voxelshape1.getStart(Facing$axis), 0.0D, 1.0E-7D)) {
                Voxelshape1 = empty();
            }

            return !compare(fullCube(),
                    combine(new SplitVoxelShape(Voxelshape, Facing$axis, Voxelshape.part.getSize(Facing$axis) - 1),
                            new SplitVoxelShape(Voxelshape1, Facing$axis, 0), IBooleanFunction.OR),
                    IBooleanFunction.ONLY_FIRST);
        } else {
            return true;
        }
    }

    public static boolean func_223416_b(VoxelShape p_223416_0_, VoxelShape p_223416_1_) {
        if (p_223416_0_ != fullCube() && p_223416_1_ != fullCube()) {
            if (p_223416_0_.isEmpty() && p_223416_1_.isEmpty()) {
                return false;
            } else {
                return !compare(fullCube(), combine(p_223416_0_, p_223416_1_, IBooleanFunction.OR),
                        IBooleanFunction.ONLY_FIRST);
            }
        } else {
            return true;
        }
    }

    protected static IDoubleListMerger makeListMerger(int p_199410_0_, DoubleList list1, DoubleList list2,
                                                      boolean p_199410_3_, boolean p_199410_4_) {
        int i = list1.size() - 1;
        int j = list2.size() - 1;
        if (list1 instanceof DoubleRangeList && list2 instanceof DoubleRangeList) {
            long k = lcm(i, j);
            if ((long) p_199410_0_ * k <= 256L) {
                return new DoubleCubeMergingList(i, j);
            }
        }

        if (list1.getDouble(i) < list2.getDouble(0) - 1.0E-7D) {
            return new NonOverlappingMerger(list1, list2, false);
        } else if (list2.getDouble(j) < list1.getDouble(0) - 1.0E-7D) {
            return new NonOverlappingMerger(list2, list1, true);
        } else if (i == j && Objects.equals(list1, list2)) {
            if (list1 instanceof SimpleDoubleMerger) {
                return (IDoubleListMerger) list1;
            } else {
                return list2 instanceof SimpleDoubleMerger ? (IDoubleListMerger) list2
                        : new SimpleDoubleMerger(list1);
            }
        } else {
            return new IndirectMerger(list1, list2, p_199410_3_, p_199410_4_);
        }
    }

    public interface ILineConsumer {
        void consume(double p_consume_1_, double p_consume_3_, double p_consume_5_, double p_consume_7_,
                     double p_consume_9_, double p_consume_11_);
    }
}