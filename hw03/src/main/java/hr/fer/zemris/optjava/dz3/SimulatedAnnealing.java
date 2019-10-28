package hr.fer.zemris.optjava.dz3;

import org.apache.commons.math3.linear.RealMatrix;

import java.util.Random;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<SingleObjectiveSolution> {

    private IDecoder<T> decoder;
    private INeighbourhood<T> neighbourhood;
    private T startWith;
    private IFunction function;
    private boolean minimize;
    private Random rand;
    private ITempSchedule tempSchedule;

    public SimulatedAnnealing(IDecoder<T> decoder, INeighbourhood<T> neighbourhood,
                              T startWith, IFunction function, boolean minimize,
                              ITempSchedule tempSchedule) {
        this.decoder = decoder;
        this.neighbourhood = neighbourhood;
        this.startWith = startWith;
        this.function = function;
        this.minimize = minimize;
        this.tempSchedule = tempSchedule;
        this.rand = new Random();
    }

    @Override
    public void run() {
        T solution = startWith;
        solution.value = function.getValue(decoder.decode(solution));

        double currentTemperature = tempSchedule.getInitialTemperature();
        int outerLimit = tempSchedule.getOuterLoopCounter();
        int innerLimit = tempSchedule.getInnerLoopCounter();

        for(int i = 0; i < outerLimit; i++) {
            for(int j = 0; j < innerLimit; j++) {
                System.out.format("Iteration: %d Temperature: %.3e Error: %.3e Solution: ", i, currentTemperature, solution.value);
                new SolutionDisplay<T>().printSolution(solution, decoder);
                T nextSolution = neighbourhood.randomNeighbour(solution);
                nextSolution.value = function.getValue(decoder.decode(nextSolution));
                double diff = nextSolution.diff(solution) * (minimize ? 1 : -1);
                if (diff <= 0) solution = nextSolution;
                else if (rand.nextDouble() < Math.exp(-diff / currentTemperature)) solution = nextSolution;
            }
            currentTemperature = tempSchedule.getNextTemperature();;
        }
    }
}
