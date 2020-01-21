package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.generic.ga.jobs.Evaluate;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PEGA {

    private static RouletteWheel rouletteWheel = new RouletteWheel();
    private static UniformCrossing uniformCrossing = new UniformCrossing();
    private static double MUTATION_PROBABILITY = 0.05;

    private static IRNG rng = RNG.getRNG();
    private static Queue<Solution> unitsToEvaluate = new ConcurrentLinkedQueue<>();
    private static Queue<Solution> evaluatedUnits = new ConcurrentLinkedQueue<>();

    public static Solution solve(int POPULATION_MAXIMUM, int GENERATIONS_MAXIMUM, double FITNESS_MINIMUM,
                             int RECTANGLE_NUMBER, GrayScaleImage image) {

        List<Solution> population = new ArrayList<>();
        List<Solution> newPopulation = new ArrayList<>();
        SigmaMutation sigmaMutation = new SigmaMutation(image.getWidth(), image.getHeight());
        BreakPointCrossover breakPointCrossover = new BreakPointCrossover(1 + 5 * RECTANGLE_NUMBER, 3);

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        List<EVOThread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            EVOThread thread = new EVOThread(() -> {
                Evaluator evaluator = new Evaluator(image);

                while(true) {
                    Solution solution = unitsToEvaluate.poll();
                    if (solution != null) {
                        if (solution.getData() == null) break;
                        else {
                            evaluator.evaluate(solution);
                            evaluatedUnits.offer(solution);
                        }
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (int i = 0; i < POPULATION_MAXIMUM; i++) {
            int[] data = new int[1 + RECTANGLE_NUMBER * 5];
            for (int j = 0; j < 1 + RECTANGLE_NUMBER * 5; j++) {
                if (j == 0) data[j] = rng.nextInt(0, 256);
                else {
                    switch (j % 5) {
                        case 1:
                            data[j] = rng.nextInt(0, image.getWidth());
                            break;
                        case 2:
                            data[j] = rng.nextInt(0, image.getHeight());
                            break;
                        case 3:
                            data[j] = rng.nextInt(1, image.getWidth());
                            break;
                        case 4:
                            data[j] = rng.nextInt(1, image.getHeight());
                            break;
                        case 0:
                            data[j] = rng.nextInt(0, 256);
                            break;
                    }
                }
            }
            Solution solution = new Solution(data);
            unitsToEvaluate.offer(solution);
        }

        int i = 0;
        while (i < POPULATION_MAXIMUM) {
            Solution solution = evaluatedUnits.poll();
            if (solution != null) {
                population.add(solution);
                i++;
            }
        }

        Solution bestOverall = population.stream().max(Comparator.comparingDouble(Solution::getFitness)).get();

        for (i = 0; i < GENERATIONS_MAXIMUM && bestOverall.getFitness() < FITNESS_MINIMUM; i++) {
            for (int j = 0; j < POPULATION_MAXIMUM; j++) {
                Solution parent1 = rouletteWheel.select(population, 8);
                Solution parent2 = rouletteWheel.select(population, 8);
                Solution child = breakPointCrossover.crossover(parent1, parent2);
                child = sigmaMutation.mutate(MUTATION_PROBABILITY, child);
                unitsToEvaluate.offer(child);
            }

            population.clear();

            for (int j = 0; j < POPULATION_MAXIMUM;) {
                Solution solution = evaluatedUnits.poll();
                if (solution != null) {
                    population.add(solution);
                    j++;
                }
            }

            Solution currentBest = population.stream().max(Comparator.comparingDouble(Solution::getFitness)).get();

            if (currentBest.getFitness() > bestOverall.getFitness()) {
                bestOverall = currentBest;
            }

                population.remove(0);
                population.add(bestOverall);

            MUTATION_PROBABILITY = rng.nextDouble(0.01, 0.05);
            System.out.format("Generation: %4d Best fitness so far: %d\n", i, (int) bestOverall.getFitness());
        }


        for (i = 0; i < numberOfThreads; i++) {
            unitsToEvaluate.offer(new Solution(null));
        }
        return bestOverall;
    }
}
