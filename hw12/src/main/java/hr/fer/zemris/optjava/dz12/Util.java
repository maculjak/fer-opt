package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.actions.Left;
import hr.fer.zemris.optjava.actions.Move;
import hr.fer.zemris.optjava.actions.Right;
import hr.fer.zemris.optjava.functions.IfFoodAhead;
import hr.fer.zemris.optjava.functions.Prog2;
import hr.fer.zemris.optjava.functions.Prog3;

import java.util.Arrays;
import java.util.Random;

public class Util {

    private static Random rand = new Random();

    public static boolean[][] copy2DArray(boolean[][] array) {
        boolean[][] newArray = new boolean[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static INode genFullNode(int depth, int maxDepth) {
        int randInt = rand.nextInt(3);
        if (depth == maxDepth) return genTerminal(depth, randInt);
        else return genNonTerminal(depth, maxDepth, randInt);
    }

    private static INode genNonTerminal(int depth, int maxDepth, int randInt) {
        if (randInt == 0) return new IfFoodAhead(
                genFullNode(depth + 1, maxDepth)
                , genFullNode(depth + 1, maxDepth)
                , depth + 1);
        else if (randInt == 1) return new Prog2(
                genFullNode(depth + 1, maxDepth)
                , genFullNode(depth + 1, maxDepth)
                , depth + 1);
        else return new Prog3(genFullNode(depth + 1, maxDepth)
                    , genFullNode(depth + 1, maxDepth)
                    , genFullNode(depth + 1, maxDepth)
                    , depth + 1);
    }

    private static INode genTerminal(int depth, int randInt) {
        if (randInt == 0) return new Move(depth);
        else if (randInt == 1) return new Right(depth);
        else return new Left(depth);
    }

    public static INode genGrowNode(int depth, int maxDepth) {
        int randInt = rand.nextInt(3);
        if (depth == maxDepth) return genTerminal(randInt, depth);
        int randInt2 = rand.nextInt(2);
        if (randInt2 == 0) return genTerminal(randInt, depth);
        else return genNonTerminal(depth, maxDepth, randInt);
    }

    public static boolean isFunction(INode node) {
        return node.getType() == NodeType.Prog2 || node.getType() == NodeType.Prog3 || node.getType() == NodeType.IfFoodAhead;
    }
}
