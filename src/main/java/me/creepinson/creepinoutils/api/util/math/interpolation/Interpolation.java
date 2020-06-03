package me.creepinson.creepinoutils.api.util.math.interpolation;

import me.creepinson.creepinoutils.api.util.math.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public abstract class Interpolation {

    protected LinkedHashMap<Double, Vector> points = new LinkedHashMap<>();
    protected ArrayList<Vector> pointVecs = new ArrayList<>();
    private final Class classOfT;

    public Interpolation(double[] times, Vector[] points) {
        if (points.length < 2)
            throw new IllegalArgumentException("At least two points are needed!");

        if (times.length != points.length)
            throw new IllegalArgumentException("Invalid times array!");

        classOfT = points[0].getClass();
        for (int i = 0; i < points.length; i++) {
            this.points.put(times[i], points[i]);
        }
        pointVecs = new ArrayList<>(this.points.values());
    }

    public Interpolation(Vector... points) {
        if (points.length < 2)
            throw new IllegalArgumentException("At least two points are needed!");

        classOfT = points[0].getClass();
        double time = 0;
        double stepLength = 1D / (points.length - 1);
        for (int i = 0; i < points.length; i++) {
            this.points.put(time, points[i]);
            time += stepLength;
        }
        pointVecs = new ArrayList<>(this.points.values());
    }

    protected double getValue(int index, int dim) {
        return pointVecs.get(index).getValueByDim(dim);
    }

    /**
     * 1 <= t <= 1
     **/
    public Vector valueAt(double t) {
        if (t >= 0 && t <= 1) {
            Entry<Double, Vector> firstPoint = null;
            int indexFirst = -1;
            Entry<Double, Vector> secondPoint = null;
            int indexSecond = -1;

            int i = 0;
            for (Iterator<Entry<Double, Vector>> iterator = points.entrySet().iterator(); iterator.hasNext(); ) {
                Entry<Double, Vector> entry = iterator.next();
                if (entry.getKey() >= t) {
                    if (firstPoint == null) {
                        firstPoint = entry;
                        indexFirst = i;
                    } else {
                        secondPoint = entry;
                        indexSecond = i;
                    }
                    break;
                }

                firstPoint = entry;
                indexFirst = i;

                i++;
            }

            if (secondPoint == null)
                return firstPoint.getValue().clone();

            Vector vec = firstPoint.getValue().clone();

            double pointDistance = secondPoint.getKey() - firstPoint.getKey();
            double mu = (t - firstPoint.getKey()) / pointDistance;

            for (int dim = 0; dim < 3; dim++) {
                vec.setValueByDim(dim, valueAt(mu, indexFirst, indexSecond, dim));
            }

            return vec;
        }
        return new Vector();
    }

    public abstract float valueAt(double mu, int pointIndex, int pointIndexNext, int dim);

}
