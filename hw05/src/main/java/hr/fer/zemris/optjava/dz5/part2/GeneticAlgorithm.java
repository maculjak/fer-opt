package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.Crossovers;
import hr.fer.zemris.optjava.dz5.IFunction;
import hr.fer.zemris.optjava.dz5.Mutations;
import hr.fer.zemris.optjava.dz5.Selections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    public static void main(String[] args) {

        if (args.length != 3) {
            System.err.println("Invalid number of arguments! Closing the program...");
            System.exit(1);
        }

        int solutionSize;
        int[][] distances;
        int[][] flow;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(GeneticAlgorithm.class.getResourceAsStream(args[0])));
            solutionSize = Integer.parseInt(br.readLine().trim());
            distances = new int[solutionSize][solutionSize];
            flow = new int[solutionSize][solutionSize];
            br.readLine();
            for (int i = 0; i < solutionSize; i++) {
                distances[i] = Arrays.stream(br.readLine().trim().replace("  ", " ").split(" "))
                        .filter(s -> !s.equals(""))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            br.readLine();

            for (int i = 0; i < solutionSize; i++) {
                flow[i] = Arrays.stream(br.readLine().trim().replace("  ", " ").split(" "))
                        .filter(s -> !s.equals(""))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return;
        }

        final int POPULATION_MAXIMUM = Integer.parseInt(args[1]);
        int numberOfSubPopulations = Integer.parseInt(args[2]);

        ArrayList<LocationPermutationSolution> population = new ArrayList<>(POPULATION_MAXIMUM);
        QuadraticAssignmentCostFunction function = new QuadraticAssignmentCostFunction(solutionSize, distances, flow);

        for (int i = 0; i < POPULATION_MAXIMUM; i++) {
            LocationPermutationSolution solution = new LocationPermutationSolution(solutionSize);
            solution.setValue(function.getValue(solution));
            population.add(solution);
        }


        while (numberOfSubPopulations > 1) {
            ArrayList<ArrayList<LocationPermutationSolution>> populations = divideIntoSubPopulations(population, numberOfSubPopulations);
            population.clear();
            for (ArrayList<LocationPermutationSolution> p : populations) {
                population.addAll(offspringSelection(p, function));
            }
            numberOfSubPopulations--;
            LocationPermutationSolution best = population.stream().min(LocationPermutationSolution::compareTo).orElse(null);
            System.out.println(best + " " + best.getValue());
        }

//        LocationPermutationSolution
    }

    private static ArrayList<ArrayList<LocationPermutationSolution>> divideIntoSubPopulations(ArrayList<LocationPermutationSolution> population, int n) {
        int quant = (int) Math.ceil(1.0 * population.size() / n);
        ArrayList<ArrayList<LocationPermutationSolution>> populations = new ArrayList<>();
        for (int i = 0; i < population.size(); i += quant) {
            populations.add(new ArrayList<>(population.subList(i, Math.min(i + quant, population.size()))));
        }

        return populations;
    }

    private static ArrayList<LocationPermutationSolution> offspringSelection(ArrayList<LocationPermutationSolution> population, IFunction<LocationPermutationSolution> function) {
        final int ITERATIONS = 1000;
        int populationSize = population.size();
        final double SUCCESS_RATIO = 0.9;
        final int MAXIMUM_SELECTION_PRESSURE = 30;
        final double COMPARISON_FACTOR_LOWER_BOUND = 0.3;
        final double COMPARISON_FACTOR_UPPER_BOUND = 1;
        double comparisonFactor = COMPARISON_FACTOR_LOWER_BOUND;
        double actualSelectionPressure = 1;
        Random rand = new Random();

        for (int i = 0; i < ITERATIONS && actualSelectionPressure < MAXIMUM_SELECTION_PRESSURE; i++) {
            ArrayList<LocationPermutationSolution> newPopulation = new ArrayList<>();
            ArrayList<LocationPermutationSolution> pool = new ArrayList<>();

            while (newPopulation.size() < populationSize * SUCCESS_RATIO
                    &&  newPopulation.size() + pool.size() < populationSize * MAXIMUM_SELECTION_PRESSURE) {
                LocationPermutationSolution parent1 = Selections.tournament(population, 2, true);
                LocationPermutationSolution parent2 = Selections.tournament(population, 2, true);

                double parent1Value = parent1.setValue(function);
                double parent2Value = parent2.setValue(function);

                double worseParentValue = Math.min(parent1Value, parent2Value);
                double betterParentValue = Math.max(parent1Value, parent2Value);

                double fitnessThreshold = worseParentValue + comparisonFactor * (worseParentValue - betterParentValue);

                LocationPermutationSolution child = Crossovers.locationPermutationCrossover(parent1, parent2);
                if (rand.nextDouble() < 0.2) child = Mutations.locationPermutationMutate(child);

                child.setValue(function.getValue(child));
                if (child.getValue() >= fitnessThreshold) {
                    int j;
                    for (j = 0; j < pool.size() && child.getValue() < pool.get(j).getValue(); j++);
                    pool.add(j, child);
                } else {
                    newPopulation.add(child);
                }
            }

            actualSelectionPressure = (1.0 * newPopulation.size() + pool.size()) / population.size();
            for (LocationPermutationSolution solution : pool) {
                if (newPopulation.size() >= populationSize) break;
                newPopulation.add(solution);
            }
            comparisonFactor += (COMPARISON_FACTOR_UPPER_BOUND - COMPARISON_FACTOR_LOWER_BOUND) / (i + 1);
            population = newPopulation;
        }
        return population;
    }


}
