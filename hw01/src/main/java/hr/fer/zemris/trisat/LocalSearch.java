package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Random;

public class LocalSearch implements Algorithm{

    protected SATFormula satFormula;
    protected final int MAX_NUMBER_OF_ITERATIONS;

    public LocalSearch(SATFormula satFormula, int numberOfIterations) {
        this.satFormula = satFormula;
        this.MAX_NUMBER_OF_ITERATIONS = numberOfIterations;
    }

    public MutableBitVector getSolution() {
        Random rand = new Random();
        MutableBitVector x = new MutableBitVector(rand, satFormula.getNumberOfVariables());

        for (int i = 0; i < MAX_NUMBER_OF_ITERATIONS; i++) {
            int xQuality = fit(x);

            if (xQuality == satFormula.getNumberOfClauses()) return x;

            BitVectorNGenerator bitVectorNGenerator = new BitVectorNGenerator(x);
            ArrayList<MutableBitVector> bestSubset = getBestSubset(bitVectorNGenerator.createNeighbourhood());

            int neighbourhoodQuality = fit(bestSubset.get(0));

            if (neighbourhoodQuality < xQuality) return null;
            else x = bestSubset.get(rand.nextInt(bestSubset.size()));
        }
        return null;
    }

    public ArrayList<MutableBitVector> getBestSubset(MutableBitVector[] neighbourhood) {
        int max = 0;
        ArrayList<MutableBitVector> bestSubset = new ArrayList<>();

        for (int i = 0; i < neighbourhood.length; i++) {
            int quality = fit(neighbourhood[i]);

            if (quality > max) {
                max = quality;
                bestSubset.clear();
                bestSubset.add(neighbourhood[i]);
            } else if (quality == max) bestSubset.add(neighbourhood[i]);
        }
        return bestSubset;
    }

    public int fit(MutableBitVector x) {
        int numberOfSatisfiedClauses = 0;
        for (int i = 0; i < satFormula.getNumberOfClauses(); i++) if(satFormula.getClause(i).isSatisfied(x)) numberOfSatisfiedClauses++;
        return numberOfSatisfiedClauses;
    }
}
