package dev.throwouterror.util.math.interpolation;

import dev.throwouterror.util.math.Tensor;

public class CosineInterpolation extends Interpolation {

	public CosineInterpolation(Tensor... points) {
		super(points);
	}

	@Override
	public float valueAt(double mu, int pointIndex, int pointIndexNext, int dim) {
		double mu2 = (1 - Math.cos(mu * Math.PI)) / 2;
		return (float) (getValue(pointIndex, dim) * (1 - mu2) + getValue(pointIndexNext, dim) * mu2);
	}
}
