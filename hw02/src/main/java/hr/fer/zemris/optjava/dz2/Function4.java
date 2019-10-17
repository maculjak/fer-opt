package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Solution for the coefficients is:
 * a = 7
 * b = -3
 * c = 2
 * d = 1
 * e = 3
 * f = 3
 */

public class Function4 implements IHFunction {

    private Array2DRowRealMatrix x;
    private Array2DRowRealMatrix y;
    private int numberOfVariables;

    public Function4(double[][] x, double[] y) {
        this.x = new Array2DRowRealMatrix(x);
        this.y = new Array2DRowRealMatrix(y);
        this.numberOfVariables = 6;
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public double getValue(Array2DRowRealMatrix point) {
        double value = 0;

        for(int i = 0; i < x.getRowDimension(); i++) value += Math.pow(f(point, i) - y.getEntry(i, 0), 2);
        return value / x.getRowDimension();
    }

    private double f(Array2DRowRealMatrix point, int i) {
        double x1, x2, x3, x4, x5;
        double a, b, c, d, e, f;

        x1 = x.getEntry(i, 0);
        x2 = x.getEntry(i, 1);
        x3 = x.getEntry(i, 2);
        x4 = x.getEntry(i, 3);
        x5 = x.getEntry(i, 4);

        a = point.getEntry(0, 0);
        b = point.getEntry(1, 0);
        c = point.getEntry(2, 0);
        d = point.getEntry(3, 0);
        e = point.getEntry(4, 0);
        f = point.getEntry(5, 0);

        return a*x1 + b*x1*x1*x1*x2 + c*Math.exp(d*x3)*(1 + Math.cos(e*x4)) + f*x4*x5*x5;
    }

    @Override
    public Array2DRowRealMatrix getGradient(Array2DRowRealMatrix point) {
        double[] partialDerivations = getPartialDerivations(point);
        RealVector vector = new ArrayRealVector(partialDerivations);
        vector.unitize();
        return new Array2DRowRealMatrix(vector.toArray());
    }

    @Override
    public Array2DRowRealMatrix getHessian(Array2DRowRealMatrix point) {
        double[][] hessianMatrix = new double[point.getRowDimension()][point.getRowDimension()];
        double x1, x2, x3, x4, x5, yi;
        double a, b, c, d, e, f;
        double exp, cos, sin, part;

        a = point.getEntry(0, 0);
        b = point.getEntry(1, 0);
        c = point.getEntry(2, 0);
        d = point.getEntry(3, 0);
        e = point.getEntry(4, 0);
        f = point.getEntry(5, 0);

        for(int i = 0; i < x.getRowDimension(); i++) {
            x1 = x.getEntry(i, 0);
            x2 = x.getEntry(i, 1);
            x3 = x.getEntry(i, 2);
            x4 = x.getEntry(i, 3);
            x5 = x.getEntry(i, 4);
            yi = y.getEntry(i, 0);

            exp = Math.exp(d*x3);
            cos = 1 + Math.cos(e*x4);
            sin = -Math.sin(e*x4);
            part = b*x2 * x1*x1*x1 + a*x1 + f*x4 * x5*x5 - yi + c*exp * cos;

            hessianMatrix[0][0] += x1*x1;
            hessianMatrix[0][1] += x1*x1*x1*x1 * x2;
            hessianMatrix[0][2] += x1 * exp * cos;
            hessianMatrix[0][3] += c*x1 * x3 * exp * cos;
            hessianMatrix[0][4] += c*x1 * x4 * exp * sin;
            hessianMatrix[0][5] += x1 * x4 * x5*x5;
            hessianMatrix[1][1] += x1*x1*x1*x1*x1*x1 * x2*x2;
            hessianMatrix[1][2] += x1*x1*x1 * x2 * exp * cos;
            hessianMatrix[1][3] += c*x1*x1*x1 * x2 * x3 * exp * cos;
            hessianMatrix[1][4] += c*x1*x1*x1 * x2 * x4 * exp * sin;
            hessianMatrix[1][5] += x1*x1*x1 * x2 * x4 * x5*x5;
            hessianMatrix[2][2] += exp*exp * cos*cos;
            hessianMatrix[2][3] += c*x3 * exp*exp * cos*cos + x3 * exp * cos * part;
            hessianMatrix[2][4] += x4 * exp * sin * part + c*x4 * exp * exp * sin * cos;
            hessianMatrix[2][5] += x4 * x5*x5 * exp * cos;
            hessianMatrix[3][3] += c*c*x3*x3 * exp*exp * cos * cos + c*x3*x3 * exp * cos * part;
            hessianMatrix[3][4] += c*c*x3 * x4 * exp*exp * sin * cos + c*x3 * x4 * exp * sin * part;
            hessianMatrix[3][5] += c * x3 * x4 * x5*x5 * exp * cos;
            hessianMatrix[4][4] += c*c * x4*x4 * exp*exp * sin*sin - c*x4*x4 * exp * cos * part;
            hessianMatrix[4][5] += c * x4*x4 * x5*x5 * exp * sin;
            hessianMatrix[5][5] += x4*x4 * x5*x5*x5*x5;
        }

        for(int i = 0; i < getNumberOfVariables(); i++) for (int j = 0; j < i; j++) hessianMatrix[i][j] = hessianMatrix[j][i];
        return (Array2DRowRealMatrix) new Array2DRowRealMatrix(hessianMatrix).scalarMultiply(1.0 / x.getRowDimension());
    }

    private double[] getPartialDerivations(Array2DRowRealMatrix point) {
        double[] partialDerivations = new double[getNumberOfVariables()];
        double x1, x2, x3, x4, x5, yi;
        double c, d, e;
        c = point.getEntry(2, 0);
        d = point.getEntry(3, 0);
        e = point.getEntry(4, 0);

        for(int i = 0; i < x.getRowDimension(); i++) {
            x1 = x.getEntry(i, 0);
            x2 = x.getEntry(i, 1);
            x3 = x.getEntry(i, 2);
            x4 = x.getEntry(i, 3);
            x5 = x.getEntry(i, 4);
            yi = y.getEntry(i, 0);
            partialDerivations[0] += (f(point, i) - yi) * x1;
            partialDerivations[1] += (f(point, i) - yi) * x1*x1*x1 * x2;
            partialDerivations[2] += (f(point, i) - yi) * Math.exp(d*x3) * (1 + Math.cos(e*x4));
            partialDerivations[3] += (f(point, i) - yi) * c * x3 * Math.exp(d*x3) * (1 + Math.cos(e*x4));
            partialDerivations[4] += (f(point, i) - yi) * -c * Math.exp(d*x3) * Math.sin(e*x4) * x4;
            partialDerivations[5] += (f(point, i) - yi) * x4 * x5*x5;
        }

        return partialDerivations;
    }
}
