package hr.fer.zemris.optjava.dz10;

public abstract class MOOPProblem {

    protected int numberOfObjectives;
    protected int numberOfArguments;
    protected double[] minArgumentBounds;
    protected double[] maxArgumentBounds;
    protected double[] minValueBounds;
    protected double[] maxValueBounds;

    public MOOPProblem(int numberOfObjectives, int numberOfArguments, double[] minArgumentBounds, double[] maxArgumentBounds
            , double[] minValueBounds, double[] maxValueBounds) {
        this.numberOfObjectives = numberOfObjectives;
        this.minArgumentBounds = minArgumentBounds;
        this.maxArgumentBounds = maxArgumentBounds;
        this.minValueBounds = minValueBounds;
        this.maxValueBounds = maxValueBounds;
        this.numberOfArguments = numberOfArguments;
    }

    public int getNumberOfObjectives(){
        return numberOfObjectives;
    }

    public int getNumberOfArguments() { return numberOfArguments; }

    public double[] getMinArgumentBounds() {
        return minArgumentBounds;
    }

    public double[] getMaxArgumentBounds() {
        return maxArgumentBounds;
    }

    public double[] getMinValueBounds() {
        return minValueBounds;
    }

    public double[] getMaxValueBounds() {
        return maxValueBounds;
    }

    public abstract void evaluateSolution(double[] solution, double[] objectives);
}
