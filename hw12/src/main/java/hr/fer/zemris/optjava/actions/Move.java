package hr.fer.zemris.optjava.actions;

import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.dz12.WorldFrame;

public class Move implements IAction {
    ActionType type = ActionType.Move;
    WorldFrame worldFrame;

    public Move(WorldFrame worldFrame) {
        this.worldFrame = worldFrame;
    }

    public void execute() {
        worldFrame.moveAnt();
    }
}
