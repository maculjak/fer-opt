package hr.fer.zemris.optjava.dz7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dataset implements IReadOnlyDataset{

    private Map<double[], int[]> inputOutputMap;

    public Dataset(String file) {
        inputOutputMap = new HashMap<double[], int[]>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(")", "").replace("(", "");
                String[] inputOutput = line.split(":");
                double[] input = Arrays.stream(inputOutput[0].split(",")).mapToDouble(Double::parseDouble).toArray();
                int[] output = Arrays.stream(inputOutput[1].split(",")).mapToInt(Integer::parseInt).toArray();
                inputOutputMap.put(input,output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfSamples() {
        return inputOutputMap.size();
    }

    public int[] getOutputVector(double[] input) {
        return inputOutputMap.get(input);
    }
}
