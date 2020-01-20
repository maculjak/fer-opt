package hr.fer.zemris.optjava.generic.ga;

public abstract class GASolution<T> implements Comparable<GASolution<T>> {

    protected T data;
    protected double fitness;

    public GASolution() {
    }

    public T getData() {
        return data;
    }

    public abstract GASolution<T> duplicate();

    @Override
    public int compareTo(GASolution<T> o) {
        return -Double.compare(this.fitness, o.fitness);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
