package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Right implements INode {

    private int depth;
    private NodeType type = NodeType.Right;

    public Right(int depth) {
        this.depth = depth;
    }

    public void execute(WorldFrame worldFrame) {
        worldFrame.rotateAnt("R");
    }

    public void execute(Ant ant) {
        ant.rotateRight();
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
        return new Right(depth);
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
