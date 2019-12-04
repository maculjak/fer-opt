package hr.fer.zemris.optjava.dz7;

public interface IReadOnlyDataset {

    int getNumberOfSamples();
    int[] getOutputVector(double[] inputVector);
}
