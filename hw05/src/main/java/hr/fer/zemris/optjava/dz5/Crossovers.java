package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.dz5.part1.BitVectorSolution;

import java.util.Random;

public class Crossovers {

    public static BitVectorSolution uniformBitVectorCrossover (BitVectorSolution parent1, BitVectorSolution parent2) {
        int numberOfBits = parent1.getNumberOfBits();
        BitVectorSolution child = new BitVectorSolution(numberOfBits);
        Random rand = new Random();

        for (int i = 0; i < numberOfBits; i++) {
            if (rand.nextBoolean()) child.setBit(i, parent1.get(i));
            else child.setBit(i, parent2.get(i));
        }

        return child;
    }
}
