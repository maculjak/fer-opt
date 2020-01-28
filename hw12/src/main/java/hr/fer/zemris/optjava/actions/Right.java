package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.dz12.Ant;
import hr.fer.zemris.optjava.dz12.INode;
import hr.fer.zemris.optjava.dz12.NodeType;
import hr.fer.zemris.optjava.GUI.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Right implements INode {

    private int depth;
    private NodeType type = NodeType.Right;
    private boolean run;

    public Right(int depth) {
        this.depth = depth;
        this.run = true;
    }

    public void execute(WorldFrame worldFrame) {
        if (run) worldFrame.rotateAnt("R");
    }

    public void execute(Ant ant) {
        ant.rotateRight();
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
        return new Right(depth);
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
        return "Right";
    }
}
