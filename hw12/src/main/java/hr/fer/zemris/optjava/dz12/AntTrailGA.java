package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.INode;
import hr.fer.zemris.optjava.NodeType;
import hr.fer.zemris.optjava.Solution;
import hr.fer.zemris.optjava.Util;
import hr.fer.zemris.optjava.functions.IfFoodAhead;
import hr.fer.zemris.optjava.functions.Prog2;
import hr.fer.zemris.optjava.functions.Prog3;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AntTrailGA {

    private static List<Integer> indicesList = new ArrayList<>();
    private static Random rand = new Random();

    public static void main(String[] args) {

        final String mapPath = args[0];
        final int GENERATIONS_MAXIMUM = Integer.parseInt(args[1]);
        final int POPULATION_MAXIMUM = Integer.parseInt(args[2]);
        final double FITNESS_MINIMUM = Double.parseDouble(args[3]);
        final String outputPath = args[4];
        final boolean[][] map;

        for (int i = 0; i < POPULATION_MAXIMUM; i++) indicesList.add(i);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(mapPath)));
            String[] worldDimensions = br.readLine().split("x");

            int rows = Integer.parseInt(worldDimensions[0]);
            int cols = Integer.parseInt(worldDimensions[1]);

            map = new boolean[rows][cols];

            for (int i = 0; i < rows; i++) {
                String[] mapRow = br.readLine().split("");
                for (int j = 0; j < cols; j++) {
                    map[i][j] = mapRow[j].equals("1");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<Solution> population = new ArrayList<>();

        int initialDepthMaximum = 6;


        for (int i = initialDepthMaximum; i > 1; i--) genUnitsAtDepth(i, population, POPULATION_MAXIMUM, map);

        Solution best = population.stream().max(Solution::compareTo).get();


        for (int generation = 0; generation < GENERATIONS_MAXIMUM; generation++) {
            List<Solution> newPopulation = new ArrayList<>();
            for (int i = 0; i < POPULATION_MAXIMUM; i++) {
                double value = rand.nextDouble();
                Solution child = null;
                Solution parent = select(population, 7);

                if (value < 0.85) child = crossOver(parent, select(population, 7));
                else if (value < 0.99) child = mutate(parent);
                else child = new Solution(parent.getRoot().clone());

                child.evaluate(map);
                if (child.getScore() > best.getScore()) best = child;
                else if (child.getScore() == best.getScore()) best = child.getNumberOfNodes() < best.getNumberOfNodes() ? child : best;
                else if (child.getScore() == parent.getScore() && child.getNumberOfNodes() < parent.getNumberOfNodes()) {
                    child.penalty();
                }

                newPopulation.add(child);
            }

            population.clear();
            population.addAll(newPopulation);
            population.remove(0);
            population.add(best);
            System.out.println("Generation :" + generation
                    + " Food eaten: " + best.getFoodEaten()
                    + " Number of nodes: " + best.getNumberOfNodes());
        }

        best.evaluate(map);
        Solution finalBest1 = best;
        SwingUtilities.invokeLater(() -> {
            WorldFrame worldFrame = new WorldFrame(map, finalBest1);
        });

    }

    private static void genUnitsAtDepth(int depth, List<Solution> population, int POPULATION_MAXIMUM, boolean[][] map) {
        Solution solution;
        for (int i = 0; i < POPULATION_MAXIMUM / 10; i++) {
            solution = new Solution(Util.genGrowNode(0, depth));
            solution.evaluate(map);
            population.add(solution);

            solution = new Solution(Util.genFullNode(0, depth));
            solution.evaluate(map);
            population.add(solution);
        }
    }

    private static Solution select(List<Solution> population, int numberOfParticipants) {
        Collections.shuffle(indicesList);
        Solution best = population.get(indicesList.get(0));
        int value = rand.nextInt();

        for (int i = 1; i < numberOfParticipants; i++) {
            Solution currentSolution = population.get(indicesList.get(i));
            if (value < 0.95) if (currentSolution.getScore() > best.getScore()) best = currentSolution;
            else if (currentSolution.getNumberOfNodes() < best.getNumberOfNodes()) best = currentSolution;

        }

        return best;
    }

    private static Solution crossOver(Solution parent1, Solution parent2) {


        INode root1 = parent1.getRoot().clone();
        INode root2 = parent2.getRoot().clone();


        List<INode> nodeList1 = root1.getSubTreeNodes();
        List<INode> nodeList2 = root2.getSubTreeNodes();

        int size1 = nodeList1.size();
        int size2 = nodeList2.size();

        if (size1 < 1 || size2 < 1) return new Solution(rand.nextDouble() < 0.5 ? Util.genGrowNode(0, rand.nextInt(7))
                : Util.genFullNode(0, rand.nextInt(7)));


        int i1 = rand.nextInt(size1);
        int i2 = rand.nextInt(size2);

        INode node = nodeList1.get(i1);
        NodeType type = node.getType();

        while(type != NodeType.Prog3 && type != NodeType.IfFoodAhead && type != NodeType.Prog2) {
            i1 = rand.nextInt(size1);
            node = nodeList1.get(i1);
            type = node.getType();
        }

        int depth = node.getDepth();

        if (type == NodeType.IfFoodAhead) {
            if (rand.nextDouble() < 0.5) ((IfFoodAhead) node).setFalse(nodeList2.get(i2));
            else ((IfFoodAhead) node).setTrue(nodeList2.get(i2));
        } else if (type == NodeType.Prog2) {
            if (rand.nextDouble() < 0.5) ((Prog2) node).setA(nodeList2.get(i2));
            else ((Prog2) node).setB(nodeList2.get(i2));
        } else {
            if (rand.nextDouble() < 1.0 / 3) ((Prog3) node).setA(nodeList2.get(i2));
            else if (rand.nextDouble() < 2.0 / 3) ((Prog3) node).setB(nodeList2.get(i2));
            else ((Prog3) node).setA(nodeList2.get(i2));
        }

        if (root1.countNodesInSubtree() > 199) return rand.nextDouble() < 0.5
                ? new Solution(parent1.getRoot().clone())
                : new Solution(parent2.getRoot().clone());
        root1.updateDepths(0);

        return new Solution(root1);
    }

    private static Solution mutate(Solution solution) {
        INode root = solution.getRoot().clone();
        root.updateDepths(0);

        List<INode> nodeList = root.getSubTreeNodes();
        int size = nodeList.size();

        if (size < 1) return new Solution(rand.nextDouble() < 0.5 ? Util.genGrowNode(0, rand.nextInt(7))
                : Util.genFullNode(0, rand.nextInt(7)));


        int index = rand.nextInt(size);

        INode node = nodeList.get(index);
        NodeType type = node.getType();

        int depth = node.getDepth();

        INode newNode = rand.nextDouble() < 0.5 ? Util.genFullNode(0, rand.nextInt(7))
                : Util.genFullNode(0, rand.nextInt(7));

        if (type == NodeType.IfFoodAhead) {
            if (rand.nextDouble() < 0.5) ((IfFoodAhead) node).setFalse(newNode);
            else ((IfFoodAhead) node).setTrue(newNode);
        } else if (type == NodeType.Prog2) {
            if (rand.nextDouble() < 0.5) ((Prog2) node).setA(newNode);
            else ((Prog2) node).setB(newNode);
        } else {
            if (rand.nextDouble() < 1.0 / 3) ((Prog3) node).setA(newNode);
            else if (rand.nextDouble() < 2.0 / 3) ((Prog3) node).setB(newNode);
            else ((Prog3) node).setA(newNode);
        }


        if (root.countNodesInSubtree() > 199) return new Solution(newNode);
        root.updateDepths(0);

        return new Solution(root);
    }
}
