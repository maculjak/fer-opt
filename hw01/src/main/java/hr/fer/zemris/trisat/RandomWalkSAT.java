package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomWalkSAT {

    private SATFormula satFormula;
    private double randomWalkProbability;
    private final int MAX_TRIES;
    private final int MAX_FLIPS;

    public RandomWalkSAT(SATFormula satFormula, double randomWalkProbability, int MAX_TRIES, int MAX_FLIPS) {
        this.satFormula = satFormula;
        this.randomWalkProbability = randomWalkProbability;
        this.MAX_TRIES = MAX_TRIES;
        this.MAX_FLIPS = MAX_FLIPS;
    }

    public BitVector getSolution() {
        Random rand = new Random();
        SATFormulaStats satFormulaStats = new SATFormulaStats(satFormula);

        for(int k = 0; k < MAX_TRIES; k++) {
            MutableBitVector x = new MutableBitVector(rand, satFormula.getNumberOfVariables());
            for (int i = 0; i < MAX_FLIPS; i++) {
                if (satFormula.isSatisfied(x)) return x;

                Clause[] unsatisfiedClauses = new Clause[satFormula.getNumberOfClauses()];
                int unsatisfiedClauseIndex = 0;
                for (int j = 0; j < satFormula.getNumberOfClauses(); j++)
                    if (!satFormula.getClause(j).isSatisfied(x))
                        unsatisfiedClauses[unsatisfiedClauseIndex++] = satFormula.getClause(j);

                Clause randomUnsatisfiedClause = unsatisfiedClauses[rand.nextInt(unsatisfiedClauseIndex)];
                if (rand.nextDouble() <= randomWalkProbability) {
                    int index = randomUnsatisfiedClause.getRandomLiteral();
                    x.set(Math.abs(index) - 1, !x.get(Math.abs(index) - 1));
                } else {
                    int maxQuality = 0;
                    MutableBitVector best = x;
                    int[] variables = randomUnsatisfiedClause.getNonZeroLiterals();
                    for (int j = 0; j < variables.length; j++) {
                        MutableBitVector temp = new MutableBitVector(Arrays.copyOf(x.bits, x.getSize()));
                        temp.set(Math.abs(variables[j]) - 1, !temp.get(Math.abs(variables[j]) - 1));
                        satFormulaStats.setAssignment(temp, false);

                        int quality = satFormulaStats.getNumberOfSatisfiedClauses();
                        if (quality > maxQuality) {
                            maxQuality = quality;
                            best = temp;
                        }
                    }
                    x = best;
                }
            }
        }
        return null;
    }
}
