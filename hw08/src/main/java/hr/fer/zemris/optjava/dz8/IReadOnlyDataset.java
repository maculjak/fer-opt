package hr.fer.zemris.optjava.dz8;

import java.util.Set;

public interface IReadOnlyDataset {

    int getNumberOfSamples();
    int getInputVectorSize();
    double[] getEntry(int index);
}
