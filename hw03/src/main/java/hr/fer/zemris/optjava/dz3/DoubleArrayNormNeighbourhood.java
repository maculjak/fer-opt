package hr.fer.zemris.optjava.dz3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DoubleArrayNormNeighbourhood<T> implements INeighbourhood<DoubleArraySolution>{

    private double[] deltas;
    Random rand;

    public DoubleArrayNormNeighbourhood(double[] deltas) {
        this.deltas = deltas;
        this.rand = new Random();
    }

    @Override
    public DoubleArraySolution randomNeighbour(DoubleArraySolution solution) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < deltas.length; i++) indexes.add(i);
        Collections.shuffle(indexes);
        int variablesToChange = rand.nextInt(deltas.length / 2 - 1) + 1;
        DoubleArraySolution neighbour = solution.duplicate();
        for(int i = 0; i < variablesToChange; i++) {
            int index = indexes.get(i);
            neighbour.values[index] = neighbour.values[index] + rand.nextGaussian() * deltas[index] * deltas[i];
        }
        return neighbour;
    }
}
