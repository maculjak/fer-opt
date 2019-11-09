package hr.fer.zemris.optjava.dz6;

public class TSPSolver {

    public static void main(String[] args) {

        if (args.length != 4) {
            System.err.println("Invalid number of arguments. Closing the program...");
            System.exit(1);
        }

        final String FILE = args[0];
        final int CANDIDATES = Integer.parseInt(args[1]);
        final int ANTS_IN_COLONY = Integer.parseInt(args[2]);
        final int ITERATIONS = Integer.parseInt(args[3]);

        final double ALPHA = 1;
        final double BETA = 5;
        final double RHO = 0.02;
        final double a = 100;

        World world = new TSPFileParser("/" + FILE).getWorld();
        world.setALPHA(ALPHA);
        world.updateStructures(CANDIDATES, BETA);

        Ant greedyAnt = new Ant(world);
        greedyAnt.greedyTravel();
        double tauMAX = 1 / (greedyAnt.getDistanceTravelled());
        double tauMIN = tauMAX / a;

        world.setPheromoneTrails(tauMAX);
        Ant bestSoFarAnt = null;
        int badIterationsCounter = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            Colony colony = new Colony();

            for (int j = 0; j < ANTS_IN_COLONY; j++) {
                Ant ant = new Ant(world);
                colony.addAnt(ant);
                ant.smartTravel();
            }

            world.evaporatePheromoneTrails(tauMIN, RHO);
            Ant colonyBestAnt = colony.getBestAnt();


            if (bestSoFarAnt == null || colonyBestAnt.getDistanceTravelled() < bestSoFarAnt.getDistanceTravelled()) {
                bestSoFarAnt = colonyBestAnt;
                tauMAX = 1.0 / (RHO * bestSoFarAnt.getDistanceTravelled());
                tauMIN = tauMAX / a;
                bestSoFarAnt.printRoute();
            } else {
                badIterationsCounter++;
            }

            bestSoFarAnt.updatePheromoneTrails(tauMAX);

            if (badIterationsCounter == 500) {
                badIterationsCounter = 0;
                world.setPheromoneTrails(tauMAX);
            }

        }
    }
}
