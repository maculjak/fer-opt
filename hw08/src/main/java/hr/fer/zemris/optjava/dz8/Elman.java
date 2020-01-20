package hr.fer.zemris.optjava.dz8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Elman extends FFANN{

    private double[] contextNeurons;

    public Elman(int[] layers, ITransferFunction[] transferFunctions, IReadOnlyDataset dataset) {
        super(layers, transferFunctions, dataset);

        weightsCount += layers[1] * layers[1];
        contextNeurons = new double[layers[1]];
    }


    public int getWeightsCount() {
        return weightsCount + contextNeurons.length;
    }

    @Override
    public void calculateOutputs(double[] inputs, double[] weights, double[] outputs) {
        List<Double> neuronInputs = new ArrayList<>();
        for (double i : inputs) neuronInputs.add(i);

        List<Double> neuronOutputs = new ArrayList<>();
        int weightIndex = 0;
        int contextWeightIndex = weightsCount - layers[1] * layers[1] - 1;


        for (int i = 1; i < layers.length; i++) {
            for (int j = 0; j < layers[i]; j++) {
                double value = 0;
                for (double k : neuronInputs) value += k * weights[weightIndex++];
                if (i == 1) for (int k = 0; k < layers[1]; k++) value += contextNeurons[k] * weights[contextWeightIndex++];

                value += weights[weightIndex++];
                neuronOutputs.add(value);
            }
            neuronInputs.clear();
            for (double k : neuronOutputs) neuronInputs.add(transferFunctions[i - 1].output(k));
            if (i == 1) for (int k = 0; k < neuronInputs.size(); k++) contextNeurons[k] = neuronInputs.get(k);
            neuronOutputs.clear();
        }

        for (int i = 0; i < outputs.length; i++) outputs[i] = neuronInputs.get(i);
    }

    public double getError(double[] weights) {
        int size = dataset.getNumberOfSamples();
        int vectorSize = dataset.getInputVectorSize();

        double[] networkOutput = new double[layers[layers.length - 1]];
        double error = 0;
        int numberOfSamples = dataset.getNumberOfSamples();
        contextNeurons = Arrays.copyOfRange(weights, weightsCount - layers[1] * (layers[1] + 1)
                , weightsCount - layers[1] * layers[1]);

        for (int i = 0; i < size; i++) {
            double[] entry = dataset.getEntry(i);
            double[] desiredOutput = Arrays.copyOfRange(entry, vectorSize, entry.length);
            double[] input = Arrays.copyOfRange(entry, 0, vectorSize);
            calculateOutputs(input, weights, networkOutput);

            for (int j = 0; j < networkOutput.length; j++) {
                error += (desiredOutput[j] - networkOutput[j]) * (desiredOutput[j] - networkOutput[j]);
            }
        }

        return error / numberOfSamples;
    }
}
