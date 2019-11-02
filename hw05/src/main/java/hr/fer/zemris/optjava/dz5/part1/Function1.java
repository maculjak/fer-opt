package hr.fer.zemris.optjava.dz5.part1;

public class Function1 implements IFunction<BitVectorSolution> {

    @Override
    public double getValue(BitVectorSolution point) {
        double k = point.getNumberOfOnes();
        int n = point.getNumberOfBits();

        if (k <= 0.8 * n) return k / n;
        else if (k > 0.8 * n && k <= 0.9 * n) return 0.8;
        else return 2 * k / n - 1;
    }

    @Override
    public int getNumberOfVariables() {
        return 1;
    }
}
