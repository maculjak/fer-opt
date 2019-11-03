package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;

public class OffspringSelection implements Runnable {

    private ArrayList<LocationPermutationSolution> population;
    private final int ITERATIONS = 1000;
    private double successRatio = 0;

    public OffspringSelection(ArrayList<LocationPermutationSolution> population) {
        this.population = population;
    }

    public void run() {

    }
}
