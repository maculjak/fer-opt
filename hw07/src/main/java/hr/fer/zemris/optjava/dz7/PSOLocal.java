package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;
import java.util.Random;

public class PSOLocal implements ANNTrainingAlgorithm{

    private static final double XMIN = -1;
    private static final double XMAX = 1;
    private static final double INERTIA_MAX = 1;
    private static final double INERTIA_MIN = 0.5;
    private double VMIN;
    private double VMAX;
    private double MAXIMUM_ERROR;
    private int MAXIMUM_ITERATIONS;
    private int POPULATION_SIZE;
    private Random rand;
    private double INDIE_FACTOR;
    private double SOCIAL_FACTOR;
    private int RADIUS;

    public PSOLocal(double VMIN, double VMAX
            , double MAXIMUM_ERROR, int MAXIMUM_ITERATIONS, int POPULATION_SIZE
            , double INDIE_FACTOR, double SOCIAL_FACTOR, int RADIUS) {
        this.VMIN = VMIN;
        this.VMAX = VMAX;
        this.MAXIMUM_ERROR = MAXIMUM_ERROR;
        this.MAXIMUM_ITERATIONS = MAXIMUM_ITERATIONS;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.rand = new Random();
        this.INDIE_FACTOR = INDIE_FACTOR;
        this.SOCIAL_FACTOR = SOCIAL_FACTOR;
        this.RADIUS = RADIUS;
    }

    @Override
    public void train(FFANN network) {
        int numberOfWeights = network.getWeightsCount();

        double[][] positions = new double[POPULATION_SIZE][numberOfWeights];
        double[][] velocities = new double[POPULATION_SIZE][numberOfWeights];
        double[][] personalBestPositions = new double[POPULATION_SIZE][numberOfWeights];
        double[][] neighbourhoodBestPositions = new double[POPULATION_SIZE][numberOfWeights];

        double[] personalSmallestErrors = new double[POPULATION_SIZE];
        double[] errors = new double[POPULATION_SIZE];
        double[] neighbourhoodBestErrors = new double[POPULATION_SIZE];
        double[] globalBestPositions;

        double globalBestError;
        double inertia = INERTIA_MAX;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < numberOfWeights; j++) {
                positions[i][j] = personalBestPositions[i][j] = neighbourhoodBestPositions[i][j] = rand.nextDouble() * (XMAX - XMIN) + XMIN;
                velocities[i][j] = rand.nextDouble() * (VMAX - VMIN) + VMIN;
            }
            personalSmallestErrors[i] = network.getError(personalBestPositions[i]);
            neighbourhoodBestErrors[i] = network.getError(neighbourhoodBestPositions[i]);
        }

        globalBestPositions = Arrays.copyOf(positions[0], positions[0].length);
        globalBestError = network.getError(positions[0]);

        int iteration = 0;

        do {
            for (int i = 0; i < POPULATION_SIZE; i++) errors[i] = network.getError(positions[i]);

            for (int i = 0; i < POPULATION_SIZE; i++) {
                if (errors[i] < personalSmallestErrors[i]) {
                    personalSmallestErrors[i] = neighbourhoodBestErrors[i] = errors[i];
                    personalBestPositions[i] = Arrays.copyOf(positions[i], positions[i].length);
                    neighbourhoodBestPositions[i] = Arrays.copyOf(positions[i], positions[i].length);

                    if (errors[i] < globalBestError) {
                        globalBestError = errors[i];
                        globalBestPositions = Arrays.copyOf(positions[i], positions[i].length);
                    }
                }
            }

            for (int i = 0; i < POPULATION_SIZE; i++) {
                for (int j = -RADIUS; j <= RADIUS; j++) {
                    int index = (i + j + POPULATION_SIZE) % POPULATION_SIZE;

                    if (errors[index] < neighbourhoodBestErrors[i]) {
                        neighbourhoodBestErrors[i] = errors[index];
                        neighbourhoodBestPositions[i] = positions[index];
                    }
                }
            }

            for (int i = 0; i < POPULATION_SIZE; i++) {
                for (int j = 0; j < numberOfWeights; j++) {
                    velocities[i][j] = velocities[i][j] * inertia
                            + INDIE_FACTOR * rand.nextDouble() * (personalBestPositions[i][j] - positions[i][j])
                            + SOCIAL_FACTOR * rand.nextDouble() * (neighbourhoodBestPositions[i][j] - positions[i][j]);

                    if (velocities[i][j] > VMAX) velocities[i][j] = VMAX;
                    else if (velocities[i][j] < VMIN) velocities[i][j] = VMIN;

                    positions[i][j] += velocities[i][j];
                }
            }

            inertia -= (INERTIA_MAX - INERTIA_MIN) / MAXIMUM_ITERATIONS;
            System.out.printf("At iteration %4d the global best is %.3f\n", ++iteration, globalBestError);

        } while (iteration < MAXIMUM_ITERATIONS && !(globalBestError < MAXIMUM_ERROR));

        network.setOperativeWeights(globalBestPositions);
    }
}