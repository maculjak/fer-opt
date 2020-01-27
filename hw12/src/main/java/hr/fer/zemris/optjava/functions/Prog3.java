package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.Util;
import hr.fer.zemris.optjava.dz12.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Prog3 implements INode{

    private INode a;
    private INode b;
    private INode c;
    private int depth;
    private NodeType type = NodeType.Prog3;

    public Prog3(INode a, INode b, INode c, int depth) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.depth = depth;
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException{
        a.execute(worldFrame);
        b.execute(worldFrame);
        c.execute(worldFrame);
    }

    public void execute(Ant ant) throws InterruptedException{
        a.execute(ant);
        b.execute(ant);
        c.execute(ant);
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int countNodesInSubtree() {
        return 3 + a.countNodesInSubtree() + b.countNodesInSubtree() + c.countNodesInSubtree();
    }

    @Override
    public INode clone() {
        return new Prog3(a.clone(), b.clone(), c.clone(), depth);
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public List<INode> getSubTreeNodes() {
        List<INode> nodeList = new ArrayList<>();
        if (Util.isFunction(a)){
            nodeList.addAll(a.getSubTreeNodes());
            nodeList.add(a);
        }
        if (Util.isFunction(b)) {
            nodeList.addAll(b.getSubTreeNodes());
            nodeList.add(b);
        }
        if (Util.isFunction(c)) {
            nodeList.addAll(c.getSubTreeNodes());
            nodeList.add(c);
        }

        return nodeList;
    }

    public void setA(INode a) {
        this.a = a;
    }

    public void setB(INode b) {
        this.b = b;
    }

    public void setC(INode c) {
        this.c = c;
    }

    public void updateDepths(int depth) {
        this.depth = depth;
        a.updateDepths(depth + 1);
        b.updateDepths(depth + 1);
        c.updateDepths(depth + 1);
    }

}
