package hr.fer.zemris.optjava.functions;

import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.dz12.WorldFrame;

public class IfFoodAhead implements INode{

    private INode False;
    private INode True;
    private WorldFrame worldFrame;

    public IfFoodAhead(WorldFrame worldFrame, INode False, INode True) {
        this.worldFrame = worldFrame;
        this.False = False;
        this.True = True;
    }

    public void execute() {
        if (worldFrame.isFoodInFrontOfAnt()) False.execute();
        else True.execute();
    }
}
