package hr.fer.zemris.optjava.dz3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class RegresijaSustava {

    private static final double ALPHA = 0.96;
    private static final double INITIALTEMPERATURE = 10000;
    private static final int INNERITERATIONS = 4000;
    private static final int OUTERITERATIONS = 600;
    private static final boolean MINIMIZE = true;

    public static void main (String[] args){
        double[][] x;
        double[] y;
        ArrayList<String> lines = new ArrayList<>();

        if (args.length != 2) {
            System.out.println("Invalid number of arguments. Closing the program...");
            System.exit(1);
        }

        try {
            InputStreamReader isr = new InputStreamReader(RegresijaSustava.class.getResourceAsStream(args[1]));
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                if (line.startsWith("#")) continue;

                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int i = 0;

        x = new double[lines.size()][lines.get(0).split(", ").length - 1];
        y = new double[lines.size()];

        for (String line : lines) {
            line = line.replaceAll("\\[*]*", "");
            String[] values = line.split(", ");
            for (int j = 0; j < values.length - 1; j++) x[i][j] = Double.parseDouble(values[j]);
            y[i++] = Double.parseDouble(values[values.length - 1]);
        }

        if(!args[0].equals("decimal") && !args[0].startsWith("binary")) {
            System.err.println("Invalid argument. CLosing the program...");
            System.exit(1);
        }

        Random rand = new Random();
        ErrorFunction errorFunction = new ErrorFunction(x, y);
        GeometricTempSchedule tempSchedule = new GeometricTempSchedule(ALPHA, INITIALTEMPERATURE, INNERITERATIONS, OUTERITERATIONS);


        if (args[0].equals("decimal")) {
            DoubleArrayUnifNeighbourhood neighbourhood = new DoubleArrayUnifNeighbourhood(new double[] {1, 0.2, 0.075, 0.05, 0.018, 0.2});
            PassThroughDecoder decoder= new PassThroughDecoder();
            DoubleArraySolution solution = new DoubleArraySolution(6);
            solution.randomize(rand, new double[]{-5, -5, -5, -5, -5, -5}, new double[]{5, 5, 5, 5, 5, 5});
            SimulatedAnnealing<DoubleArraySolution> simulatedAnnealing = new SimulatedAnnealing<>(decoder,
                    neighbourhood, solution, errorFunction, MINIMIZE, tempSchedule);
            simulatedAnnealing.run();
        } else if (args[0].startsWith("binary")) {
            int bitsPerVariable = Integer.parseInt(args[0].split(":")[1]);
            BinaryNeighbourhood neighbourhood = new BinaryNeighbourhood();
            GreyBinaryDecoder decoder = new GreyBinaryDecoder(-8, 8, bitsPerVariable, errorFunction.getNumberOfVariables());
            BitVectorSolution solution = new BitVectorSolution(errorFunction.getNumberOfVariables() * bitsPerVariable);
            solution.randomize(rand);
            SimulatedAnnealing<BitVectorSolution> simulatedAnnealing = new SimulatedAnnealing<>(decoder,
                    neighbourhood, solution, errorFunction, MINIMIZE, tempSchedule);
            simulatedAnnealing.run();
        }
    }
}
