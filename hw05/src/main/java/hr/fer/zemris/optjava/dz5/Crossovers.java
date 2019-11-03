package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.dz5.part1.BitVectorSolution;
import hr.fer.zemris.optjava.dz5.part2.LocationPermutationSolution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Crossovers {

    private static final double probability = 0.3;

    public static BitVectorSolution uniformBitVectorCrossover (BitVectorSolution parent1, BitVectorSolution parent2) {
        int numberOfBits = parent1.getNumberOfBits();
        BitVectorSolution child = new BitVectorSolution(numberOfBits);
        Random rand = new Random();

        for (int i = 0; i < numberOfBits; i++) {
            if (rand.nextBoolean()) child.setBit(i, parent1.get(i));
            else child.setBit(i, parent2.get(i));
        }

        return child;
    }

    public static LocationPermutationSolution locationPermutationCrossover (LocationPermutationSolution parent1, LocationPermutationSolution parent2) {
        Random rand = new Random();
        LocationPermutationSolution child = new LocationPermutationSolution(parent2);
        int solutionSize = parent1.getSolutionSize();
        ArrayList<Integer> altered = new ArrayList<>();
        ArrayList<Integer> newlyAssigned = new ArrayList<>();
        ArrayList<Integer> unassigned = new ArrayList<>();
        for (int i = 0; i < solutionSize; i++) {
            if (rand.nextDouble() < 0.3) {
                child.set(i, parent1.get(i));
                altered.add(i);
                newlyAssigned.add(parent1.get(i));
                unassigned.add(parent2.get(i));
            }
        }
        ArrayList<Integer> duplicatePosition = new ArrayList<>();
        for (int i = 0; i < solutionSize; i++)
            if (newlyAssigned.contains(child.get(i)) && !altered.contains(i))
                for (int j : unassigned) if (!child.contains(j)) child.set(i, j);

        return child;
    }

}
