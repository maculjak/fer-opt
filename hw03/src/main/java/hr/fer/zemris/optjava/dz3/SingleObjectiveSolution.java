package hr.fer.zemris.optjava.dz3;

public class SingleObjectiveSolution {

    public double fitness;
    public double value;

    public double diff(SingleObjectiveSolution solution) {
        return this.value - solution.value;
    }
}
