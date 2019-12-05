package hr.fer.zemris.optjava.dz7;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dataset implements IReadOnlyDataset {

    private Map<double[], double[]> inputOutputMap;

    public Dataset(String file) {
        inputOutputMap = new HashMap<double[], double[]>();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(")", "").replace("(", "");
                String[] inputOutput = line.split(":");
                double[] input = Arrays.stream(inputOutput[0].split(",")).mapToDouble(Double::parseDouble).toArray();
                double[] output = Arrays.stream(inputOutput[1].split(",")).mapToDouble(Double::parseDouble).toArray();
                inputOutputMap.put(input,output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfSamples() {
        return inputOutputMap.size();
    }

    public double[] getOutputVector(double[] input) {
        return inputOutputMap.get(input);
    }

    public Set<double[]> getInputs() {
        return inputOutputMap.keySet();
    }
}
