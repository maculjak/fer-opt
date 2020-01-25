package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.INode;

import java.util.PriorityQueue;

public class Prog2 implements INode {

    private INode a;
    private INode b;

    public Prog2(INode a, INode b) {
        this.a = a;
        this.b = b;
    }

    public void execute() {
        a.execute();
        b.execute();
    }
}
