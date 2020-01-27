package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Move implements INode {

    private int depth;
    private NodeType type = NodeType.Move;

    public Move(int depth) {
        this.depth = depth;
    }

    public void execute(Ant ant) throws InterruptedException {
        ant.move();
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException {
        worldFrame.moveAnt();
        Thread.sleep(100);
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
        return new Move(depth);
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
