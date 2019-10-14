package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.awt.*;
import java.util.Random;

public class PointUtilities {

    public static void printPoint(Array2DRowRealMatrix point) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (double d : point.getColumn(0)) sb.append("x").append(i++).append(" ").append(Math.round(d*1000) / 1000d).append("\n");
        System.out.println(sb);
    }

    public static void printPoint2(Array2DRowRealMatrix point) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        char var = 'a';
        for (double d : point.getColumn(0)) sb.append(var++).append(" ").append(Math.round(d*1000) / 1000d).append("\n");
        System.out.println(sb);
    }

    public static Array2DRowRealMatrix easyCreatePoint(double... variables) {
        double[][] p = {variables};
        return (Array2DRowRealMatrix) new Array2DRowRealMatrix(p).transpose();
    }

    public static Array2DRowRealMatrix generateRandomPoint(int dimensions, double intervalLowerBound, double intervalUpperBound) {
        if(dimensions < 1) return null;

        Random random = new Random(System.currentTimeMillis());
        Array2DRowRealMatrix randomPoint = new Array2DRowRealMatrix(dimensions, 1);

        for (int i = 0; i < dimensions; i++) {
            double value = intervalLowerBound + random.nextDouble() * (intervalUpperBound - intervalLowerBound);
            randomPoint.setEntry(i, 0, value);
        }

        return randomPoint;
    }

    public static Array2DRowRealMatrix generateRandom2DPoint(double intervalLowerBound, double intervalUpperBound) {
        return generateRandomPoint(2, intervalLowerBound, intervalUpperBound);
    }
}
