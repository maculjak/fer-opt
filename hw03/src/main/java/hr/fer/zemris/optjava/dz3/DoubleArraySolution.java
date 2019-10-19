package hr.fer.zemris.optjava.dz3;

import org.apache.commons.math3.linear.RealMatrix;

import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution {

    public double[] values;

    public DoubleArraySolution(int n) {
        this.values = new double[n];
    }

    public DoubleArraySolution newLikeThis() {
        return new DoubleArraySolution(values.length);
    }

    public DoubleArraySolution duplicate() {
        DoubleArraySolution solution = new DoubleArraySolution(values.length);
        solution.values = this.values.clone();
        solution.value = this.value;
        solution.fitness = this.fitness;
        return solution;
    }

    public void randomize(Random rand, double[] minValues, double[] maxValues) {
        for (int i = 0; i < values.length; i++) values[i] = minValues[i] + rand.nextDouble() * (maxValues[i] - minValues[i]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double value : values) sb.append(Math.round(value * 1000) / 1000.0).append(" ");
        return sb.toString();
    }
}
