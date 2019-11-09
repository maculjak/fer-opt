package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Objects;

public class City {

    private double x;
    private double y;
    private int index;
    private double travelProbability;

    public City(double x, double y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistance(City c) {
        return Math.sqrt((x - c.x)*(x - c.x) + (y - c.y)*(y - c.y));
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("%4d", index);    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(city.x, x) == 0 &&
                Double.compare(city.y, y) == 0 &&
                index == city.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, index);
    }

    public void setTravelProbability(double probability) {
        this.travelProbability = probability;
    }

    public double getTravelProbability() {
        return travelProbability;
    }
}
