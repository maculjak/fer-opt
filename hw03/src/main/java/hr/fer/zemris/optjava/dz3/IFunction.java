package hr.fer.zemris.optjava.dz3;


import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public interface IFunction {
    double getValue(double[] point);
    int getNumberOfVariables();
}
