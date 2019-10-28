package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Random;

public class DoubleArraySolution {

    private ArrayList<Double> values;
    private double value;
    private double fitness;
    private int capacity;

    public DoubleArraySolution(int n) {
        this.values = new ArrayList<>();
        this.capacity = n;
        for(int i = 0; i < n; i++) values.add(0.0);
    }

    public DoubleArraySolution newLikeThis() {
        return new DoubleArraySolution(values.size());
    }

    public DoubleArraySolution duplicate() {
        DoubleArraySolution solution = new DoubleArraySolution(values.size());
        solution.values.addAll(values);
        solution.value = this.value;
        solution.fitness = this.fitness;
        return solution;
    }

    public void randomize(Random rand, double[] minValues, double[] maxValues) {
        for (int i = 0; i < values.size(); i++) {
            values.set(i, minValues[i] + rand.nextDouble() * (maxValues[i] - minValues[i]));
        }
    }

    public void randomize(Random rand, double minValue, double maxValue) {
        for (int i = 0; i < capacity; i++) {
            values.set(i, minValue + rand.nextDouble() * (maxValue - minValue));
        }
    }

    public double getValue() {
        return value;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double value : values) sb.append(String.format("%.3f", value)).append(" ");
        return sb.toString();
    }

    public int size() { return values.size(); }

    public double get(int index) {
        return values.get(index);
    }

    public double set(int index, double value) { return values.set(index, value); }

    public void setValue(double value) {
        this.value = value;
        this.fitness = -value;
    }
}
