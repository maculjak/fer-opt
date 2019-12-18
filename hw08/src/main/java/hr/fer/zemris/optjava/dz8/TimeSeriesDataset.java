package hr.fer.zemris.optjava.dz8;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TimeSeriesDataset implements IReadOnlyDataset {

    private double[] data;
    private double[][] inputOutput;
    private int inputVectorSize;
    public TimeSeriesDataset(String file, int inputVectorSize) {
        this.inputVectorSize = inputVectorSize;
        data = new double[1000];
        inputOutput = new double[600 - inputVectorSize][inputVectorSize + 1];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            for (int i = 0; i < 1000; i++) data[i] = Integer.parseInt(br.readLine().trim());
            OptionalDouble optMin = Arrays.stream(data).min();
            OptionalDouble optMax = Arrays.stream(data).max();

            if (optMin.isEmpty()) {
                System.err.println("No data");
                System.exit(1);
            }

            double min = optMin.getAsDouble();
            double max = optMax.getAsDouble();

            double a = 2 / (max - min);

            for (int i = 0; i < 1000; i++) data[i] = -1 + a * (data[i] - min);

        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 600 - inputVectorSize; i++) {
            double[] temp = new double[inputVectorSize + 1];
            System.arraycopy(data, i, temp, 0, inputVectorSize);
            temp[inputVectorSize] = data[i + inputVectorSize];
            inputOutput[i] = temp;
        }
    }

    public int getNumberOfSamples() {
        return inputOutput.length;
    }

    public int getInputVectorSize() {
        return inputVectorSize;
    }

    @Override
    public double[] getEntry(int index) {
        return inputOutput[index];
    }
}
