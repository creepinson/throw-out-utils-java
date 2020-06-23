package me.creepinson.creepinoutils.api.util.math.interpolation;


import me.creepinson.creepinoutils.api.util.math.Vector;

public class LinearInterpolation extends Interpolation {

    public LinearInterpolation(double[] times, Vector[] points) {
        super(times, points);
    }

    public LinearInterpolation(Vector... points) {
        super(points);
    }

    @Override
    public float valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
        return (float) ((getValue(pointIndexNext, dim) - getValue(pointIndex, dim)) * mu + getValue(pointIndex, dim));
    }

}
