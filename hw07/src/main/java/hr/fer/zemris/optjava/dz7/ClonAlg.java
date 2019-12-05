package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ClonAlg implements ANNTrainingAlgorithm {

    private static final double RAND_MAX = 1;
    private static final double RAND_MIN = -1;
    private static final double DELTA = 0.6;
    private int POPULATION_SIZE;
    private int D;
    private double BETA;
    private double RHO;
    private double MAXIMUM_ERROR;
    private int MAXIMUM_ITERATIONS;
    private Random rand;

    public ClonAlg(int POPULATION_SIZE, double BETA, double MAXIMUM_ERROR, int MAXIMUM_ITERATIONS, double RHO) {
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.D = D;
        this.BETA = BETA;
        this.RHO = RHO;
        this.MAXIMUM_ERROR = MAXIMUM_ERROR;
        this.MAXIMUM_ITERATIONS = MAXIMUM_ITERATIONS;
        this.rand = new Random();
    }

    @Override
    public void train(FFANN network) {
        int NUMBER_OF_WEIGHTS = network.getWeightsCount();

        double[][] population = new double[POPULATION_SIZE][NUMBER_OF_WEIGHTS + 1];
        double[] errors = new double[POPULATION_SIZE];
        double[] globalBest;

        double globalBestError;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < NUMBER_OF_WEIGHTS; j++) {
                population[i][j] = rand.nextDouble() * (RAND_MAX - RAND_MIN) + RAND_MIN;
            }
        }

        globalBest = Arrays.copyOf(population[0], population[0].length);
        globalBestError = network.getError(globalBest);

        int iteration = 0;

        do {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                population[i][NUMBER_OF_WEIGHTS] = network.getError(population[i]);
                if (population[i][NUMBER_OF_WEIGHTS] < globalBestError) {
                    globalBestError = population[i][NUMBER_OF_WEIGHTS];
                    globalBest = population[i];
                }
            }

            Arrays.sort(population, Comparator.comparingDouble(a -> a[NUMBER_OF_WEIGHTS]));

            int numberOfClones = 0;

            for (int i = 1; i <= POPULATION_SIZE; i++) numberOfClones += (int) Math.floor(BETA * POPULATION_SIZE / i);

            double[][] clones = new double[numberOfClones][NUMBER_OF_WEIGHTS + 1];

            int index = 0;
            for (int i = 0; i < POPULATION_SIZE; i++) {
                int clonesForAntiBody = (int) Math.floor(BETA * POPULATION_SIZE / (i + 1));
                double mutationProbability = Math.exp(-RHO * 1 / population[i][NUMBER_OF_WEIGHTS]);

                for (int j = 0; j < clonesForAntiBody; j++) {
                    clones[index] = Arrays.copyOf(population[i], population[i].length);

                    for (int k = 0; k < NUMBER_OF_WEIGHTS; k++) {
                        if (rand.nextDouble() < mutationProbability) {
                            clones[index][k] += rand.nextDouble() * (2 * DELTA) - DELTA;
                        }
                    }

                    clones[index][NUMBER_OF_WEIGHTS] = network.getError(clones[index]);
                    index++;
                }
            }

            int i;
            for (i = 0; i < POPULATION_SIZE - D; i++) population[i] = Arrays.copyOf(clones[i], clones[i].length);
            for (; i < POPULATION_SIZE; i++) {
                for (int j = 0; j < NUMBER_OF_WEIGHTS; j++)
                    population[i][j] = rand.nextDouble() * (RAND_MAX - RAND_MIN) + RAND_MIN;
                population[i][NUMBER_OF_WEIGHTS] = network.getError(population[i]);
            }

            System.out.printf("At iteration %4d the global best is %.3f\n", iteration + 1, globalBestError);

        } while (iteration++ < MAXIMUM_ITERATIONS || globalBestError < MAXIMUM_ERROR);

        network.setOperativeWeights(globalBest);
    }
}