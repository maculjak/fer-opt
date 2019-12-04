public interface IReadOnlyDataset {

    int getNumberOfSamples();
    int[] getOutputVector(double[] inputVector);
}
