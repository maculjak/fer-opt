package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.dz12.WorldFrame;

public class Right implements INode {
    private ActionType type = ActionType.Right;
    private WorldFrame worldFrame;

    public Right(WorldFrame worldFrame) {
        this.worldFrame = worldFrame;
    }

    public void execute() {
        worldFrame.rotateAnt("R");
    }

}
