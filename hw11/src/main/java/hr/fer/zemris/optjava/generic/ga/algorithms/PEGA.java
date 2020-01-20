package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.generic.ga.jobs.Evaluate;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PEGA {

    private static RouletteWheel rouletteWheel = new RouletteWheel();
    private static SigmaMutation sigmaMutation = new SigmaMutation();
    private static OneBreakPointCrossover oneBreakPointCrossover = new OneBreakPointCrossover();

    private static IRNG rng = RNG.getRNG();
    private static Queue<Solution> unitsToEvaluate = new ConcurrentLinkedQueue<>();
    private static Queue<Solution> evaluatedUnits = new ConcurrentLinkedQueue<>();

    public static void solve(int POPULATION_MAXIMUM, int GENERATIONS_MAXIMUM, double FITNESS_MINIMUM,
                             int RECTANGLE_NUMBER, GrayScaleImage image) {

        List<Solution> population = new ArrayList<>();
        List<Solution> newPopulation = new ArrayList<>();

        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        List<EVOThread> threads = new ArrayList<>();

        for (int i = 0; i < POPULATION_MAXIMUM; i++) {
            int[] data = new int[1 + RECTANGLE_NUMBER * 5];
            for (int j = 0; j < 1 + RECTANGLE_NUMBER * 5; j++) {
                if (j == 0) data[j] = rng.nextInt(-128, 128);
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
                            data[j] = rng.nextInt(-128, 128);
                            break;
                    }
                }
            }
            Solution solution = new Solution(data);
            unitsToEvaluate.offer(solution);
            System.out.println("asdfasdf");
        }

        int i = 0;
        while (i < POPULATION_MAXIMUM) {
            Solution solution = evaluatedUnits.poll();
            if (solution != null) {
                population.add(solution);
                i++;
            }
        }

        for (i = 0; i < numberOfThreads; i++) {
            EVOThread thread = new EVOThread(() -> {
                Evaluator evaluator = new Evaluator(image);

                while(true) {
                    Solution solution = unitsToEvaluate.poll();
                    if (solution != null) {
                        if (solution.getData() == null) break;
                        else {
                            evaluator.evaluate(solution);
                            evaluatedUnits.offer(solution);
                            System.out.println(solution.getFitness());
                        }
                    }
                    assert solution != null;
                }
            });

            threads.add(thread);
            thread.start();
        }



        Solution bestSolution = population.stream().max(Comparator.comparingDouble(Solution::getFitness)).get();

    }
}
