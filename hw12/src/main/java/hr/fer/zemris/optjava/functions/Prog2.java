package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.Util;
import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Prog2 implements INode {

    private INode a;
    private INode b;
    private int depth;
    private NodeType type = NodeType.Prog2;

    public Prog2(INode a, INode b, int depth) {

        this.a = a;
        this.b = b;
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException {
        a.execute(worldFrame);
        b.execute(worldFrame);
    }

    public void execute(Ant ant) throws InterruptedException {
        a.execute(ant);
        b.execute(ant);
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int countNodesInSubtree() {
        return 2 + a.countNodesInSubtree() + b.countNodesInSubtree();
    }

    @Override
    public INode clone() {
        return new Prog2(a.clone(), b.clone(), depth);
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public List<INode> getSubTreeNodes() {
        List<INode> nodeList = new ArrayList<>();
        if (Util.isFunction(a)) {
            nodeList.addAll(a.getSubTreeNodes());
            nodeList.add(a);
        }
        if (Util.isFunction(b)) {
            nodeList.addAll(b.getSubTreeNodes());
            nodeList.add(b);
        }
        return nodeList;
    }

    public void setA(INode a) {
        this.a = a;
    }

    public void setB(INode b) {
        this.b = b;
    }

    @Override
    public void updateDepths(int depth) {
        this.depth = depth;
        a.updateDepths(depth + 1);
        b.updateDepths(depth + 1);
    }

}
