package hr.fer.zemris.optjava.dz5.part1;

public interface IFunction<T> {
    double getValue(T point);
    int getNumberOfVariables();
}
