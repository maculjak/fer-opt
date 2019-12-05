package hr.fer.zemris.optjava.dz7;

import java.util.Set;

public interface IReadOnlyDataset {

    int getNumberOfSamples();
    double[] getOutputVector(double[] inputVector);
    Set<double[]> getInputs();
}
