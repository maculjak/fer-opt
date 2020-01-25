package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.INode;

public class Prog3 {

    private INode a;
    private INode b;
    private INode c;

    public Prog3(INode a, INode b, INode c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void execute() {
        a.execute();
        b.execute();
        c.execute();
    }
}
