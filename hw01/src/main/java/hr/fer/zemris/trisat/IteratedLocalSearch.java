package hr.fer.zemris.trisat;

import java.util.*;

public class IteratedLocalSearch extends LocalSearch{

    private final double perturbationPercentage;

    public IteratedLocalSearch(SATFormula satFormula, int MAX_FLIPS, double perturbationPercentage) {
        super(satFormula, MAX_FLIPS);
        this.perturbationPercentage = perturbationPercentage;
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

            if (neighbourhoodQuality <= xQuality) {
                int variablesToPerturb = (int) Math.round(perturbationPercentage * satFormula.getNumberOfVariables());
                for(int index : getIndexesToFlip(variablesToPerturb)) x.set(index, !x.get(index));
                i = 0;
            }
            else x = bestSubset.get(rand.nextInt(bestSubset.size()));
        }
        return null;
    }

    public List<Integer> getIndexesToFlip(int variablesToPerturbate) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < satFormula.getNumberOfVariables(); i++) indexes.add(i);
        Collections.shuffle(indexes);
        return  indexes.subList(0, variablesToPerturbate);
    }
}
