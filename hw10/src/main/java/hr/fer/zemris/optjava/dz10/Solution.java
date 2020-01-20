package hr.fer.zemris.optjava.dz10;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class Solution implements Comparable<Solution>{

    private double[] arguments;
    private double[] values;
    private int rank;
    private double crowdingDistance;

    public Solution(double[] arguments, double[] values) {
        this.arguments = arguments;
        this.values = values;
    }

    public static Comparator<Solution> featureComparator(int feature) {
        return new Comparator<Solution>() {
            @Override
            public int compare(Solution o1, Solution o2) {
                return Double.compare(o1.values[feature], o2.values[feature]);
            }
        };
    }

    @Override
    public int compareTo(@NotNull Solution o) {
        if (o.rank == this.rank) return Double.compare(this.crowdingDistance, o.crowdingDistance);
        else return -Integer.compare(this.rank, o.rank);
    }

    public void setArguments(double[] arguments) {
        this.arguments = arguments;
    }

    public double[] getArgumetns() {
        return arguments;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public void addToCrowdingDistance(double value) {
        this.crowdingDistance += value;
    }
}
