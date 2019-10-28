package hr.fer.zemris.optjava.dz4;

import hr.fer.zemris.optjava.dz4.part1.DoubleArraySolution;

public interface IFunction {
    double getValue(DoubleArraySolution point);
    int getNumberOfVariables();
}
