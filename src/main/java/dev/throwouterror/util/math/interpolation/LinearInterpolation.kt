package dev.throwouterror.util.math.interpolation;


import dev.throwouterror.util.math.Tensor;

public class LinearInterpolation extends Interpolation {

    public LinearInterpolation(double[] times, Tensor[] points) {
        super(times, points);
    }

    public LinearInterpolation(Tensor... points) {
        super(points);
    }

    @Override
    public float valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
        return (float) ((getValue(pointIndexNext, dim) - getValue(pointIndex, dim)) * mu + getValue(pointIndex, dim));
    }

}
