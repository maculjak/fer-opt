package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



public class PMGA {

    private static Queue<Integer> tasksToComplete = new ConcurrentLinkedQueue<>();
    private static Queue<Solution> createdUnits = new ConcurrentLinkedQueue<>();
    private static IRNG rng = RNG.getRNG();


    public static Solution solve(int POPULATION_MAXIMUM, int GENERATIONS_MAXIMUM, double FITNESS_MINIMUM, int RECTANGLE_NUMBER, GrayScaleImage image) throws InterruptedException {

        List<Solution> population = new ArrayList<>();
        double MUTATION_PROBABILITY = 0.02;
        int rouletteParticipants = 8;

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        final int CHILDREN_PER_TASK = POPULATION_MAXIMUM / numberOfThreads;


        for (int i = 0; i < numberOfThreads; i++) {
            EVOThread thread = new EVOThread(() -> {
                Evaluator evaluator = new Evaluator(image);
                BreakPointCrossover breakPointCrossover = new BreakPointCrossover(1 + 5 * RECTANGLE_NUMBER, 3);
                UniformMutation uniformMutation = new UniformMutation(image.getWidth(), image.getHeight());
                RouletteWheel rouletteWheel = new RouletteWheel();

                while(true) {
                    Integer childrenToCreate = tasksToComplete.poll();
                    if (childrenToCreate != null) {
                        if (childrenToCreate == -1) break;
                        else if (childrenToCreate < 0) {
                            for (int j = 0; j < -childrenToCreate; j++) {
                                int[] data = new int[1 + RECTANGLE_NUMBER * 5];
                                for (int k = 0; k < 1 + RECTANGLE_NUMBER * 5; k++) {
                                    if (k == 0) data[k] = rng.nextInt(-128, 128);
                                    else {
                                        if (k % 5 == 1) data[k] = rng.nextInt(0, 256);
                                        else if (k % 5 == 2) data[k] = rng.nextInt(0, 256);
                                        else if (k % 5 == 3) data[k] = rng.nextInt(1, 256);
                                        else if (k % 5 == 4) data[k] = rng.nextInt(1, 256);
                                        else data[k] = rng.nextInt(0, 256);
                                    }
                                }
                                Solution solution = new Solution(data);
                                evaluator.evaluate(solution);
                                createdUnits.offer(solution);
                            }
                        }
                        else {
                            for (int j = 0; j < childrenToCreate; j++) {
                                Solution parent1 = rouletteWheel.select(population, rouletteParticipants);
                                Solution parent2 = rouletteWheel.select(population, rouletteParticipants);
                                while(parent1.equals(parent2)) parent2 = rouletteWheel.select(population, rouletteParticipants);

                                Solution child = breakPointCrossover.crossover(parent1, parent2);
                                child = uniformMutation.mutate(MUTATION_PROBABILITY, child);
                                evaluator.evaluate(child);

                                createdUnits.offer(child);
                            }
                        }
                    }
                }
            });

            thread.start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            tasksToComplete.offer(-CHILDREN_PER_TASK);
        }

        if (POPULATION_MAXIMUM % numberOfThreads != 0) tasksToComplete.offer(-(POPULATION_MAXIMUM % numberOfThreads));

        int i = 0;
        while (i < POPULATION_MAXIMUM) {
            Solution solution = createdUnits.poll();
            if (solution != null) {
                population.add(solution);
                i++;
            }
        }

        Solution bestOverall = population.stream().max(Comparator.comparingDouble(Solution::getFitness)).get();

        List<Solution> newPopulation = new ArrayList<>();

        for (i = 0; i < GENERATIONS_MAXIMUM && bestOverall.getFitness() < FITNESS_MINIMUM; i++) {
            for (int j = 0; j < numberOfThreads; j++) {
                tasksToComplete.offer(CHILDREN_PER_TASK);
            }
            if (POPULATION_MAXIMUM % numberOfThreads != 0) tasksToComplete.offer(POPULATION_MAXIMUM % numberOfThreads);


            for (int j = 0; j < POPULATION_MAXIMUM;) {
                Solution solution = createdUnits.poll();
                if (solution != null) {
                    newPopulation.add(solution);
                    j++;
                }
            }


            Solution currentBest = newPopulation.stream().max(Comparator.comparingDouble(Solution::getFitness)).get();
            if (currentBest.getFitness() > bestOverall.getFitness()) bestOverall = currentBest;

            System.out.format("Generation: %4d Best fitness so far: %d\n", i, (int) bestOverall.getFitness());


            population.clear();
            population.addAll(newPopulation);
            population.remove(0);
            population.add(bestOverall);
            newPopulation.clear();

        }

        for (i = 0; i < numberOfThreads; i++) {
            tasksToComplete.offer(-1);
        }


        return bestOverall;

    }
}