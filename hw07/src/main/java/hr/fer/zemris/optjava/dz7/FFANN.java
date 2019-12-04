package hr.fer.zemris.optjava.dz7;

import java.util.ArrayList;
import java.util.List;

public class FFANN {

    private int[] layers;
    private ITransferFunction[] transferFunctions;
    private IReadOnlyDataset dataset;
    private int weightsCount;

    public FFANN(int[] layers, ITransferFunction[] transferFunctions, IReadOnlyDataset dataset) {
        this.layers = layers;
        this.transferFunctions = transferFunctions;
        this.dataset = dataset;

        for (int i = 1; i < layers.length; i++) {
            weightsCount += layers[i];
            weightsCount += layers[i-1] * layers[i];
        }
    }

    public int getWeightsCount() {
        return weightsCount;
    }

    public void calculateOutputs(double[] inputs, double[] weights, double[] outputs) {
        List<Double> neuronOutputs = new ArrayList<>();
        for (double i : inputs) neuronOutputs.add(i);
        for (int i = 1; i < layers.length; i++) {
            for (int j = 0; j < layers[i]; j++) {

            }
        }
    }
}
