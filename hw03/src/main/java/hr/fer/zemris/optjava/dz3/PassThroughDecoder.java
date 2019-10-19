package hr.fer.zemris.optjava.dz3;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution>{

    @Override
    public double[] decode(DoubleArraySolution solution) {
        return solution.values.clone();
    }

    @Override
    public void decode(DoubleArraySolution solution, double[] array) {
        array = decode(solution);
    }
}
