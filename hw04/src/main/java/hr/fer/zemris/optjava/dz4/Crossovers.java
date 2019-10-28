package hr.fer.zemris.optjava.dz4;

import hr.fer.zemris.optjava.dz4.part1.DoubleArraySolution;
import hr.fer.zemris.optjava.dz4.part2.Box;
import hr.fer.zemris.optjava.dz4.part2.Column;
import hr.fer.zemris.optjava.dz4.part2.Stick;

import java.util.*;

public class Crossovers {

    public static DoubleArraySolution BLXAlpha(double ALPHA, DoubleArraySolution parent1, DoubleArraySolution parent2) {
        DoubleArraySolution offspring = parent1.newLikeThis();
        Random rand = new Random();
        for (int i = 0; i < parent1.size(); i++) {

            double ci1 = parent1.get(i);
            double ci2 = parent2.get(i);
            double cimin = Math.min(ci1, ci2);
            double cimax = Math.max(ci1, ci2);

            double Ii = cimax - cimin;
            double lowerBound = cimin - Ii * ALPHA;
            double upperBound = cimax + Ii * ALPHA;

            offspring.set(i, lowerBound + rand.nextDouble() * (upperBound - lowerBound));
        }
        return offspring;
    }

    public static Box boxCrossover(Box parent1, Box parent2) {
        Random rand = new Random();

        int i1 = rand.nextInt(parent2.getFill());
        int i2 = rand.nextInt(parent2.getFill());

        List<Column> temp = parent2.getColumnsBetweenIndexes(Math.min(i1, i2), Math.max(i1, i2));
        List<Column> columns2 = new ArrayList<>();

        for (Column c : temp) columns2.add(c.duplicate());

        HashSet<Stick> unassignedSticks1 = new HashSet<>();
        Box parent1Clone = parent1.duplicate();
        tidyUp(parent1Clone, columns2, unassignedSticks1);

        parent1Clone.addAllColumns(columns2);
        parent1Clone.sort();

        for (Stick s : unassignedSticks1)
            parent1Clone.addStick(s);

        return parent1Clone;
    }

    private static Box tidyUp(Box parentClone, List<Column> columns, HashSet<Stick> unassignedSticks) {
        int size = columns.size();
        ArrayList<Column> columnsToRemove = new ArrayList<>();

        for (int j = 0; j < size; j++) {
            Column c = columns.get(j);
            HashSet<Stick> stickSet = new HashSet<>(c.getSticks());
            int length = parentClone.getFill();

            for (int i = 0; i < length; i++) {
                Column c2 = parentClone.get(i);
                if (c2.removeAllSticks(stickSet))
                    columnsToRemove.add(c2);
            }
        }

        columnsToRemove.forEach(c -> {
            unassignedSticks.addAll(c.getSticks());
            parentClone.removeColumn(c);
        });

        return parentClone;
    }
}
