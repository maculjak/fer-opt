package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class UniformCrossing {

    private IRNG rng;

    public UniformCrossing() {
        this.rng = RNG.getRNG();
    }

    public Solution crossover(Solution parent1, Solution parent2) {
        int[] data1 = parent1.getData();
        int[] data2 = parent2.getData();
        int[] dataChild = new int[data1.length];

        for (int i = 0; i < data1.length; i++) {
            dataChild[i] = rng.nextDouble() < 0.5 ? data1[i] : data2[i];
        }
        return new Solution(dataChild);
    }

}
