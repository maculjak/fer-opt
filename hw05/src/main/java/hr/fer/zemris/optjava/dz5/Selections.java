package hr.fer.zemris.optjava.dz5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import hr.fer.zemris.optjava.dz5.Solution;

public class Selections {

    public static <T extends Solution> T Tournament(List<T> population, int k) {
        List<T> temp = new ArrayList<>(population);
        List<T> participants = new ArrayList<>(k);
        int unitsInPopulation = population.size();
        Random rand = new Random();

        for (int i = 0; i < k; i++) {
            int participantIndex = rand.nextInt(unitsInPopulation - i);
            T nextParticipant = temp.get(participantIndex);
            participants.add(nextParticipant);
            temp.remove(participantIndex);
        }

        return participants.stream().max(Solution::compareTo).orElse(null);
    }

    public static <T extends Solution> T random(List<T> population) {
        Random rand = new Random();
        int index = rand.nextInt(population.size());
        return population.get(index);
    }
}
