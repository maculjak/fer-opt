package hr.fer.zemris.optjava.generic.ga;

import java.util.Arrays;

public class Solution extends GASolution<int[]> {

    public Solution(int[] data) {
        super.data = data;
    }

    @Override
    public GASolution<int[]> duplicate() {
        return new Solution(Arrays.copyOf(data, data.length));
    }



}
