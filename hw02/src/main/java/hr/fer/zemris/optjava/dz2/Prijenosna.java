package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static hr.fer.zemris.optjava.dz2.PointUtilities.*;
import static hr.fer.zemris.optjava.dz2.PointUtilities.printPoint;

public class Prijenosna {

    public static void main(String[] args) {
        double[][] x;
        double[] y;
        ArrayList<String> lines = new ArrayList<>();

        if(args.length != 3) {
            System.out.println("Invalid number of arguments. Closing the program...");
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Prijenosna.class.getResourceAsStream("/02-zad-prijenosna.txt")));
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

        if (args[0].equals("grad")) {
            Function4 errorFunction = new Function4(x, y);
            ArrayList<Array2DRowRealMatrix> solutions = NumOptAlgorithms.gradientDescent(errorFunction,
                    Integer.parseInt(args[1]), generateRandomPoint(errorFunction.getNumberOfVariables(),-5, 5));
            Array2DRowRealMatrix solution = solutions.get(solutions.size() - 1);
            printPoint2(solution);
            System.out.println("Error: " + errorFunction.getValue(solution));
        } else if (args[0].equals("newton")) {
            Function4 errorFunction = new Function4(x, y);
            ArrayList<Array2DRowRealMatrix> solutions = NumOptAlgorithms.newtonsMethod(errorFunction,
                    Integer.parseInt(args[1]), generateRandomPoint(errorFunction.getNumberOfVariables(), -5, 5));
            Array2DRowRealMatrix solution = solutions.get(solutions.size() - 1);
            printPoint2(solution);
            System.out.println("Error: " + errorFunction.getValue(solution));
        }

    }
}
