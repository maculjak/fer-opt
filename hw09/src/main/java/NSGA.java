import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class NSGA {

    public static void solve(MOOPProblem problem, int MAXIMUM_ITERATIONS, int POPULATION_SIZE, String SHARING_SPACE) {
        double SIGMA_SHARE = 1.25;
        double ALPHA = 2;
        double[][] population = new double[POPULATION_SIZE][problem.getNumberOfObjectives()];
        double[][] values = new double[POPULATION_SIZE][problem.getNumberOfObjectives()];
        double[] fitnesses = new double[POPULATION_SIZE];
        double[] minArgumentBounds = problem.getMinArgumentBounds();
        double[] maxArgumentBounds = problem.getMaxArgumentBounds();
        double[] minValueBounds = problem.getMinValueBounds();
        double[] maxValueBounds = problem.getMaxValueBounds();

        int numberOfObjectives = problem.getNumberOfObjectives();
        Random rand = new Random();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < numberOfObjectives; j++) {
                population[i][j] = rand.nextDouble() * (maxArgumentBounds[j] - minArgumentBounds[j]) + minArgumentBounds[j];
            }
            problem.evaluateSolution(population[i], values[i]);
        }

        double[][] newPopulation = new double[POPULATION_SIZE][numberOfObjectives];

        for (int generation = 0; generation < MAXIMUM_ITERATIONS; generation++) {
            List<Set<Integer>> fronts = nonDominatedSort(population, values);
            double minFitness = POPULATION_SIZE;
            for (Set<Integer> front : fronts) {
                double frontFitness = minFitness * 0.99;
                for (int i : front) {
                    double nicheDensity = 0;
                    for (int j : front) {
                        double distance = SHARING_SPACE.equals("objective-space")
                                ? distance(values[i], values[j], minValueBounds, maxValueBounds)
                                : distance(population[i], population[j], minArgumentBounds, maxArgumentBounds);

                        nicheDensity += distance < SIGMA_SHARE
                                ? 1 - Math.pow(distance / SIGMA_SHARE, ALPHA)
                                : 0;
                    }
                    fitnesses[i] = minFitness / nicheDensity;
                    if (fitnesses[i] < frontFitness) frontFitness = fitnesses[i];
                }
                minFitness = frontFitness;
            }

            for (int i = 0; i < POPULATION_SIZE; i++) {
                int p1 = rouletteWheel(fitnesses);
                int p2 = rouletteWheel(fitnesses);
                double[] offspring = BLXAlpha(0.5, population[p1], population[p2]);
                offspring = mutate(offspring, 0.1, maxArgumentBounds, minArgumentBounds);
                newPopulation[i] = offspring;
                problem.evaluateSolution(newPopulation[i], values[i]);
            }

            population = newPopulation;
            int best = 0;
            for (int i = 0; i < POPULATION_SIZE; i++) if (fitnesses[i] > fitnesses[best]) best = i;
            System.out.print(generation + " ");
            Arrays.stream(population[best]).forEach(s -> System.out.print(s + " "));
            System.out.println();
        }

        try {
            BufferedWriter objectiveSpaceWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("izlaz-obj.csv")));
            BufferedWriter decisionSpaceWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("izlaz-dec.csv")));
            StringBuilder objectiveSpaceBuilder = new StringBuilder();
            StringBuilder decisionSpaceBuilder = new StringBuilder();

            for (int j = 0; j < population[0].length; j++) {
                objectiveSpaceBuilder.append("f").append(j + 1);
                decisionSpaceBuilder.append("x").append(j + 1);

                if (j != population[0].length - 1) {
                    objectiveSpaceBuilder.append(",");
                    decisionSpaceBuilder.append(",");
                }
            }

            objectiveSpaceBuilder.append("\n");
            decisionSpaceBuilder.append("\n");

            objectiveSpaceWriter.write(objectiveSpaceBuilder.toString());
            decisionSpaceWriter.write(decisionSpaceBuilder.toString());

            for (int i = 0; i < population.length; i++) {
                objectiveSpaceBuilder = new StringBuilder();
                decisionSpaceBuilder = new StringBuilder();

                for (int j = 0; j < population[0].length; j++) {
                    objectiveSpaceBuilder.append(String.format("%.4f", values[i][j]));
                    decisionSpaceBuilder.append(String.format("%.4f", population[i][j]));

                    if (j != population[0].length - 1) {
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

    private static List<Set<Integer>> nonDominatedSort(double[][] population, double[][] values) {
        List<Set<Integer>> dominates = new ArrayList<>();
        for (int i = 0; i < population.length; i++) dominates.add(new HashSet<>());
        int[] dominatedBy = new int[population.length];
        List<Set<Integer>> fronts = new ArrayList<>();
        Set<Integer> currentFront = new HashSet<>();
        fronts.add(currentFront);

        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < population.length; j++) {
                if (i == j) continue;
                if (dominates(values[i], values[j])) {
                    dominates.get(i).add(j);
                } else if (dominates(values[j], values[i])) {
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

    private static double distance(double[] solution1, double[] solution2
            , double[] minBounds, double[] maxBounds) {
        double distance = 0;

        for (int i = 0; i < solution1.length; i++) {
            double fraction = (solution1[i] - solution2[i]) /
                    (maxBounds[i] - minBounds[i]);
            distance += fraction * fraction;
        }

        return Math.sqrt(distance);
    }

    public static int rouletteWheel(double[] fitnesses) {
        Random rand = new Random();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < fitnesses.length; i++) indexes.add(i);
        indexes = indexes.stream()
                .sorted((i, j) -> Double.compare(fitnesses[j], fitnesses[i]))
                .collect(Collectors.toList());

        double minFitness = fitnesses[0];
        double sum = 0;
        double result = rand.nextDouble();

        for (int i : indexes) sum += fitnesses[i] - minFitness;

        double previousProbability = 0;

        for (int i : indexes) {
            double value = fitnesses[i];
            double probability = (value - minFitness) / sum;
            previousProbability += probability;

            if (result < previousProbability) return i;
        }
        return indexes.size() - 1;
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
}
