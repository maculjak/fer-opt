package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SigmaMutation {

    private IRNG rng;
    private int imageHeight;
    private int imageWidth;

    public SigmaMutation(int imageHeight, int imageWidth) {
        rng = RNG.getRNG();
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }

    public Solution mutate(double probability, Solution chromosome) {
        int[] data = chromosome.duplicate().getData();

        for (int i = 0; i < data.length; i++) {
            if(rng.nextDouble() < probability) {
                if (i == 0) data[i] = rng.nextInt(0, 256);
                else if (i % 5 == 0)  data[i] = rng.nextInt(0, imageWidth);
                else if (i % 5 == 1)  data[i] = rng.nextInt(0, imageHeight);
                else if (i % 5 == 2)  data[i] = rng.nextInt(1, imageWidth);
                else if (i % 5 == 3)  data[i] = rng.nextInt(1, imageHeight);
                else data[i] = rng.nextInt(0, 256);
            }
        }

        return new Solution(data);
    }

}
