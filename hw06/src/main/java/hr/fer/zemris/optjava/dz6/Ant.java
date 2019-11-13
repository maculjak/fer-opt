package hr.fer.zemris.optjava.dz6;

import java.util.*;

public class Ant implements Iterator<City> {

    World world;

    private static final Random rand = new Random();
    private City currentCity;
    private City initialCity;
    private ArrayList<City> visited;
    private ArrayList<City> remaining;
    private double distanceTravelled;

    public Ant(World world) {
        this.world = world;

        initialCity = world.get(rand.nextInt(world.size()));
        currentCity = initialCity;

        visited = new ArrayList<>();
        visited.add(currentCity);

        remaining = new ArrayList<>(world.getCities());
        remaining.remove(currentCity);

        distanceTravelled = 0;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    @Override
    public boolean hasNext() {
        return !remaining.isEmpty();
    }

    @Override
    public City next() {
        ArrayList<City> citiesToChooseFrom = world.getClosest(currentCity);
        citiesToChooseFrom.removeAll(visited);

        if (citiesToChooseFrom.isEmpty()) citiesToChooseFrom = remaining;

        world.setTravelProbabilities(currentCity, citiesToChooseFrom);
        citiesToChooseFrom.sort(Comparator.comparingDouble(City::getTravelProbability).reversed());

        double previousProbability = 0;
        double value = rand.nextDouble();

        City nextCity = citiesToChooseFrom.get(0);

        for (City c : citiesToChooseFrom) {
            previousProbability += c.getTravelProbability();

            if (value < previousProbability) {
                nextCity = c;
                break;
            }
        }

        visited.add(nextCity);
        remaining.remove(nextCity);

        distanceTravelled += world.getDistance(currentCity, nextCity);
        currentCity = nextCity;
        return nextCity;
    }

    public City greedyNext() {
        City nextCity = getClosestRemaining(currentCity);
        visited.add(nextCity);
        remaining.remove(nextCity);

        distanceTravelled += world.getDistance(currentCity, nextCity);
        currentCity = nextCity;

        return nextCity;
    }

    public void greedyTravel() {
        while (hasNext()) greedyNext();
        distanceTravelled += world.getDistance(currentCity, initialCity);
    }

    public void smartTravel() {
        while (hasNext()) next();
        distanceTravelled += world.getDistance(currentCity, initialCity);
    }

    public void printRoute() {
        int i = 0;
        for (City c : visited) {
            System.out.print(c + " -> ");
            i++;
            if (i % 10 == 0) System.out.println();
        }
        System.out.println(initialCity + "\nDistance: " + Math.round(distanceTravelled) + "\n");
    }

    private City getClosestRemaining (City city) {
        City closest = null;
        for (City c : remaining) {
            if (closest == null || world.getDistance(city, c) < world.getDistance(city, closest))
                closest = c;
        }
        return  closest;
    }

    public void updatePheromoneTrails(double tauMAX) {
        int j = 1;
        int size = visited.size();
        for (int i = 0; j < size; i++) {
            int index1 = visited.get(i).getIndex();
            int index2 = visited.get(j++).getIndex();
            world.updatePheromoneTrail(index1, index2, 1 / distanceTravelled, tauMAX);
        }
    }
}
