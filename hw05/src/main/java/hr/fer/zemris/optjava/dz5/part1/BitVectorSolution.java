package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.Solution;

import java.util.BitSet;
import java.util.Objects;
import java.util.Random;

public class BitVectorSolution extends Solution {

    private BitSet representation;
    private int numberOfOnes;
    private int numberOfBits;


    public BitVectorSolution(int numberOfBits) {
        Random rand = new Random();
        representation = new BitSet(numberOfBits);
        this.numberOfBits = numberOfBits;
        numberOfOnes = 0;
        for (int i = 0; i < numberOfBits; i++) {
            representation.set(i, rand.nextBoolean());
            if (representation.get(i)) numberOfOnes++;
        }
    }

    public BitVectorSolution(BitVectorSolution old) {
        this.representation = new BitSet(old.getNumberOfBits());
    }

    public double setValue(IFunction<BitVectorSolution> function) {
        this.setValue(function.getValue(this));
        return getValue();
    }

    @Override
    public int compareTo(Solution s2) {
        return 0;
    }

    public BitSet getRepresentation() {
        return representation;
    }

    public int getNumberOfOnes() {
        return numberOfOnes;
    }

    public int getNumberOfBits() {
        return numberOfBits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitVectorSolution that = (BitVectorSolution) o;
        return Objects.equals(representation, that.representation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(representation);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfBits; i++) sb.append(representation.get(i) ? 1 : 0);
        return sb.toString();
    }

    public void setBit(int index, boolean value) {
        boolean valueAtIndex = representation.get(index);
        this.representation.set(index, value);
        if (valueAtIndex && !value) numberOfOnes--;
        else if (!valueAtIndex && value) numberOfOnes++;
    }

    public boolean get(int index) {
        return representation.get(index);
    }

    public void flip(int index) {
        this.representation.flip(index);
    }
}
