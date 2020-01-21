package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BreakPointCrossover {

    private IRNG rng;
    private List<Integer> indices = new ArrayList<>();
    private int t;

    public BreakPointCrossover(int length, int t) {
        this.rng = RNG.getRNG();
        for (int i = 0; i < length; i++) {
            indices.add(i);
        }
        this.t = t;
    }


    public Solution crossover(Solution parent1, Solution parent2) {
        Collections.shuffle(indices);
        List<Integer> breakPoints = indices.subList(0, t);
        Collections.sort(breakPoints);
        int[] data1 = parent1.duplicate().getData();
        int[] data2 = parent2.duplicate().getData();
        int[] data = new int[data1.length];
        int startIndex = 0;

        for (int i = 0; i < t; i++) {
            int index = indices.get(i);
            if (i % 2 == 0) System.arraycopy(data1, startIndex, data, startIndex, index - startIndex);
            else System.arraycopy(data2, startIndex, data, startIndex, index - startIndex);
            startIndex = index;
        }

        return new Solution(data);
    }

}
