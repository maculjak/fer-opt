package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.Solution;
import hr.fer.zemris.optjava.actions.Move;
import hr.fer.zemris.optjava.actions.Right;
import hr.fer.zemris.optjava.functions.IfFoodAhead;
import hr.fer.zemris.optjava.functions.Prog2;
import hr.fer.zemris.optjava.functions.Prog3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation extends Thread {

    private WorldFrame worldFrame;
    private Solution solution;

    public Simulation(WorldFrame worldFrame, Solution solution) {
        this.worldFrame = worldFrame;
        this.solution = solution;
    }

    public void run() {

        INode node = solution.getRoot();


        Random rand = new Random();
        int index = 0;
        List<INode> list = new ArrayList<>();
        Solution solution = new Solution(node);
        solution.evaluate(worldFrame.getMap());

        while(true) {
            try {
                node.execute(worldFrame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

