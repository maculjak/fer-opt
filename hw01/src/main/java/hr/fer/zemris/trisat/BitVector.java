package hr.fer.zemris.trisat;

import java.util.Random;

public class BitVector {

    protected boolean[] bits;

    public BitVector(Random rand, int numberOfBits) {
        bits = new boolean[numberOfBits];
        for (int i = 0; i < numberOfBits; i++) bits [i] = rand.nextBoolean();
    }

    public BitVector(boolean ... bits) {
        this.bits = bits;
    }

    public BitVector(int n) {
        bits = new boolean[n];
    }

    public int getSize() {
        return bits.length;
    }

    public boolean get(int index) {
        return bits[index];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean bit : bits) sb.append(bit ? 1 : 0);
        return sb.toString();
    }

    public boolean allTrue() {
        for (boolean bit : bits) if(!bit) return false;
        return true;
    }
}
