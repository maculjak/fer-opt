package hr.fer.zemris.optjava.dz10;

public class Problem2 extends MOOPProblem{

    public Problem2() {
        super(2, 2, new double[]{0.1, 0}, new double[]{1, 5}, new double[]{0.1, 1}, new double[]{1, 60});
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        objectives[0] = solution[0];
        objectives[1] = (1 + solution[1]) / solution[0];
    }
}
