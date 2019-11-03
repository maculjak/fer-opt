package hr.fer.zemris.optjava.dz5;

public interface IFunction<T> {
    double getValue(T point);
    int getNumberOfVariables();
}
