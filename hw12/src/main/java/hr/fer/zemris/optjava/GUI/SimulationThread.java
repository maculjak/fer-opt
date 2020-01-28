package hr.fer.zemris.optjava.GUI;

import hr.fer.zemris.optjava.dz12.INode;
import hr.fer.zemris.optjava.dz12.Solution;

public class SimulationThread extends Thread{
    private Solution solution;
    private WorldFrame worldFrame;
    private boolean run;

    public SimulationThread(Solution solution, WorldFrame worldFrame) {
        this.solution = solution;
        this.worldFrame = worldFrame;
        this.run = true;
    }

    public void run() {
        INode node = solution.getRoot();
        while(run) {
            try {
                node.execute(worldFrame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}

