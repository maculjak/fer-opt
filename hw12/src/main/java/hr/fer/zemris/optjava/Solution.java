package hr.fer.zemris.optjava;

import java.util.ArrayList;
import java.util.List;

public class Solution implements Comparable<Solution> {

    INode root;
    int foodEaten;
    int numberOfNodes;
    private List<INode> nodes;
    double score;

    public Solution(INode root) {
        this.root = root;
        this.foodEaten = 0;
        numberOfNodes = 1 + root.countNodesInSubtree();
        this.nodes = new ArrayList<>();
        nodes.add(root);
    }

    public void evaluate(boolean[][] map) {
        Ant ant = new Ant(0, 0, 0, map.length, map[0].length, Util.copy2DArray(map));
        try {
            int movesLeft = ant.getMovesLeft();
            while (movesLeft > 0) {
                root.execute(ant);
                movesLeft = ant.getMovesLeft();
            }
            this.foodEaten = ant.getScore();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        numberOfNodes = 1 + root.countNodesInSubtree();

        score = foodEaten;
    }

    public INode getRoot() {
        return root;
    }

    @Override
    public int compareTo(Solution o) {
        return Double.compare(foodEaten, o.foodEaten);
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public double getScore() {
        return score;
    }

    public List<INode> getNodes() {
        return root.getSubTreeNodes();
    }

    public void penalty() {
        score *= 0.9;
    }

    public int getFoodEaten() {
        return foodEaten;
    }
}
