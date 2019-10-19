package hr.fer.zemris.optjava.dz3;

public class SolutionDisplay<T extends SingleObjectiveSolution> {

    public void printSolution(T solution, IDecoder<T> decoder) {
        double[] values = decoder.decode(solution);
        for(double value : values) System.out.print(Math.round(value * 1000) / 1000.0 + " ");
        System.out.println();
    }
}
