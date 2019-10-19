package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BitVectorSolution extends SingleObjectiveSolution{

    public boolean[] bits;

    public BitVectorSolution(int n) {
        this.bits = new boolean[n];
    }

    public BitVectorSolution newLikeThis() {
        return new BitVectorSolution(bits.length);
    }

    public BitVectorSolution duplicate() {
        BitVectorSolution solution = new BitVectorSolution(bits.length);
        solution.bits = this.bits.clone();
        solution.fitness = this.fitness;
        solution.value = this.value;
        return solution;
    }

    public void randomize(Random rand) {
        for (int i = 0; i < bits.length; i++) bits[i] = rand.nextBoolean();
    }

}
