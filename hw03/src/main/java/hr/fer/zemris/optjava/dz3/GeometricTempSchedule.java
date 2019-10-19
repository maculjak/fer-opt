package hr.fer.zemris.optjava.dz3;

public class GeometricTempSchedule implements ITempSchedule {

    private double alpha;
    private double tinitial;
    private double tCurrent;
    private int innerLimit;
    private int outerLimit;

    public GeometricTempSchedule(double alpha, double tinitial, int innerLimit, int outerLimit) {
        this.alpha = alpha;
        this.tinitial = tinitial;
        this.innerLimit = innerLimit;
        this.outerLimit = outerLimit;
        tCurrent = tinitial;
    }

    @Override
    public double getNextTemperature() {
        return (tCurrent *= alpha);
    }

    @Override
    public int getInnerLoopCounter() {
        return innerLimit;
    }

    @Override
    public int getOuterLoopCounter() {
        return outerLimit;
    }

    @Override
    public double getInitialTemperature() {
        return tinitial;
    }
}
