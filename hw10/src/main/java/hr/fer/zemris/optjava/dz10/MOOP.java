package hr.fer.zemris.optjava.dz10;

public class MOOP {

    public static void main(String[] args) {

        String PROBLEM = args[0];
        int POPULATION_SIZE = Integer.parseInt(args[1]);
        int MAXIMUM_ITERATIONS = Integer.parseInt(args[2]);

        if (PROBLEM.equals("1")) NSGAII.solve(new Problem1(), MAXIMUM_ITERATIONS, POPULATION_SIZE);
        else NSGAII.solve(new Problem2(), MAXIMUM_ITERATIONS, POPULATION_SIZE);
    }
}
