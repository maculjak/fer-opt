package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SigmaMutation {

    private IRNG rng;

    public SigmaMutation() {
        rng = RNG.getRNG();
    }

    public Solution mutate(Solution chromosome, double s) {
        int[] data = chromosome.duplicate().getData();

        for (int i = 0; i < data.length; i++) data[i] = (int) (data[i] + rng.nextGaussian() * s);

        return new Solution(data);
    }
}
