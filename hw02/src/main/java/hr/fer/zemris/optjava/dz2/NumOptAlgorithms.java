package hr.fer.zemris.optjava.dz2;

import com.sun.source.tree.IfTree;
import org.apache.commons.math3.linear.*;

import java.util.ArrayList;
import java.util.Random;

import static hr.fer.zemris.optjava.dz2.PointUtilities.generateRandom2DPoint;
import static hr.fer.zemris.optjava.dz2.PointUtilities.generateRandomPoint;

public class NumOptAlgorithms {

    public static ArrayList<Array2DRowRealMatrix> gradientDescent(IFunction function, int numberOfIterations, Array2DRowRealMatrix solution){

        ArrayList<Array2DRowRealMatrix> solutions = new ArrayList<>();
        double lambda;

        for (int i = 0; i < numberOfIterations; i++) {
            solutions.add(solution);
            Array2DRowRealMatrix gradient = function.getGradient(solution);
            lambda = getLambda(function, solution, (Array2DRowRealMatrix) gradient.scalarMultiply(-1));
            if (isOptimum(function, solution)) break;
            solution = (Array2DRowRealMatrix) solution.subtract(gradient.scalarMultiply(lambda));
        }


        return solutions;
    }

    public static  ArrayList<Array2DRowRealMatrix> gradientDescent(IFunction function, int numberOfIterations, double intervalLowerBound, double intervalUpperBound) {
        return gradientDescent(function, numberOfIterations, generateRandomPoint(function.getNumberOfVariables(), intervalLowerBound, intervalUpperBound));
    }

    public static ArrayList<Array2DRowRealMatrix> newtonsMethod(IHFunction function, int numberOfIterations, Array2DRowRealMatrix solution) {
        double lambda;
        ArrayList<Array2DRowRealMatrix> solutions = new ArrayList<>();

        for(int i = 0; i < numberOfIterations; i++) {
            solutions.add(solution);

            Array2DRowRealMatrix hessian = function.getHessian(solution);
            Array2DRowRealMatrix gradient = function.getGradient(solution);
            Array2DRowRealMatrix d = new Array2DRowRealMatrix(MatrixUtils.inverse(hessian).multiply(gradient).scalarMultiply(-1).getColumn(0));

            lambda = getLambda(function, solution, d);
            if (isOptimum(function, solution)) break;
            solution = (Array2DRowRealMatrix) solution.add(d.scalarMultiply(lambda));
        }
        return solutions;
    }

    public static ArrayList<Array2DRowRealMatrix> newtonsMethod (IHFunction function, int numberOfIterations, double intervalLowerBound, double intervalUpperBound) {
        return newtonsMethod(function, numberOfIterations, generateRandomPoint(function.getNumberOfVariables(), intervalLowerBound, intervalUpperBound));
    }


    public static double getLambda(IFunction function, Array2DRowRealMatrix x, Array2DRowRealMatrix d) {
        double lambdaL = 0;
        double lambdaU = getLambdaUpper(function, x, d);
        double lambda = lambdaU;
        double derivation;

        for(int i = 0; i < 256; i++) {
            lambda = (lambdaL + lambdaU) / 2;
            derivation = function.getGradient((Array2DRowRealMatrix) x.add(d.scalarMultiply(lambda))).transpose().multiply(d).getEntry(0,0);
            if (Math.abs(derivation) < 0.1) break;
            if (derivation > 0) lambdaU = lambda;
            else if (derivation < 0) lambdaL = lambda;
        }

        return lambda;
    }

    private static double getLambdaUpper(IFunction function, Array2DRowRealMatrix x, Array2DRowRealMatrix d) {
        double lambdaUpper = 1;

        double derivation;
        while(true) {
            x = (Array2DRowRealMatrix) x.add(d.scalarMultiply(lambdaUpper));
            derivation = function.getGradient(x).transpose().multiply(d).getEntry(0,0);
            if  (derivation >= 0) break;
            else if (derivation < 0) lambdaUpper *= 2;
        }

        return lambdaUpper;
    }

    private static boolean isOptimum(IFunction function, Array2DRowRealMatrix x) {
        Array2DRowRealMatrix gradient = function.getGradient(x);
        for(double g : gradient.getColumn(0)) if (Math.abs(g) > 0.1) return false;
        return true;
    }

    private static double gradientAbsValue(Array2DRowRealMatrix gradient) {
        double sum = 0;
        for(int i = 0; i < gradient.getRowDimension(); i++) {
            sum += Math.pow(gradient.getEntry(i,0), 2);
        }
        return Math.sqrt(sum);
    }
}
