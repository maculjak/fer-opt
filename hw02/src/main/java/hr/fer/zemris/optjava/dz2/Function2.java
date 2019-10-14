package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.util.ArrayList;

public class Function2 implements IHFunction{

    int numberOfVariables;

    public Function2() {
        this.numberOfVariables = 2;
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public double getValue(Array2DRowRealMatrix point) {
        double x1 = point.getEntry(0, 0);
        double x2 = point.getEntry(1, 0);

        return (x1 - 1) * (x1 - 1)  +  10 * (x2 - 2) * (x2 - 2);
    }

    public Array2DRowRealMatrix getGradient(Array2DRowRealMatrix point) {
        double[] partialDerivations = getPartialDerivations(point);
        return getGradient(point, partialDerivations[0], partialDerivations[1]);
    }

    private double[] getPartialDerivations(Array2DRowRealMatrix point) {
        double[] partialDerivations = new double[point.getRowDimension()];

        partialDerivations[0] = 2 * point.getEntry(0, 0) - 2;
        partialDerivations[1] = 20 * point.getEntry(1,0) - 40;

        return partialDerivations;
    }

    public Array2DRowRealMatrix getHessian(Array2DRowRealMatrix point) {
        double[][] hessian = {{2,0},{0,20}};
        return new Array2DRowRealMatrix(hessian);
    }
}
