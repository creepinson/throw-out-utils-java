package dev.throwouterror.util.math

import dev.throwouterror.util.math.rotation.Rotation
import dev.throwouterror.util.math.rotation.RotationUtils
import kotlin.math.abs

/**
 * @author Creepinson https:/theoparis.com/about
 * Project creepinoutils
 */
class Transformation {
    var center: Tensor
    var rotX: Int
    var rotY: Int
    var rotZ: Int
    var doubledRotationCenter: Tensor
    var offset: Tensor

    constructor(array: IntArray) {
        require(array.size == 13) { "Invalid array when creating transformation!" }
        center = Tensor(array[0].toDouble(), array[1].toDouble(), array[2].toDouble())
        rotX = array[3]
        rotY = array[4]
        rotZ = array[5]
        doubledRotationCenter = Tensor(array[6].toDouble(), array[7].toDouble(), array[8].toDouble())
        offset = Tensor(array[9].toDouble(), array[10].toDouble(), array[11].toDouble())
    }

    constructor(center: Tensor, rotX: Int, rotY: Int, rotZ: Int, doubledRotationCenter: Tensor, offset: Tensor) {
        this.center = center
        this.rotX = rotX
        this.rotY = rotY
        this.rotZ = rotZ
        this.doubledRotationCenter = doubledRotationCenter
        this.offset = offset
    }

    constructor(center: Tensor, rotation: Rotation) {
        this.center = center
        rotX = if (rotation.axis === Direction.Axis.X) if (rotation.clockwise) 1 else -1 else 0
        rotY = if (rotation.axis === Direction.Axis.Y) if (rotation.clockwise) 1 else -1 else 0
        rotZ = if (rotation.axis === Direction.Axis.Z) if (rotation.clockwise) 1 else -1 else 0
        doubledRotationCenter = Tensor.ZERO_VECTOR.clone()
        offset = Tensor.ZERO_VECTOR.clone()
    }

    fun getRotation(axis: Direction.Axis?): Rotation? {
        when (axis) {
            Direction.Axis.X -> {
                return if (rotX == 0) null else Rotation.getRotation(axis, rotX > 0)
            }
            Direction.Axis.Y -> {
                return if (rotY == 0) null else Rotation.getRotation(axis, rotY > 0)
            }
            Direction.Axis.Z -> {
                return if (rotZ == 0) null else Rotation.getRotation(axis, rotZ > 0)
            }
        }
        return null
    }

    fun transform(position: Tensor?): Tensor {
        var pos = position
        pos = pos!!.sub(center)
        if (rotX != 0) {
            val rotation = getRotation(Direction.Axis.X)
            for (i in 0 until abs(rotX)) pos = RotationUtils.rotate(pos!!, rotation)
        }
        if (rotY != 0) {
            val rotation = getRotation(Direction.Axis.Y)
            for (i in 0 until abs(rotY)) pos = RotationUtils.rotate(pos!!, rotation)
        }
        if (rotZ != 0) {
            val rotation = getRotation(Direction.Axis.Z)
            for (i in 0 until abs(rotZ)) pos = RotationUtils.rotate(pos!!, rotation)
        }
        pos = pos!!.add(center)
        if (offset != null) pos = pos.add(offset)
        return pos
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
    fun array(): IntArray {
        return intArrayOf(center.x.toInt(), center.y.toInt(), center.z.toInt(), rotX, rotY, rotZ, doubledRotationCenter.x.toInt(), doubledRotationCenter.y.toInt(), doubledRotationCenter.z.toInt(), offset.x.toInt(), offset.y.toInt(), offset.z.toInt())
    }

    override fun toString(): String {
        return "center:$center;rotation:$rotX,$rotY,$rotZ;offset:$offset"
    }
}