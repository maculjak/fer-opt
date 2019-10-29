package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.dz4.Crossovers;
import hr.fer.zemris.optjava.dz4.Mutations;
import hr.fer.zemris.optjava.dz4.Selections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

public class BoxFilling {

    public static void main(String[] args) {

        if (args.length != 7) {
            System.err.println("Invalid number of argumetns");
            System.exit(1);
        }

        final int VEL_POP = Integer.parseInt(args[1]);
        final int N = Integer.parseInt(args[2]);
        final int M = Integer.parseInt(args[3]);
        final boolean P = Boolean.parseBoolean(args[4]);
        final int ITERATIONS = Integer.parseInt(args[5]);
        final int STOPCONDITION = Integer.parseInt(args[6]);
        final double SIGMA = 0.7;

        Box box;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(BoxFilling.class.getResourceAsStream(args[0])));
            String[] boxInformations = args[0].split("-");
            int height = Integer.parseInt(boxInformations[1]);

            String line = br.readLine();
            line = line.replace("[", "").replace("]", "");
            String[] stickHeights = line.split(", ");

            int index = 0;
            ArrayList<Stick> sticks = new ArrayList<>();
            for(String stickHeight : stickHeights) {
                sticks.add(new Stick(Integer.parseInt(stickHeight), index++));
            }



            ArrayList<Box> initialPopulation = new ArrayList<>();
            for (int i = 0; i < VEL_POP; i++) {
                box = new Box(stickHeights.length, sticks, height);
                box.randomize();
                initialPopulation.add(box);
            }

            Box best = initialPopulation.stream().min(Comparator.comparingInt(Box::getFill)).orElse(null);
            boolean change = true;

            for (int i = 0; i < ITERATIONS && (best == null || best.getFill() >= STOPCONDITION); i++) {
                Box box1 = Selections.boxTournament(initialPopulation, N, true);
                Box box2 = Selections.boxTournament(initialPopulation, N, true);

                Box boxToAdd = Mutations.mutate(Crossovers.boxCrossover(box1, box2), 0.5);
                Box boxToRemove = Selections.boxTournament(initialPopulation, M, false);

                if (!P || boxToAdd.getFill() < boxToRemove.getFill()) {
                    initialPopulation.remove(boxToRemove);
                    initialPopulation.add(boxToAdd);

                    if (best == null || boxToAdd.getFill() < best.getFill()) best = boxToAdd;
                    change = true;
                }

                if (change) System.out.println(best + "Iteration: " + (i + 1) + " Number of sticks: " + best.getFill());
                change = false;

                if (best.getFill() <= STOPCONDITION) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
