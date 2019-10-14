package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static hr.fer.zemris.optjava.dz2.PointUtilities.*;

public class Sustav {

    public static void main(String args[]) {

        double[][] A;
        double[] y;
        ArrayList<String> lines = new ArrayList<>();

        if (args.length != 3) {
            System.out.println("Invalid number of arguments. Closing the program...");
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(Sustav.class.getResourceAsStream("/02-zad-sustav.txt")));

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

        A = new double[lines.size()][lines.size()];
        y = new double[lines.size()];

        int i = 0;

        for(String line : lines) {
            line = line.replaceAll("\\[*]*", "");
            String[] values = line.split(", ");

            for (int j = 0; j < values.length - 1; j++) A[i][j] = Double.parseDouble(values[j]);
            y[i++] = Double.parseDouble(values[values.length - 1]);
        }

        if (args[0].equals("grad")) {
            Function3 errorFunction = new Function3(A, y);
            ArrayList<Array2DRowRealMatrix> solutions = NumOptAlgorithms.gradientDescent(errorFunction,
                    Integer.parseInt(args[1]), generateRandomPoint(errorFunction.getNumberOfVariables(),-5, 5));
            Array2DRowRealMatrix solution = solutions.get(solutions.size() - 1);
            printPoint(solution);
            System.out.println("Error: " + errorFunction.getValue(solution));
        } else if (args[0].equals("newton")) {
            Function3 errorFunction = new Function3(A, y);
            ArrayList<Array2DRowRealMatrix> solutions = NumOptAlgorithms.newtonsMethod(errorFunction,
                    Integer.parseInt(args[1]), generateRandomPoint(errorFunction.getNumberOfVariables(), -5, 5));
            Array2DRowRealMatrix solution = solutions.get(solutions.size() - 1);
            printPoint(solution);
            System.out.println("Error: " + errorFunction.getValue(solution));
        }
    }
}
