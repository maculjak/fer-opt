public class Problem1 extends MOOPProblem {

    public Problem1() {
        super(4, new double[]{-5, -5, -5, -5}, new double[]{5, 5, 5, 5}
        , new double[]{0, 0, 0, 0}, new double[]{25, 25, 25, 25});
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        for (int i = 0; i < numberOfObjectives; i++)
            objectives[i] = solution[i] * solution[i];
    }
}
