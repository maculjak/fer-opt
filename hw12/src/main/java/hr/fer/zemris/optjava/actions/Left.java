package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.dz12.Ant;
import hr.fer.zemris.optjava.dz12.INode;
import hr.fer.zemris.optjava.dz12.NodeType;
import hr.fer.zemris.optjava.GUI.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Left implements INode {

    private int depth;
    private NodeType type = NodeType.Left;
    private boolean run;

    public Left(int depth) {
        this.depth = depth;
        this.run = true;
    }

    public void execute(WorldFrame worldFrame) {
        if (run) worldFrame.rotateAnt("L");
    }

    public void execute(Ant ant) {
        ant.rotateLeft();
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
        return new Left(depth);
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

    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public String toString() {
        return "Left";
    }
}
