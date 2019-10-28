package hr.fer.zemris.optjava.dz4;

import hr.fer.zemris.optjava.dz4.part1.DoubleArraySolution;
import hr.fer.zemris.optjava.dz4.part2.Box;
import hr.fer.zemris.optjava.dz4.part2.Column;
import hr.fer.zemris.optjava.dz4.part2.Stick;

import java.util.Random;

public class Mutations {

    public static DoubleArraySolution mutate(DoubleArraySolution chromosome, double s) {
        Random rand = new Random();
        DoubleArraySolution mutatedChromosome = chromosome.newLikeThis();

        for (int i = 0; i < chromosome.size(); i++)
            mutatedChromosome.set(i, chromosome.get(i) + rand.nextGaussian() * s);

        return mutatedChromosome;
    }

    public static Box mutate(Box box, double sigma) {
        Random rand = new Random();
        if (rand.nextDouble() > sigma) return box;

        int index = rand.nextInt(box.getFill());

        Column c = box.get(index);
        box.removeColumn(c);

        for (Stick s : c) box.addStick(s);

        return box;
    }
}
