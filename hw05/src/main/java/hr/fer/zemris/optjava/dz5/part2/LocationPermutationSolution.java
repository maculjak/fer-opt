package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.IFunction;
import hr.fer.zemris.optjava.dz5.Solution;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LocationPermutationSolution extends Solution {

    private int solutionSize;
    private ArrayList<Integer> locations;

    public LocationPermutationSolution(int solutionSize) {
        this.solutionSize = solutionSize;
        this.locations = new ArrayList<>();
        for (int i = 0; i < solutionSize; i++) locations.add(i);
        Collections.shuffle(locations);
    }


    public LocationPermutationSolution(int ... permutation) {
        locations = new ArrayList<>();
        for (int i = 0; i < permutation.length; i++) locations.add(permutation[i]);
        solutionSize = permutation.length;
    }

    public LocationPermutationSolution(LocationPermutationSolution old) {
        this.solutionSize = old.solutionSize;
        this.locations = new ArrayList<>(old.locations);
    }

    public LocationPermutationSolution(ArrayList<Integer> locations) {
        this.locations = locations;
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationPermutationSolution solution = (LocationPermutationSolution) o;
        return solutionSize == solution.solutionSize &&
                Objects.equals(locations, solution.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solutionSize, locations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        locations.forEach(s -> sb.append(s).append(" "));
        return sb.toString();
    }

    public int get(int index) {
        return locations.get(index);
    }

    public int getSolutionSize() {
        return solutionSize;
    }

    public ArrayList<Integer> getLocations() {
        return locations;
    }

    public void set(int index, int value) {
        locations.set(index, value);
    }

    public boolean contains(int i) {
        return locations.contains(i);
    }
    public double setValue(IFunction<LocationPermutationSolution> function) {
        this.setValue(function.getValue(this));
        return getValue();
    }
}
