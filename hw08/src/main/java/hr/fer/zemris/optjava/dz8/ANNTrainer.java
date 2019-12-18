package hr.fer.zemris.optjava.dz8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ANNTrainer {

    public static void main(String[] args) {
        String FILE = args[0];
        String NET = args[1];
        int N = Integer.parseInt(args[2]);
        double MAXIMUM_ERROR = Double.parseDouble(args[3]);
        int MAXIMUM_ITERATIONS = Integer.parseInt(args[4]);

        TimeSeriesDataset ds = new TimeSeriesDataset(FILE, 6);

        FFANN network = new FFANN(new int[]{4, 12, 1},
                new ITransferFunction[]{
                        new TanhTransferFunction(),
                        new TanhTransferFunction(),
                        new TanhTransferFunction()},
                ds);
        System.out.println(network.getWeightsCount());
    }

    private static void differentialEvolution(FFANN network, int MAXIMUM_POPULATION, int MAXIMUM_ITERATIONS, double MAXIMUM_ERROR) {
        int weightsCount = network.getWeightsCount();
        double[][] population = new double[MAXIMUM_POPULATION][weightsCount + 1];
        ArrayList<Integer> indexes = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < MAXIMUM_POPULATION; i++) {
            for (int j = 0; j < weightsCount; j++) {
                population[i][j] = -1 + rand.nextDouble() * 2;
            }
            population[i][weightsCount] = network.getError(population[i]);
            indexes.add(i);
        }

        for (int i = 0; i < MAXIMUM_ITERATIONS; i++) {
            for (int j = 0; j < MAXIMUM_POPULATION; j++) {
                Collections.shuffle(indexes);
                indexes.remove(i);
                double[] x0 = population[indexes.get(0)];
                double[] x1 = population[indexes.get(1)];
                double[] x2 = population[indexes.get(2)];
                double[] mutant = new double[weightsCount + 1];
                for (int k = 0; k < weightsCount; k++) {
                    mutant[k] = x0[k] + 0.99 * (x1[k] - x2[k]);
                }


                indexes.add(i);
            }
        }
    }

}
