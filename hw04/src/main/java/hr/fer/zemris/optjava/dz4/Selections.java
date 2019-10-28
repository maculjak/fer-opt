package hr.fer.zemris.optjava.dz4;

import hr.fer.zemris.optjava.dz4.part1.DoubleArraySolution;
import hr.fer.zemris.optjava.dz4.part2.Box;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Selections {

    public static DoubleArraySolution tournament(ArrayList<DoubleArraySolution> population, int numberOfParticipants) {
        Collections.shuffle(population);
        ArrayList<DoubleArraySolution> participants = new ArrayList<>(population.subList(0, numberOfParticipants));
        DoubleArraySolution bestParticipant = participants.get(0);

        for (DoubleArraySolution chromosome : participants)
            if (chromosome.getFitness() > bestParticipant.getFitness()) bestParticipant = chromosome;

        return bestParticipant;
    }

    public static DoubleArraySolution rouletteWheel(ArrayList<DoubleArraySolution> population, int numberOfParticipants) {
        Random rand = new Random();
        List<DoubleArraySolution> sortedPopulation;

        sortedPopulation = population.stream()
               .sorted(Comparator.comparingDouble(DoubleArraySolution::getFitness).reversed())
               .limit(numberOfParticipants)
               .collect(toList());

        double minFitness = sortedPopulation.get(0).getFitness();
        double sum = 0;
        double result = rand.nextDouble();

        for (DoubleArraySolution solution : sortedPopulation) sum += solution.getFitness() - minFitness;

        double previousProbability = 0;

        for (int i = 0; i < numberOfParticipants; i++) {
            double value = sortedPopulation.get(i).getFitness();
            double probability = (value - minFitness) / sum;
            previousProbability += probability;

            if (result < previousProbability) return sortedPopulation.get(i);
        }
        return sortedPopulation.get(sortedPopulation.size() - 1);
    }

    public static Box boxTournament(ArrayList<Box> population, int numberOfParticipants, boolean best) {
        Collections.shuffle(population);
        List<Box> participants = population.subList(0, numberOfParticipants);
        Box bestBox = participants.get(0);

        if (best) {
            for (Box box : participants) if (box.getFill() < bestBox.getFill())
                bestBox = box;
        } else {
            for (Box box : participants) if (box.getFill() > bestBox.getFill())
                bestBox = box;
        }

        return bestBox;
    }
}
