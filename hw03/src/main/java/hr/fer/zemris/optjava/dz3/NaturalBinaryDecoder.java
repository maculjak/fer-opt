package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;

public class NaturalBinaryDecoder extends BitVectorDecoder {

    public NaturalBinaryDecoder(double[] minimums, double[] maximums, int[] bits, int n) {
        super(minimums, maximums, bits, n);
    }

    public NaturalBinaryDecoder(double minimum, double maximum, int bit, int n) {
        super(minimum, maximum, bit, n);
    }

    @Override
    public double[] decode(BitVectorSolution solution) {

        double[] doubleArray = new double[n];
        boolean[] remainingBits = solution.bits;
        int index = 0;
        for (int i = 0; i < n; i++) {
            boolean[] singleVariable = Arrays.copyOf(remainingBits, bits[i]);
            remainingBits = Arrays.copyOfRange(remainingBits, bits[i], remainingBits.length + bits[i]);
            int intValue = 0;
            for(boolean bit : singleVariable) {
                intValue <<= 1;
                intValue |= bit ? 1 : 0;
            }
            doubleArray[i] = minimums[i] + intValue * (maximums[i] - minimums[i]) / (Math.pow(2, bits[i]) - 1);
        }
        return doubleArray;

    }

    @Override
    public void decode(BitVectorSolution solution, double[] array) {
        array = decode(solution);
    }
}
