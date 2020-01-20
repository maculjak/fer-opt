package hr.fer.zemris.optjava.dz8;

public interface INeuralNetwork {
    int getWeightsCount();
    void setOperativeWeights(double[] operativeWeights);
    void classify();
    void calculateOutputs(double[] inputs, double[] weights, double[] outputs);
    double getError(double[] weights);
}
