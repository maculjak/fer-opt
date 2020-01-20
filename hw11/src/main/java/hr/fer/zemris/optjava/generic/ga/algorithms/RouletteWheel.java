package hr.fer.zemris.optjava.generic.ga.algorithms;

import hr.fer.zemris.optjava.generic.ga.GASolution;
import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RouletteWheel {

    private IRNG rng;

    public RouletteWheel() {
        this.rng = RNG.getRNG();
    }

    public Solution select(List<Solution> population, int numberOfParticipants) {

        List<Solution> sortedPopulation;

        sortedPopulation = population.stream()
                .sorted(Comparator.comparingDouble(Solution::getFitness).reversed())
                .limit(numberOfParticipants)
                .collect(toList());

        double minFitness = sortedPopulation.get(0).getFitness();
        double sum = 0;
        double result = rng.nextDouble();

        for (Solution solution : sortedPopulation) sum += solution.getFitness() - minFitness;

        double previousProbability = 0;

        for (int i = 0; i < numberOfParticipants; i++) {
            double value = sortedPopulation.get(i).getFitness();
            double probability = (value - minFitness) / sum;
            previousProbability += probability;

            if (result < previousProbability) return sortedPopulation.get(i);
        }
        return sortedPopulation.get(sortedPopulation.size() - 1);
    }
}
