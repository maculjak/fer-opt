package hr.fer.zemris.trisat;

import java.util.Random;

public class Clause {

    int[] indexes;

    public Clause(int[] indexes){
        this.indexes = indexes;
    }

    public int getSize() {
        return indexes.length;
    }

    public int getLiteral(int index) {
        return indexes[index];
    }

    public boolean isSatisfied(BitVector assignment){
        for (int i = 0; i < getSize(); i++) {
            if (indexes[i] != 0) {
                boolean bit = assignment.get(i);
                if (indexes[i] < 0 && !bit || indexes[i] > 0 && bit) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int index : indexes) {
            if (index == 0) continue;
            sb.append(index);
            sb.append(" + ");
        }
        return sb.substring(0, sb.length() - 3) + ")";
    }

    public int getRandomLiteral() {
        return getNonZeroLiterals()[new Random().nextInt(getNonZeroLiterals().length)];
    }

    public int[] getNonZeroLiterals() {
        int[] variables = new int[3];
        int index = 0;
        for (int value : indexes) if (value != 0) variables[index++] = value;
        return variables;
    }
}
