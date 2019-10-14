package hr.fer.zemris.optjava.dz2;


import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public interface IFunction {

    int getNumberOfVariables();
    double getValue(Array2DRowRealMatrix point);
    default Array2DRowRealMatrix getGradient(Array2DRowRealMatrix point, double ... partialDerivations) {
        Array2DRowRealMatrix gradient = new Array2DRowRealMatrix(getNumberOfVariables(), 1);
        for(int i = 0; i < partialDerivations.length; i++) gradient.setEntry(i, 0, partialDerivations[i]);
        return gradient;
    }
    Array2DRowRealMatrix getGradient(Array2DRowRealMatrix point);

}
