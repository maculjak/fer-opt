package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.dz5.part1.BitVectorSolution;

import java.util.Random;

public class Mutations {

    public static BitVectorSolution bitVectorMutate(BitVectorSolution unit) {
        int size = unit.getNumberOfBits();
        BitVectorSolution result = new BitVectorSolution(size);
        Random rand = new Random();
        int bitToFlip = rand.nextInt(size);
        for (int i = 0; i < size; i++)
            result.setBit(i, Boolean.logicalXor(i == bitToFlip, unit.get(i)));
        return result;
    }
}
