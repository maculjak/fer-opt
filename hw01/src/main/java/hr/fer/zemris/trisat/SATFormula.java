package hr.fer.zemris.trisat;

public class SATFormula {

    private Clause[] clauses;
    private int numberOfClauses;
    private int numberOfVariables;

    public SATFormula(int numberOfVariables, Clause[] clauses) {
        this.numberOfClauses = clauses.length;
        this.numberOfVariables = numberOfVariables;
        this.clauses = clauses;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getNumberOfClauses() {
        return numberOfClauses;
    }

    public Clause getClause(int index) {
        return clauses[index];
    }

    public boolean isSatisfied (BitVector assignment) {
        for(Clause clause : clauses) if (!clause.isSatisfied(assignment)) return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfClauses; i++) sb.append(clauses[i]);
        return sb.toString();
    }
}
