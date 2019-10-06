package hr.fer.zemris.trisat;

import java.io.*;

import java.util.ArrayList;

public class TriSATSolver {
    public static void main (String[] args) {

        int algorithm;
        String filename;

        if (args.length != 2) {
            System.err.println("Invalid number of arguments, closing the program...");
            System.exit(1);
        }

        algorithm = Integer.parseInt(args[0]);
        filename = "/" + args[1];

        ArrayList<Clause> clauses = new ArrayList<Clause>();;
        int literalsInAClause = 0;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(TriSATSolver.class.getResourceAsStream(filename)));

            while (true) {
                String line = br.readLine();
                line = line.replaceAll("( )+", " ").trim();

                if (line.equals("%")) break;
                if (line.startsWith("c")) continue;
                if (line.startsWith("p")) {
                    String[] s = line.split(" ");
                    literalsInAClause = Integer.parseInt(s[2]);
                    continue;
                }

                String[] s = line.split(" ");
                int[] indexes = new int[literalsInAClause];

                for (int i = 0; i < s.length - 1; i++) {
                    int literal = Integer.parseInt(s[i]);
                    indexes[Math.abs(literal) - 1] = literal;
                }

                clauses.add(new Clause(indexes));;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        SATFormula satFormula = new SATFormula(literalsInAClause, clauses.toArray(new Clause[literalsInAClause]));
        BitVector solution = null;
        switch (algorithm) {
            case 1:
                solution = new BruteForce(satFormula).getSolution();
                break;
            case 2:
                solution = new LocalSearch(satFormula, 100000).getSolution();
                break;
            case 3:
                solution = new SmartLocalSearch(satFormula).getSolution();
                break;
            case 4:
                solution = new MultistartLocalSearch(satFormula, 100, 10000).getSolution();
                break;
            case 5:
                solution = new RandomWalkSAT(satFormula, 0.5, 100, 10000).getSolution();
                break;
            case 6:
                solution = new IteratedLocalSearch(satFormula, 100000, 0.5).getSolution();
                break;
            default:
                System.err.println("Invalid algorithm index, closing the program...");
                System.exit(1);
        }

        if(solution != null) System.out.println("Satisfiable: " + solution);
        else System.out.println("Solution not found...");

    }
}
