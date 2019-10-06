package hr.fer.zemris.trisat;

import java.util.Random;

public class MutableBitVector extends BitVector {

    public MutableBitVector(boolean ... bits) {
        super(bits);
    }

    public MutableBitVector(int n) {
        super(n);
    }

    public MutableBitVector(Random rand, int n) { super(rand, n); }

    public void set(int index, boolean value) {
        super.bits[index] = value;
    }
}
