package me.creepinson.creepinoutils.api.util.math;

import me.creepinson.creepinoutils.api.util.data.game.IPlayer;

public class Range4D {

    public int dimensionId;
    public int xMin;
    public int yMin;
    public int zMin;
    public int xMax;
    public int yMax;
    public int zMax;

    public Range4D(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int dimension) {
        xMin = minX;
        yMin = minY;
        zMin = minZ;
        xMax = maxX;
        yMax = maxY;
        zMax = maxZ;
        dimensionId = dimension;
    }

    public Range4D expandChunks(int chunks) {
        xMin -= chunks * 16;
        xMax += chunks * 16;
        zMin -= chunks * 16;
        zMax += chunks * 16;
        return this;
    }

    public Range4D expandFromCenter(int radius) {
        xMin -= radius;
        xMax += radius;
        zMin -= radius;
        zMax += radius;
        return this;
    }

    public boolean intersects(Range4D range) {
        return (xMax + 0.99999 > range.xMin) && (range.xMax + 0.99999 > xMin) && (yMax + 0.99999 > range.yMin) &&
                (range.yMax + 0.99999 > yMin) && (zMax + 0.99999 > range.zMin) && (range.zMax + 0.99999 > zMin);
    }

    public boolean hasPlayerInRange(IPlayer player) {
        if (player.getWorld().getDimension() != dimensionId) {
            return false;
        }
        //Ignore height for partial Cubic chunks support as range comparision gets used ignoring player height normally anyways
        int radius = 32 * 16;
        int playerX = (int) player.getPosition().x;
        int playerZ = (int) player.getPosition().z;
        //playerX/Z + radius is the max, so to stay in line with how it was before,
        // it has an extra + 1 added to it
        return (playerX + radius + 1.99999 > xMin) && (xMax + 0.99999 > playerX - radius) &&
                (playerZ + radius + 1.99999 > zMin) && (zMax + 0.99999 > playerZ - radius);
    }

    @Override
    public Range4D clone() {
        return new Range4D(xMin, yMin, zMin, xMax, yMax, zMax, dimensionId);
    }

    @Override
    public String toString() {
        return "[Range4D: " + xMin + ", " + yMin + ", " + zMin + ", " + xMax + ", " + yMax + ", " + zMax + ", dim=" + dimensionId + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Range4D &&
                ((Range4D) obj).xMin == xMin &&
                ((Range4D) obj).yMin == yMin &&
                ((Range4D) obj).zMin == zMin &&
                ((Range4D) obj).xMax == xMax &&
                ((Range4D) obj).yMax == yMax &&
                ((Range4D) obj).zMax == zMax &&
                ((Range4D) obj).dimensionId == dimensionId;
    }

    @Override
    public int hashCode() {
        int code = 1;
        code = 31 * code + xMin;
        code = 31 * code + yMin;
        code = 31 * code + zMin;
        code = 31 * code + xMax;
        code = 31 * code + yMax;
        code = 31 * code + zMax;
        code = 31 * code + dimensionId;
        return code;
    }
}