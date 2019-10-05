package hr.fer.zemris.trisat;

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
        for(int i = 0; i < getSize(); i++) {
            if(indexes[i] != 0) {
                boolean bit = assignment.get(i);
                if(indexes[i] < 0 && !bit || indexes[i] > 0 && bit) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(int index : indexes) {
            if(index == 0) continue;
            sb.append(index);
            sb.append(" + ");
        }
        return sb.substring(0, sb.length() - 3) + ")";
    }

}
