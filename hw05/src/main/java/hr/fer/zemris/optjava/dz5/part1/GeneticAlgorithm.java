package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.Crossovers;
import hr.fer.zemris.optjava.dz5.Mutations;
import hr.fer.zemris.optjava.dz5.Selections;

import java.util.*;

public class GeneticAlgorithm {

    public static void main(String[] args) {

        final int POPULATION_MAXIMUM = 1000;
        final int POPULATION_MINIMUM = 2;
        final int MAXIMUM_EFFORT = 2000;
        final int ITERATIONS = 150;
        final int TOURNAMENT_PARTICIPANTS = 2;
        final double SIGMA = 0.5;
        final boolean DOUBLE_TOURNAMENT = false;

        if (args.length != 1) {
            System.err.println("Invalid number of arguments. Closing the program...");
            System.exit(1);
        }

        int numberOfBits = Integer.parseInt(args[0]);

        Set<BitVectorSolution> population = new HashSet<>();
        Function1 function = new Function1();


        for (int i = 0; i < POPULATION_MAXIMUM; i++) {
            BitVectorSolution bitVectorSolution = new BitVectorSolution(numberOfBits);
            bitVectorSolution.setValue(function);
            population.add(bitVectorSolution);
        }

        int i = 0;
        double COMPFACTOR = 0.4;
        Random rand = new Random();
        while (i < ITERATIONS && population.size() >= POPULATION_MINIMUM) {
            HashSet<BitVectorSolution> newPopulation = new HashSet<>();
            int generatedChildren = 0;

            while (generatedChildren < MAXIMUM_EFFORT ) {

                BitVectorSolution parent1;
                BitVectorSolution parent2;

                if (!DOUBLE_TOURNAMENT) {
                    parent1 = Selections.tournament(new ArrayList<>(population), TOURNAMENT_PARTICIPANTS, false);
                    parent2 = Selections.random(new ArrayList<>(population));
                } else {
                    parent1 = Selections.tournament(new ArrayList<>(population), TOURNAMENT_PARTICIPANTS, false);
                    parent2 = Selections.tournament(new ArrayList<>(population), TOURNAMENT_PARTICIPANTS, false);
                }

                double parent1Value = parent1.setValue(function);
                double parent2Value = parent2.setValue(function);

                double worseParentValue = Math.min(parent1Value, parent2Value);
                double betterParentValue = Math.max(parent1Value, parent2Value);

                double fitnessThreshold = worseParentValue + COMPFACTOR * (betterParentValue - worseParentValue);

                BitVectorSolution child = Crossovers.uniformBitVectorCrossover(parent1, parent2);
                if (rand.nextDouble() < SIGMA) Mutations.bitVectorMutate(child);

                if (child.setValue(function) > fitnessThreshold) newPopulation.add(child);

                generatedChildren++;

            }
            COMPFACTOR += 1d / ITERATIONS;
            i++;

            BitVectorSolution best = newPopulation.stream().max(BitVectorSolution::compareTo).orElse(null);
            population = newPopulation;
            System.out.format("Iteration: %3d Best unit: %s Fitness: %.3f Population size: %d\n", i, best, best == null ? 0 : best.setValue(function), population.size());
        }
    }
}
