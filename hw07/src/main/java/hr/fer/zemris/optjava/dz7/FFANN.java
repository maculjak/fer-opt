package hr.fer.zemris.optjava.dz7;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FFANN {

    private int[] layers;
    private ITransferFunction[] transferFunctions;
    private IReadOnlyDataset dataset;
    private int weightsCount;
    private double[] operativeWeights;

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
        List<Double> neuronInputs = new ArrayList<>();
        for (double i : inputs) neuronInputs.add(i);

        List<Double> neuronOutputs = new ArrayList<>();
        int weightIndex = 0;

        for (int i = 1; i < layers.length; i++) {
            for (int j = 0; j < layers[i]; j++) {
                double value = 0;
                for (double k : neuronInputs) value += k * weights[weightIndex++];
                value += weights[weightIndex++];
                neuronOutputs.add(value);
            }
            neuronInputs.clear();
            for (double k : neuronOutputs) neuronInputs.add(transferFunctions[i - 1].output(k));
            neuronOutputs.clear();
        }

        for (int i = 0; i < outputs.length; i++) outputs[i] = neuronInputs.get(i);
    }

    public double getError(double[] weights) {
        Set<double[]> inputs = dataset.getInputs();
        double[] networkOutput = new double[layers[layers.length - 1]];
        double error = 0;
        int numberOfSamples = dataset.getNumberOfSamples();

        for (double[] input : inputs) {
            double[] desiredOutput = dataset.getOutputVector(input);
            calculateOutputs(input, weights, networkOutput);

            for (int i = 0; i < networkOutput.length; i++)
                error += (desiredOutput[i] - networkOutput[i]) * (desiredOutput[i] - networkOutput[i]);
        }

        return error / numberOfSamples;
    }

    public void setOperativeWeights(double[] operativeWeights) {
        this.operativeWeights = operativeWeights;
    }

    public void classify() {
        Set<double[]> inputs = dataset.getInputs();
        int correctlyClassified = 0;
        int wronglyClassified = 0;

        int index = 0;
        for (double[] input : inputs) {
            double[] output = new double[layers[layers.length - 1]];

            calculateOutputs(input, operativeWeights, output);
            System.out.printf("%3d. Our result: ", ++index);

            for (int i = 0; i < output.length; i++) {
                System.out.print(Math.round(output[i]) + " ");
            }

            double[] correctOutput = dataset.getOutputVector(input);
            System.out.print("Correct result: ");

            boolean correct = true;
            for (int i = 0; i < correctOutput.length; i++) {
                System.out.print(Math.round(correctOutput[i]) + " ");
                if (Math.round(output[i]) != correctOutput[i]) correct = false;
            }

            if (correct) {
                correctlyClassified++;
                System.out.print("Correct!");
            } else {
                wronglyClassified++;
                System.out.print("Wrong!");
            }
            System.out.println();
        }

        System.out.printf("Correct: %3d Wrong: %3d Precision: %4.2f%%\n"
                , correctlyClassified, wronglyClassified, 100.0 * correctlyClassified / (wronglyClassified + correctlyClassified));
    }
}
