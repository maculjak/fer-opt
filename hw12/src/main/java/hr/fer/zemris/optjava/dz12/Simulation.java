package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.Tree;
import hr.fer.zemris.optjava.actions.Move;
import hr.fer.zemris.optjava.actions.Right;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation extends Thread {

    private WorldFrame worldFrame;

    public Simulation(WorldFrame worldFrame) {
        this.worldFrame = worldFrame;
    }

    public void run() {

        Random rand = new Random();
        Right right = new Right(worldFrame);
        Move move = new Move(worldFrame);
        int index = 0;
        List<INode> list = new ArrayList<>();
        list.add(move);
        list.add(move);
        list.add(move);
        list.add(move);
        list.add(right);
        while(true) {
            list.get(index).execute();
            index = (index + 1) % 5;


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

