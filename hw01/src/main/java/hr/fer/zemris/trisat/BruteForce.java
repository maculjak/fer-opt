package hr.fer.zemris.trisat;

public class BruteForce implements Algorithm {

    private SATFormula satFormula;

    public BruteForce(SATFormula satFormula) {
        this.satFormula = satFormula;
    }

    @Override
    public BitVector getSolution() {
        MutableBitVector bv = new MutableBitVector(satFormula.getNumberOfVariables());
        MutableBitVector returnValue = null;

        while(true) {
            if(satFormula.isSatisfied(bv)) {
                returnValue = bv;
                System.out.println(bv);
            }

            if(bv.allTrue()) break;
            bv = generateNext(bv);
        }

        return returnValue;
    }

    public MutableBitVector generateNext(MutableBitVector old) {
        int i = satFormula.getNumberOfVariables() - 1;
        boolean carry = old.get(i);

        old.set(i, !old.get(i));
        i--;

        while(carry && i >= 0) {
            if(!old.get(i)) carry = false;
            old.set(i, !old.get(i));
            i--;
        }

        return old;
    }

}
