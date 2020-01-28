package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.dz12.Ant;
import hr.fer.zemris.optjava.dz12.INode;
import hr.fer.zemris.optjava.dz12.NodeType;
import hr.fer.zemris.optjava.dz12.Util;
import hr.fer.zemris.optjava.GUI.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class IfFoodAhead implements INode{

    private INode False;
    private INode True;
    private int depth;
    private NodeType type = NodeType.IfFoodAhead;
    private boolean run;

    public IfFoodAhead(INode True, INode False, int depth) {
        this.False = False;
        this.True = True;
        this.depth = depth;
        this.run = true;
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException {
        if (run) {
            if (worldFrame.isFoodInFrontOfAnt()) True.execute(worldFrame);
            else False.execute(worldFrame);
        }
        }


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
    public List<INode> getFunctionNodes() {
        List<INode> nodeList = new ArrayList<>();
        if (Util.isFunction(False)) {
            nodeList.addAll(False.getFunctionNodes());
            nodeList.add(False);
        }
        if (Util.isFunction(True)) {
            nodeList.addAll(True.getFunctionNodes());
            nodeList.addAll(True.getFunctionNodes());
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

    @Override
    public void setRun(boolean run) {
        this.run = run;
        this.True.setRun(run);
        this.False.setRun(run);
    }

    @Override
    public String toString() {
        return "IfFoodAhead(" + False.toString() + ", " + True.toString() + ")";
    }
}
