package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.dz5.part1.BitVectorSolution;
import hr.fer.zemris.optjava.dz5.part2.LocationPermutationSolution;

import java.util.ArrayList;
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

    public static LocationPermutationSolution locationPermutationMutate(LocationPermutationSolution unit) {
        Random rand = new Random();
        ArrayList<Integer> indexes = new ArrayList<>();
        unit = new LocationPermutationSolution(unit);
        int solutionSize = unit.getSolutionSize();
        for (int i = 0; i < unit.getSolutionSize(); i++) {
            indexes.add(i);
        }
        int index1 = indexes.get(rand.nextInt(solutionSize));
        indexes.remove(index1);
        int index2 = indexes.get(rand.nextInt(solutionSize - 1));

        int temp = unit.get(index1);
        unit.set(index1, unit.get(index2));
        unit.set(index2, temp);
        return unit;
    }
}
