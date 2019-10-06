package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {

    private BitVector assignment;

    public BitVectorNGenerator(BitVector assignment) {
        this.assignment = assignment;
    }

    public Iterator<MutableBitVector> iterator() {
        return new Iterator<MutableBitVector>() {

            private int numberOfNeighbours = assignment.getSize();
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < this.numberOfNeighbours;
            }

            @Override
            public MutableBitVector next() {
                MutableBitVector temp = new MutableBitVector(assignment.bits.clone());
                temp.set (index, !temp.get(index));
                index++;
                return temp;
            }
        };
    }

    public MutableBitVector[] createNeighbourhood() {
        MutableBitVector[] mutableBitVectors = new MutableBitVector[assignment.getSize()];
        Iterator<MutableBitVector> iterator = iterator();

        int i = 0;
        while (iterator.hasNext()) {
            mutableBitVectors[i] = iterator.next();
            i++;
        }
        return mutableBitVectors;
    }
}
