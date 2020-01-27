package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.Solution;
import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Left implements INode {

    private int depth;
    private NodeType type = NodeType.Left;

    public Left(int depth) {
        this.depth = depth;
    }

    public void execute(WorldFrame worldFrame) {
        worldFrame.rotateAnt("L");
    }

    public void execute(Ant ant) {
        ant.rotateLeft();
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int countNodesInSubtree() {
        return 1;
    }

    @Override
    public INode clone() {
        return new Left(depth);
    }

    @Override
    public List<INode> getSubTreeNodes() {
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
}
