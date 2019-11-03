package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.IFunction;

public class QuadraticAssignmentCostFunction implements IFunction<LocationPermutationSolution> {

    private int[][] distance;
    private int[][] flow;
    private int solutionSize;

    public QuadraticAssignmentCostFunction(int solutionSize, int[][] distance, int[][] flow) {
        this.solutionSize = solutionSize;
        this.distance = distance;
        this.flow = flow;
    }

    @Override
    public double getValue(LocationPermutationSolution point) {
        double value = 0;
        for(int i = 0; i < solutionSize; i++) {
            int location1 = point.get(i);
            for (int j = 0; j < solutionSize; j++) {
                int location2 = point.get(j);
                value += distance[i][j] * flow[location1][location2];
            }
        }
        return value;
    }

    @Override
    public int getNumberOfVariables() {
        return solutionSize;
    }
}
