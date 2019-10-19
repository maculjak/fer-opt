package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;

public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {

    protected double[] minimums;
    protected double[] maximums;
    protected int[] bits;
    protected int n;
    protected int totalBits;

    public BitVectorDecoder(double[] minimums, double[] maximums, int[] bits, int n) {
        this.minimums = minimums;
        this.maximums = maximums;
        this.bits = bits;
        this.n = n;
        this.totalBits = 0;
        for(int bit : bits) totalBits += bit;
    }

    public BitVectorDecoder(double minimum, double maximum, int bit, int n) {
        this.minimums = new double[n];
        this.maximums = new double[n];
        this.bits = new int[n];
        this.n = n;
        Arrays.fill(minimums, minimum);
        Arrays.fill(maximums, maximum);
        Arrays.fill(bits, bit);
        totalBits = n * bit;
    }

    public int getTotalDimensions() {
        return n;
    }

    public int getTotalBits() {
        return totalBits;
    }

    @Override
    public abstract double[] decode(BitVectorSolution solution);

    @Override
    public abstract void decode(BitVectorSolution solution, double[] array);
}
