package hr.fer.zemris.trisat;

import java.util.*;

public class SmartLocalSearch implements Algorithm{

    SATFormula satFormula;
    private final int numberOfBest = 2;
    private SATFormulaStats satFormulaStats;
    private final int MAX_NUMBER_OF_ITERATIONS = 10000;

    public SmartLocalSearch(SATFormula satFormula) {
        this.satFormula = satFormula;
    }

    @Override
    public BitVector getSolution() {
        Random rand = new Random();
        BitVector x = new BitVector(rand, satFormula.getNumberOfVariables());
        satFormulaStats = new SATFormulaStats(satFormula);

        for (int i = 0; i < MAX_NUMBER_OF_ITERATIONS; i++) {
            satFormulaStats.setAssignment(x, true);

            if (satFormulaStats.isSatisfied()) return x;

            BitVectorNGenerator bitVectorNGenerator = new BitVectorNGenerator(x);
            BitVector[] neighbourhood = bitVectorNGenerator.createNeighbourhood();
            BitVector[] bestNeighbours = bestNeighbours(neighbourhood);
            x = bestNeighbours[rand.nextInt(numberOfBest)];
        }
        return null;
    }

    public BitVector[] bestNeighbours(BitVector[] neighbourhood) {
        ArrayList<Double> qualities = new ArrayList<>();
        BitVector[] bestNeighbours = new BitVector[numberOfBest];
        int i = 0;

        for(BitVector b : neighbourhood) {
            satFormulaStats.setAssignment(b, false);
            qualities.add(satFormulaStats.getPercentageBonus());
        }

        for(i = 0; i < numberOfBest; i++) {
            int maxIndex = 0;
            for(int j = 1; j < qualities.size(); j++) {
                if(qualities.get(j) > qualities.get(maxIndex)) maxIndex = j;
            }
            bestNeighbours[i] = neighbourhood[maxIndex];
            qualities.remove(maxIndex);
        }
        return bestNeighbours;
    }
}
