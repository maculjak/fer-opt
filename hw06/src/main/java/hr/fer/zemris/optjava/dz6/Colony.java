package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;

public class Colony {

    private ArrayList<Ant> colony;

    public Colony() {
        this.colony = new ArrayList<>();
    }

    public boolean addAnt(Ant ant) {
        return colony.add(ant);
    }

    public Ant getBestAnt() {
        Ant best = null;
        for (Ant a : colony) if (best == null || a.getDistanceTravelled() < best.getDistanceTravelled()) best = a;
        return best;
    }
}
