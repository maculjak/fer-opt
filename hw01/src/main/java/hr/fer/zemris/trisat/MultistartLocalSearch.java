package hr.fer.zemris.trisat;

public class MultistartLocalSearch implements Algorithm{

    private SATFormula satFormula;
    private final int MAX_TRIES;
    private final int MAX_FLIPS;

    public MultistartLocalSearch(SATFormula satFormula, int maxTries, int maxFlips) {
        this.satFormula = satFormula;
        this.MAX_TRIES = maxTries;
        this.MAX_FLIPS = maxFlips;
    }

    public BitVector getSolution() {
        for (int i = 0; i < MAX_TRIES; i++) {
            BitVector solution = new LocalSearch(satFormula, MAX_FLIPS).getSolution();
            if (solution != null) return solution;
        }
        return null;
    }

}
