package hr.fer.zemris.optjava.dz2;


public interface IFunction {

    int getNumberOfVariables();
    double getValue(Tuple<Double> point);
    double getGradient(Tuple<Double> point);
}
