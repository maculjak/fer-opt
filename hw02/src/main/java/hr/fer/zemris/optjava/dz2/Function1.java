package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class Function1 implements IHFunction{

    int numberOfVariables;

    public Function1() {
        numberOfVariables = 2;
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public double getValue(Array2DRowRealMatrix point) {
        double x1 = point.getEntry(0,0);
        double x2 = point.getEntry(1,0);

        return x1 * x1 + (x2 - 1) * (x2 - 1);
    }

    public Array2DRowRealMatrix getGradient(Array2DRowRealMatrix point) {
        double[] partialDerivations = getPartialDerivations(point);
        return getGradient(point, partialDerivations[0], partialDerivations[1]);
    }

    @Override
    public Array2DRowRealMatrix getHessian(Array2DRowRealMatrix point) {
        double[][] hessian = {{2,0},{0,2}};
        return new Array2DRowRealMatrix(hessian);
    }

    private double[] getPartialDerivations(Array2DRowRealMatrix point) {
        double[] partialDerivations = new double[point.getRowDimension()];

        partialDerivations[0] = 2 * point.getEntry(0, 0);
        partialDerivations[1] = 2 * point.getEntry(1, 0) - 2;

        return partialDerivations;
    }
}
