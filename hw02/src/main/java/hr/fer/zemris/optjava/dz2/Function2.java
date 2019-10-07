package hr.fer.zemris.optjava.dz2;

public class Function2 implements IFunction{

    int numberOfVariables;

    public Function2() {
        this.numberOfVariables = 2;
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public double getValue(Tuple<Double> point) {
        double x1 = point.get(0);
        double x2 = point.get(1);

        return (x1 - 1) * (x1 - 1) + 10 * (x2 - 2) * (x2 - 2);
    }

    @Override
    public double getGradient(Tuple<Double> point) {
        return 0;
    }
}
