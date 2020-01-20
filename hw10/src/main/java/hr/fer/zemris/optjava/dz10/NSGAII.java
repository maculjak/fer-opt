package hr.fer.zemris.optjava.dz10;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class NSGAII {

    public static void solve(MOOPProblem problem, int MAXIMUM_ITERATIONS, int POPULATION_SIZE) {
        List<Solution> population = new ArrayList<>();
        double[] minArgumentBounds = problem.getMinArgumentBounds();
        double[] maxArgumentBounds = problem.getMaxArgumentBounds();
        double[] minValueBounds = problem.getMinValueBounds();
        double[] maxValueBounds = problem.getMaxValueBounds();

        int numberOfObjectives = problem.getNumberOfObjectives();
        int numberOfArguments = problem.getNumberOfArguments();
        Random rand = new Random();

        List<Solution> newPopulation = new ArrayList<>();
        for (int i = 0; i <  POPULATION_SIZE; i++) {
            double[] objectives = new double[numberOfObjectives];
            for (int j = 0; j < numberOfArguments; j++) {
                objectives[j] = rand.nextDouble() * (maxArgumentBounds[j] - minArgumentBounds[j]) + minArgumentBounds[j];
            }
            double[] values = new double[numberOfObjectives];
            problem.evaluateSolution(objectives, values);
            population.add(new Solution(objectives, values));
        }

        List<Solution> familyPopulation = new ArrayList<>();

        for (int generation = 0; generation < MAXIMUM_ITERATIONS; generation++) {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                int p1 = select(population);
                int p2 = select(population);
                double[] offspring = BLXAlpha(0.5, population.get(p1).getArgumetns(), population.get(p2).getArgumetns());
                offspring = mutate(offspring, 0.3, maxArgumentBounds, minArgumentBounds);

                double[] values = new double[numberOfObjectives];
                problem.evaluateSolution(offspring, values);
                newPopulation.add(new Solution(offspring, values));
            }

            familyPopulation.addAll(population);
            familyPopulation.addAll(newPopulation);
            newPopulation.clear();

            List<Set<Integer>> fronts = nonDominatedSort(familyPopulation);
            int rank = 0;

            newPopulation.clear();
            List<List<Solution>> solutionFronts = new ArrayList<>();

            for (Set<Integer> front : fronts) {
                List<Solution> solutionFront = new ArrayList<>();
                for (int i : front) {
                    Solution solution = familyPopulation.get(i);
                    solution.setRank(rank);
                    solutionFront.add(familyPopulation.get(i));
                }
                solutionFronts.add(solutionFront);

                int frontSize = solutionFront.size();
                if (frontSize == 0) continue;

                for (int currentObjective = 0; currentObjective < numberOfObjectives; currentObjective++) {
                    double range = problem.getMaxArgumentBounds()[currentObjective] - problem.getMinArgumentBounds()[currentObjective];
                    solutionFront.sort(Solution.featureComparator(currentObjective));

                    solutionFront.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
                    solutionFront.get(solutionFront.size() - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

                    if (frontSize == 2) continue;

                    for (int i = 1; i < frontSize - 1; i++) {
                        double leftNeighbourValue = solutionFront.get(i - 1).getValues()[currentObjective];
                        double rightNeighbourValue = solutionFront.get(i + 1).getValues()[currentObjective];

                        solutionFront.get(i).addToCrowdingDistance((rightNeighbourValue - leftNeighbourValue) / range);
                    }
                }
                rank++;
            }

            for (List<Solution> front : solutionFronts) {
                if (front.size() + newPopulation.size() <= POPULATION_SIZE) {
                    newPopulation.addAll(front);
                    continue;
                }
                Collections.sort(front);

                int i = 0;
                while(newPopulation.size() < POPULATION_SIZE) {
                    newPopulation.add(front.get(i++));
                }
            }

            population.clear();
            population.addAll(newPopulation);
            newPopulation.clear();
            familyPopulation.clear();
            System.out.println("Generation: " + generation);
        }

        List<Set<Integer>> fronts = nonDominatedSort(population);
        for (int i = 0; i < fronts.size(); i++) {
            if (fronts.get(i).size() == 0) continue;
            System.out.println("In front " + i + " there are " + fronts.get(i).size() + " units");
        }

        try {
            BufferedWriter objectiveSpaceWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("izlaz-obj.csv")));
            BufferedWriter decisionSpaceWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("izlaz-dec.csv")));
            StringBuilder objectiveSpaceBuilder = new StringBuilder();
            StringBuilder decisionSpaceBuilder = new StringBuilder();

            for (int j = 0; j < numberOfObjectives; j++) {
                objectiveSpaceBuilder.append("f").append(j + 1);
                decisionSpaceBuilder.append("x").append(j + 1);

                if (j != numberOfObjectives - 1) {
                    objectiveSpaceBuilder.append(",");
                    decisionSpaceBuilder.append(",");
                }
            }

            objectiveSpaceBuilder.append("\n");
            decisionSpaceBuilder.append("\n");

            objectiveSpaceWriter.write(objectiveSpaceBuilder.toString());
            decisionSpaceWriter.write(decisionSpaceBuilder.toString());

            int populationSize = population.size();
            for (int i = 0; i < populationSize; i++) {
                objectiveSpaceBuilder = new StringBuilder();
                decisionSpaceBuilder = new StringBuilder();

                for (int j = 0; j < numberOfArguments; j++) {
                    objectiveSpaceBuilder.append(String.format("%.4f", population.get(i).getValues()[j]));
                    decisionSpaceBuilder.append(String.format("%.4f", population.get(i).getArgumetns()[j]));

                    if (j != numberOfObjectives - 1) {
                        objectiveSpaceBuilder.append(",");
                        decisionSpaceBuilder.append(",");
                    }
                }
                objectiveSpaceBuilder.append("\n");
                decisionSpaceBuilder.append("\n");
                objectiveSpaceWriter.write(objectiveSpaceBuilder.toString());
                decisionSpaceWriter.write(decisionSpaceBuilder.toString());
            }
            objectiveSpaceWriter.close();
            decisionSpaceWriter.close();

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private static List<Set<Integer>> nonDominatedSort(List<Solution> population) {
        List<Set<Integer>> dominates = new ArrayList<>();
        int populationSize = population.size();
        for (int i = 0; i < populationSize; i++) dominates.add(new HashSet<>());
        int[] dominatedBy = new int[populationSize];
        List<Set<Integer>> fronts = new ArrayList<>();
        Set<Integer> currentFront = new HashSet<>();
        fronts.add(currentFront);

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < populationSize; j++) {
                if (i == j) continue;
                if (dominates(population.get(i).getValues(), population.get(j).getValues())) {
                    dominates.get(i).add(j);
                } else if (dominates(population.get(j).getValues(), population.get(i).getValues())) {
                    dominatedBy[i]++;
                }
            }

            if (dominatedBy[i] == 0) {
                currentFront.add(i);
            }
        }

        while (!currentFront.isEmpty()) {
            Set<Integer> Q = new HashSet<>();
            for (int i : currentFront) {
                Set<Integer> currentDominatesSet = dominates.get(i);
                for (int j : currentDominatesSet) {
                    dominatedBy[j]--;
                    if (dominatedBy[j] == 0) Q.add(j);
               }
            }
            fronts.add(Q);
            currentFront = Q;
        }

        return fronts;
    }

    public static boolean dominates(double[] values1, double[] values2) {
        boolean betterInOneComponent = false;
        for (int i = 0; i < values1.length; i++) {
            if (values1[i] > values2[i]) return false;
            else if (values1[i] < values2[i]) betterInOneComponent = true;
        }
        return betterInOneComponent;
    }



    private static double[] BLXAlpha(double ALPHA, double[] parent1, double[] parent2) {
        double[] offspring = Arrays.copyOf(parent1, parent1.length);
        Random rand = new Random();
        for (int i = 0; i < parent1.length; i++) {

            double ci1 = parent1[i];
            double ci2 = parent2[i];
            double cimin = Math.min(ci1, ci2);
            double cimax = Math.max(ci1, ci2);

            double Ii = cimax - cimin;
            double lowerBound = cimin - Ii * ALPHA;
            double upperBound = cimax + Ii * ALPHA;

            offspring[i] = lowerBound + rand.nextDouble() * (upperBound - lowerBound);
        }

        return offspring;
    }

    public static double[] mutate(double[] chromosome, double s, double[] maxBounds, double[] minBounds) {
        Random rand = new Random();
        double[] mutatedChromosome = Arrays.copyOf(chromosome, chromosome.length);

        for (int i = 0; i < chromosome.length; i++) {
            mutatedChromosome[i] = chromosome[i] + rand.nextGaussian() * s;
            if (mutatedChromosome[i] > maxBounds[i]) mutatedChromosome[i] = maxBounds[i];
            else if (mutatedChromosome[i] < minBounds[i]) mutatedChromosome[i] = minBounds[i];
        }

        return mutatedChromosome;
    }

    public static int select(List<Solution> population) {
        Random rand = new Random();
        int populationSize = population.size();
        int index1 = rand.nextInt(populationSize);
        int index2 = rand.nextInt(populationSize);
        while(index1 == index2) index2 = rand.nextInt(populationSize);
        return population.get(index1).compareTo(population.get(index2)) == 1 ? index1 : index2;
    }
}
