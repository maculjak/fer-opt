package hr.fer.zemris.optjava.dz3;

public class GreyBinaryDecoder extends  BitVectorDecoder{

    public GreyBinaryDecoder(double[] minimums, double[] maximums, int[] bits, int n) {
        super(minimums, maximums, bits, n);
    }

    public GreyBinaryDecoder(double minimum, double maximum, int bit, int n) {
        super(minimum, maximum, bit, n);
    }

    @Override
    public double[] decode(BitVectorSolution solution) {
        return new NaturalBinaryDecoder(minimums, maximums, bits, n).decode(greyToBinary(solution));
    }

    @Override
    public void decode(BitVectorSolution solution, double[] array) {
        array = decode(solution);
    }

    public BitVectorSolution greyToBinary(BitVectorSolution solution) {
        BitVectorSolution binary = solution.duplicate();
        int index = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 1; j < bits[i]; j++) {
                binary.bits[index + j] = solution.bits[index + j] ^ binary.bits[index + j - 1];
            }
            index += bits[i];
        }
        return binary;
    }
}
