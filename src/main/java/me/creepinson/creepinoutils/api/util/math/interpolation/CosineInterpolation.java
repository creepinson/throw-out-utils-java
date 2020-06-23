package me.creepinson.creepinoutils.api.util.math.interpolation;

import me.creepinson.creepinoutils.api.util.math.Vector;

public class CosineInterpolation extends Interpolation {

    public CosineInterpolation(Vector... points) {
        super(points);
    }

    @Override
    public float valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
        double mu2 = (1 - Math.cos(mu * Math.PI)) / 2;
        return (float) (getValue(pointIndex, dim) * (1 - mu2) + getValue(pointIndexNext, dim) * mu2);
    }
}
