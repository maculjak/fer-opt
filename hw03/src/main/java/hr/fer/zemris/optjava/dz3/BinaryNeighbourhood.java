package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BinaryNeighbourhood implements INeighbourhood<BitVectorSolution>{

    private Random random;

    public BinaryNeighbourhood() {
        this.random = new Random();
    }

    @Override
    public BitVectorSolution randomNeighbour(BitVectorSolution solution) {
        int bitToFlip = random.nextInt(solution.bits.length);
        BitVectorSolution neighbour = solution.duplicate();
        neighbour.bits[bitToFlip] = !solution.bits[bitToFlip];
        return neighbour;
    }
}
