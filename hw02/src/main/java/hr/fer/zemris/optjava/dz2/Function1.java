package hr.fer.zemris.optjava.dz2;

public class Function1 implements IFunction{

    int numberOfVariables;

    public Function1() {
        numberOfVariables = 2;
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public double getValue(Tuple<Double> point) {
        double x1 = point.get(0);
        double x2 = point.get(1);

        return x1 * x1 + (x2 - 1) * (x2 - 1);
    }

    @Override
    public double getGradient(Tuple<Double> point) {
        return 0;
    }
}
