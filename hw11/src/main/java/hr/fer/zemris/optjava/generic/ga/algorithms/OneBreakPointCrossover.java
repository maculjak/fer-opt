package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.List;

public class OneBreakPointCrossover {

    private IRNG rng;

    public OneBreakPointCrossover() {
        this.rng = RNG.getRNG();
    }


    public Solution crossover(Solution parent1, Solution parent2) {
        List<Integer> breakPoints = new ArrayList<>();
        int[] data1 = parent1.duplicate().getData();
        int[] data2 = parent2.duplicate().getData();

        int breakPointIndex = rng.nextInt(0, data1.length);
        if (data1.length - breakPointIndex >= 0) System.arraycopy(data2, breakPointIndex, data1, breakPointIndex, data1.length - breakPointIndex);

        return new Solution(data1);
    }

}
