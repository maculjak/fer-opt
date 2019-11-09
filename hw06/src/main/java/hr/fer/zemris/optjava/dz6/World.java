package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {

    private ArrayList<City> cities;
    private HashMap<City, ArrayList<City>> closestFromCity;
    private HashMap<City, ArrayList<City>> otherNeighbours;

    private double[][] distanceMatrix;
    private double[][] pheromoneTrails;
    private double[][] etaMatrix;
    private int size;

    private double ALPHA;

    private Random rand;

    public World() {
        cities = new ArrayList<>();
        Random rand = new Random();
    }

    public boolean addCity(City c) {
        return cities.add(c);
    }

    public void updateStructures(int CANDIDATES, double BETA) {
        size = cities.size();
        closestFromCity = new HashMap<>();
        distanceMatrix = new double[size][size];
        pheromoneTrails = new double[size][size];
        etaMatrix = new double[size][size];

        int rowIndex = 0;
        for (City city : cities) {
            int columnIndex = 0;

            for (City neighbour : cities) {
                if (neighbour.equals(city)) {
                    columnIndex++;
                    continue;
                }

                double distance = city.getDistance(neighbour);
                distanceMatrix[rowIndex][columnIndex] = distance;
                etaMatrix[rowIndex][columnIndex] = Math.pow(1.0 / distance, BETA);

                if (!closestFromCity.containsKey(city)) {
                    ArrayList<City> neighbours = new ArrayList<>();
                    neighbours.add(neighbour);
                    closestFromCity.put(city, neighbours);
                } else {
                    ArrayList<City> neighbours = closestFromCity.get(city);
                    int size = neighbours.size();

                    boolean added = false;
                    for (int i = 0; i < size && size < CANDIDATES; i++) {
                        City current = neighbours.get(i);

                        if (distance < city.getDistance(current)) {
                            neighbours.add(i, neighbour);
                            added = true;
                            break;
                        }
                    }

                    if (!added && size < CANDIDATES) neighbours.add(neighbour);
                }
                columnIndex++;
            }
            rowIndex++;
        }

        otherNeighbours = new HashMap<>();

        for (City c : cities) {
            ArrayList<City> othersList = new ArrayList<>();
            otherNeighbours.put(c, othersList);
            ArrayList<City> candidatesList = closestFromCity.get(c);
            for (City other : cities) if (!candidatesList.contains(other)) othersList.add(other);
        }

    }

    public void setPheromoneTrails(double tau) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pheromoneTrails[i][j] = tau;
            }
        }
    }

    public void evaporatePheromoneTrails(double tauMIN, double RHO) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (pheromoneTrails[i][j] * (1 - RHO) < tauMIN) pheromoneTrails[i][j] = tauMIN;
                else pheromoneTrails[i][j] = pheromoneTrails[i][j] * (1 - RHO);
            }
        }
    }

    public void updatePheromoneTrail(int i, int j, double delta, double tauMAX) {
        if (delta + pheromoneTrails[i][j] > tauMAX) pheromoneTrails[i][j] = tauMAX;
        pheromoneTrails[i][j] += delta;
    }

    public City get(int index) {
        return cities.get(index);
    }

    public City randomCity() {
        return get(rand.nextInt(size));
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<City> getClosest(City c) {
        return closestFromCity.get(c);
    }

    public ArrayList<City> getOtherNeighbours(City c) {
        return otherNeighbours.get(c);
    }

    public double getDistance(City c1, City c2) {
        return distanceMatrix[c1.getIndex()][c2.getIndex()];
    }

    public double eta(City c1, City c2) {
        return etaMatrix[c1.getIndex()][c2.getIndex()];
    }

    double tau(City c1, City c2) {
        return pheromoneTrails[c1.getIndex()][c2.getIndex()];
    }

    public int size() {
        return size;
    }

    public double getDistancesSum() {
        double sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += distanceMatrix[i][j];
            }
        }
        return sum;
    }

    public double getRemainingCitiesTravelInfo(City city, ArrayList<City> remaining) {
        double sum = 0;
        for (City c : remaining) sum += eta(city, c) * Math.pow(tau(city, c), ALPHA);
        return sum;
    }

    public void setTravelProbabilities(City city, ArrayList<City> remaining) {
        double denominator = getRemainingCitiesTravelInfo(city, remaining);
        HashMap<City, Double> probabilities = new HashMap<>();

        for (City c : remaining) c.setTravelProbability(Math.pow(tau(city, c), ALPHA) * eta(city, c) / denominator);
    }

    public void setALPHA(double ALPHA) {
        this.ALPHA = ALPHA;
    }
}
