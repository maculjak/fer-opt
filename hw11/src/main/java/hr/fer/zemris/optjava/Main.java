package hr.fer.zemris.optjava;


import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.generic.ga.algorithms.OneBreakPointCrossover;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Main {
    public static void main(String[] args) {

        int[] k = {1, 2, 3, 4, 5, 6,};
        int[] l = {6, 7, 8, 9, 10, 11};
        OneBreakPointCrossover crossover = new OneBreakPointCrossover();
        Solution d = crossover.crossover(new Solution(k), new Solution(l));

    }

}