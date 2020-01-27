package hr.fer.zemris.optjava;

import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.List;

public interface INode {
    void execute(WorldFrame worldFrame) throws InterruptedException;
    void execute(Ant ant) throws InterruptedException;
    int getDepth();
    int countNodesInSubtree();
    INode clone();
    List<INode> getSubTreeNodes();
    NodeType getType();
    void updateDepths(int depth);
}
