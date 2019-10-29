package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.Crossovers;
import hr.fer.zemris.optjava.dz4.Mutations;
import hr.fer.zemris.optjava.dz4.Selections;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    /* Best parameters for tournament:                          Best parameters for roulette
        VEL_POP = 500                                             VEL_POP = 200
        ITERATIONS = 1000                                         ITERATIONS = 1000
        N = 20                                                    SIGMA = 0.1
        SIGMA = 0.005                                              ALPHA = 0.3
        ALPHA = 0.5

     */

    public static void main(String[] args) {

        if (args.length != 5) {
            System.err.println("Invalid number of arguments!");
            System.exit(1);
        }

        final int VEL_POP = Integer.parseInt(args[0]);
        final double SATISFYING_ERROR = Double.parseDouble(args[1]);
        final int ITERATIONS = Integer.parseInt(args[2]);
        final double TOURNAMENT_PARTICIPANTS;
        final String SELECTION_CHOICE = args[3];
        final double SIGMA = Double.parseDouble(args[4]);
        final double ALPHA = 0.1;

        double[][] x;
        double[] y;
        ArrayList<String> lines = new ArrayList<>();

        try {
            InputStreamReader isr = new InputStreamReader(GeneticAlgorithm.class.getResourceAsStream("/02-zad-prijenosna.txt"));
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                if (line.startsWith("#")) continue;

                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int i = 0;

        x = new double[lines.size()][lines.get(0).split(", ").length - 1];
        y = new double[lines.size()];

        for (String line : lines) {
            line = line.replaceAll("\\[*]*", "");
            String[] values = line.split(", ");

            for (int j = 0; j < values.length - 1; j++) x[i][j] = Double.parseDouble(values[j]);

            y[i++] = Double.parseDouble(values[values.length - 1]);
        }

        ErrorFunction errorFunction = new ErrorFunction(x, y);
        Random rand = new Random();

        ArrayList<DoubleArraySolution> initialPopulation = new ArrayList<>();
        for(i = 0; i < VEL_POP; i++) {
            DoubleArraySolution chromosome = new DoubleArraySolution(errorFunction.getNumberOfVariables());

            chromosome.randomize(rand, -10, 10);
            chromosome.setValue(errorFunction.getValue(chromosome));

            initialPopulation.add(chromosome);
        }

        for(i = 0; i < ITERATIONS; i++) {
            List<DoubleArraySolution> elite = new ArrayList<>();

            ArrayList<DoubleArraySolution> newPopulation = initialPopulation.stream()
                    .sorted(Comparator.comparingDouble(DoubleArraySolution::getValue))
                    .limit(2).collect(Collectors.toCollection(ArrayList::new));

            while (newPopulation.size() < VEL_POP) {

                DoubleArraySolution parent1;
                DoubleArraySolution parent2;

                if (SELECTION_CHOICE.startsWith("tournament")) {
                    int numberOfParticipants = Integer.parseInt(SELECTION_CHOICE.split(":")[1]);
                    parent1 = Selections.tournament(initialPopulation, numberOfParticipants);
                    parent2 = Selections.tournament(initialPopulation, numberOfParticipants);
                } else if (SELECTION_CHOICE.startsWith("roulette")) {
                    parent1 = Selections.rouletteWheel(initialPopulation, 5);
                    parent2 = Selections.rouletteWheel(initialPopulation, 5);
                } else {
                    System.err.println("Invalid or unsupported selection!");
                    System.exit(1);
                    return;
                }

                DoubleArraySolution offspring1 = Crossovers.BLXAlpha(ALPHA, parent1, parent2);
                DoubleArraySolution offspring2 = Crossovers.BLXAlpha(ALPHA, parent1, parent2);

                offspring1 = Mutations.mutate(offspring1, SIGMA);
                offspring2 = Mutations.mutate(offspring2, SIGMA);

                offspring1.setValue(errorFunction.getValue(offspring1));
                offspring2.setValue(errorFunction.getValue(offspring2));

                newPopulation.add(offspring1);
                newPopulation.add(offspring2);
            }

            DoubleArraySolution bestChromosome = getBestChromosome(newPopulation);
            System.out.println("Iteration: " + (i + 1) + " Best chromosome: " + getBestChromosome(newPopulation) + " " + bestChromosome.getValue());

            initialPopulation.clear();
            initialPopulation.addAll(newPopulation);

            if (bestChromosome.getValue() <= SATISFYING_ERROR) break;
        }
    }

    private static DoubleArraySolution getBestChromosome(ArrayList<DoubleArraySolution> population) {
        DoubleArraySolution bestSolution = population.get(0);

        for (DoubleArraySolution solution : population) {
            if (solution.getFitness() > bestSolution.getFitness()) bestSolution = solution;
        }

        return bestSolution;
    }
}
