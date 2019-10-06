package hr.fer.zemris.trisat;

public class SATFormulaStats {

    private SATFormula satFormula;
    private int numberOfSatisfiedClauses;
    private double[] post;
    private double percentageBonus;

    private final double percentageConstantUp = 0.01;
    private final double percentageConstantDown = 0.1;
    private final double percentageUnitAmount = 50;

    public SATFormulaStats(SATFormula satFormula) {
        this.satFormula = satFormula;
        post = new double[satFormula.getNumberOfClauses()];
    }

    public void setAssignment(BitVector assignment, boolean updatePercentages) {
        numberOfSatisfiedClauses = 0;
        percentageBonus = 0;

        for (int i = 0; i < satFormula.getNumberOfClauses(); i++) {
            if (satFormula.getClause(i).isSatisfied(assignment)) {
                numberOfSatisfiedClauses++;
                if (updatePercentages) post[i] += (1 - post[i]) * percentageConstantUp;
                else percentageBonus += percentageUnitAmount * (1 - post[i]);
            } else {
                if (updatePercentages) post[i] += (0 - post[i]) * percentageConstantDown;
                else percentageBonus -= percentageUnitAmount * (1 - post[i]);
            }
        }
        percentageBonus += numberOfSatisfiedClauses;
    }

    public int getNumberOfSatisfiedClauses() {
        return numberOfSatisfiedClauses;
    }

    public boolean isSatisfied() { return numberOfSatisfiedClauses == satFormula.getNumberOfClauses();}

    public double getPercentage(int index) { return post[index];}

    public double getPercentageBonus() {
        return percentageBonus;
    }
}
