package hr.fer.zemris.optjava.dz8;

import java.util.*;

public class ANNTrainer {

    public static void main(String[] args) {
        String FILE = args[0];
        String NET = args[1];
        int N = Integer.parseInt(args[2]);
        double MAXIMUM_ERROR = Double.parseDouble(args[3]);
        int MAXIMUM_ITERATIONS = Integer.parseInt(args[4]);

        int[] layers = Arrays.stream(NET.split("-")[1].split("x")).mapToInt(Integer::parseInt).toArray();
        ITransferFunction[] transfer = new ITransferFunction[layers.length - 1];
        Arrays.fill(transfer, new TanhTransferFunction());
        TimeSeriesDataset ds = new TimeSeriesDataset(FILE, layers[0]);

        if (NET.startsWith("tdnn")) {
            differentialEvolution(new FFANN(layers, transfer, ds), N, MAXIMUM_ITERATIONS, MAXIMUM_ERROR);
        } else if (NET.startsWith("elman")) {
            differentialEvolution(new Elman(layers, transfer, ds), N, MAXIMUM_ITERATIONS, MAXIMUM_ERROR);
        } else {
            System.err.println("Invalid algorithm. Closing the program...");
            System.exit(1);
        }

    }

    private static void differentialEvolution(INeuralNetwork network, int MAXIMUM_POPULATION, int MAXIMUM_ITERATIONS, double MAXIMUM_ERROR) {
        int weightsCount = network.getWeightsCount();
        double[][] population = new double[MAXIMUM_POPULATION][weightsCount + 1];
        List<Integer> indexes = new LinkedList<>();
        Random rand = new Random();

        double[] bestVector = new double[weightsCount + 1];
        bestVector[weightsCount] = Double.MAX_VALUE;

        for (int i = 0; i < MAXIMUM_POPULATION; i++) {
            for (int j = 0; j < weightsCount; j++) {
                population[i][j] = -1 + rand.nextDouble() * 2;
            }
            population[i][weightsCount] = network.getError(population[i]);
            if (population[i][weightsCount] < bestVector[weightsCount]) bestVector = population[i];
            indexes.add(i);
        }

        System.out.printf("Generation: %4d Smallest error: %.3f\n", 0, bestVector[weightsCount]);

        for (int i = 0; i < MAXIMUM_ITERATIONS; i++) {
            double[][] newPopulation = new double[MAXIMUM_POPULATION][weightsCount + 1];

            for (int j = 0; j < MAXIMUM_POPULATION; j++) {
                indexes.remove(Integer.valueOf(j));
                Collections.shuffle(indexes);
                indexes.add(j);

                double[] x0 = population[indexes.get(0)];
                double[] x1 = population[indexes.get(1)];
                double[] x2 = population[indexes.get(2)];
                double[] mutant = new double[weightsCount];

                for (int k = 0; k < weightsCount; k++) mutant[k] = x0[k] + rand.nextDouble() * 0.18 * (x1[k] - x2[k]);

                int jrand = rand.nextInt(weightsCount);

                for (int k = 0; k < weightsCount; k++) {
                    if (j == jrand || rand.nextDouble() < 0.7) newPopulation[j][k] = mutant[k];
                    else newPopulation[j][k] = population[j][k];
                }

                newPopulation[j][weightsCount] = network.getError(newPopulation[j]);
            }

            for (int j = 0; j < MAXIMUM_POPULATION; j++) {
                if (newPopulation[j][weightsCount] > population[j][weightsCount]) newPopulation[j] = population[j];
                if (newPopulation[j][weightsCount] < bestVector[weightsCount]) bestVector = newPopulation[j];
            }

            population = newPopulation;
            System.out.printf("Generation: %4d Smallest error so far: %.4f\n", (i + 1), bestVector[weightsCount]);
            if (bestVector[weightsCount] < MAXIMUM_ERROR) break;
        }
        network.setOperativeWeights(bestVector);
    }

}
