package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.Util;
import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class IfFoodAhead implements INode{

    private INode False;
    private INode True;
    private int depth;
    private NodeType type = NodeType.IfFoodAhead;

    public IfFoodAhead(INode True, INode False, int depth) {
        this.False = False;
        this.True = True;
        this.depth = depth;
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException {
        if (worldFrame.isFoodInFrontOfAnt()) True.execute(worldFrame);
        else False.execute(worldFrame);    }

    public void execute(Ant ant) throws InterruptedException {
        if (ant.isFoodInFront()) True.execute(ant);
        else False.execute(ant);
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int countNodesInSubtree() {
        return 2 + False.countNodesInSubtree() + True.countNodesInSubtree();
    }

    @Override
    public INode clone() {
        return new IfFoodAhead(False.clone(), True.clone(), depth);
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public List<INode> getSubTreeNodes() {
        List<INode> nodeList = new ArrayList<>();
        if (Util.isFunction(False)) {
            nodeList.addAll(False.getSubTreeNodes());
            nodeList.add(False);
        }
        if (Util.isFunction(True)) {
            nodeList.addAll(True.getSubTreeNodes());
            nodeList.addAll(True.getSubTreeNodes());
        }

        return nodeList;
    }

    public void setFalse(INode aFalse) {
        False = aFalse;
    }

    public void setTrue(INode aTrue) {
        True = aTrue;
    }

    @Override
    public void updateDepths(int depth) {
        this.depth = depth;
        False.updateDepths(depth + 1);
        True.updateDepths(depth + 1);
    }
}
