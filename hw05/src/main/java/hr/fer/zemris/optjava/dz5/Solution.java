package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.dz5.part1.IFunction;

public abstract class Solution {

    private double value;

    public int compareTo(Solution s2) {
        return Double.compare(this.value, s2.getValue());
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
