package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.dz12.Ant;
import hr.fer.zemris.optjava.dz12.INode;
import hr.fer.zemris.optjava.dz12.NodeType;
import hr.fer.zemris.optjava.dz12.Util;
import hr.fer.zemris.optjava.GUI.WorldFrame;

import java.util.ArrayList;
import java.util.List;

public class Prog3 implements INode{

    private INode a;
    private INode b;
    private INode c;
    private int depth;
    private NodeType type = NodeType.Prog3;
    private boolean run;

    public Prog3(INode a, INode b, INode c, int depth) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.depth = depth;
        this.run = true;
    }

    public void execute(WorldFrame worldFrame) throws InterruptedException {
        if (run) {
            a.execute(worldFrame);
            b.execute(worldFrame);
            c.execute(worldFrame);
        }
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
    public List<INode> getFunctionNodes() {
        List<INode> nodeList = new ArrayList<>();
        if (Util.isFunction(a)){
            nodeList.addAll(a.getFunctionNodes());
            nodeList.add(a);
        }
        if (Util.isFunction(b)) {
            nodeList.addAll(b.getFunctionNodes());
            nodeList.add(b);
        }
        if (Util.isFunction(c)) {
            nodeList.addAll(c.getFunctionNodes());
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

    @Override
    public void setRun(boolean run) {
        this.run = run;
        this.a.setRun(run);
        this.b.setRun(run);
        this.c.setRun(run);
    }

    @Override
    public String toString() {
        return "Prog3(" + a.toString() + ", " + b.toString() + ", " + c.toString() + ")";

    }
}
