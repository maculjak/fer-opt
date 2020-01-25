package hr.fer.zemris.optjava;

import hr.fer.zemris.optjava.actions.Move;
import hr.fer.zemris.optjava.dz12.WorldFrame;

public class Tree {
    private INode root;

    public Tree(WorldFrame worldFrame) {
        this.root = new Move(worldFrame);
    }

    public void traverse() {
        root.execute();
    }
}
