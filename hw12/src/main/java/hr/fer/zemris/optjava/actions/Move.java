package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.dz12.Ant;
import hr.fer.zemris.optjava.dz12.INode;
import hr.fer.zemris.optjava.dz12.NodeType;
import hr.fer.zemris.optjava.GUI.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Move implements INode {

    private int depth;
    private NodeType type = NodeType.Move;
    private boolean run;

    public Move(int depth) {
        this.depth = depth;
    }

    public void execute(Ant ant) throws InterruptedException {
        ant.move();
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException {
        if (run) {
            worldFrame.moveAnt();
            Thread.sleep(100);
        }
    }


    public int getDepth() {
        return depth;
    }

    @Override
    public int countNodesInSubtree() {
        return 0;
    }

    @Override
    public INode clone() {
        return new Move(depth);
    }

    @Override
    public List<INode> getFunctionNodes() {
        return new ArrayList<>();
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public void updateDepths(int depth) {
        this.depth = depth;
    }

    @Override
    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public String toString() {
        return "Move";
    }
}

