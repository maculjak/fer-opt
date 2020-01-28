package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.GUI.WorldFrame;

import java.util.List;

public interface INode {
    void execute(WorldFrame worldFrame) throws InterruptedException;
    void execute(Ant ant) throws InterruptedException;
    int getDepth();
    int countNodesInSubtree();
    INode clone();
    List<INode> getFunctionNodes();
    NodeType getType();
    void updateDepths(int depth);
    void setRun(boolean run);
}
