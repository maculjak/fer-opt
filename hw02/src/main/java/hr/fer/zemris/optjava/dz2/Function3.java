package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.util.ArrayList;

/**
 * Solution of the system is:
 * x1  = -10
 * x2  = 3
 * x3  = 15
 * x4  = 12
 * x5  = -5
 * x6  = 2
 * x7  = 6
 * x8  = 1
 * x9  = -11
 * x10 = -5
 */

public class Function3 implements IHFunction{

    private Array2DRowRealMatrix A;
    private Array2DRowRealMatrix y;
    private int numberOfVariables;

    public Function3(double[][] A, double[] y) {
        this.A = new Array2DRowRealMatrix(A);
        this.y = (Array2DRowRealMatrix) new Array2DRowRealMatrix(y);
        numberOfVariables = y.length;
    }

    @Override
    public Array2DRowRealMatrix getHessian(Array2DRowRealMatrix point) {
        return (Array2DRowRealMatrix) A.transpose().scalarMultiply(2).multiply(A);
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public double getValue(Array2DRowRealMatrix point) {
        double value = 0;

        Array2DRowRealMatrix Ax = A.multiply(point);
        Array2DRowRealMatrix res = y.subtract(Ax);

        for(int i = 0; i < numberOfVariables; i++) value += res.getEntry(i, 0) * res.getEntry(i,0);

        return value / numberOfVariables;
    }

    @Override
    public Array2DRowRealMatrix getGradient(Array2DRowRealMatrix point) {
        Array2DRowRealMatrix res = (Array2DRowRealMatrix) A.transpose().scalarMultiply(2);
        res = res.multiply(A.multiply(point).subtract(y));
        return res;
    }
}
