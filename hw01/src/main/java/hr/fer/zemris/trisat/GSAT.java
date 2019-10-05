package hr.fer.zemris.trisat;

public class GSAT implements Algorithm{

    private SATFormula satFormula;
    private int MAX_TRIES;
    private int MAX_FLIPS;

    public GSAT(SATFormula satFormula, int maxTries, int maxFlips) {
        this.satFormula = satFormula;
        this.MAX_TRIES = maxTries;
        this.MAX_FLIPS = maxFlips;
    }

    public BitVector getSolution() {
        for(int i = 0; i < MAX_TRIES; i++) {
            BitVector solution = new LocalSearch(satFormula, MAX_FLIPS).getSolution();
            if(solution != null) return solution;
        }
        return null;
    }

}
