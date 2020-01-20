public abstract class MOOPProblem {

    protected int numberOfObjectives;
    protected double[] minArgumentBounds;
    protected double[] maxArgumentBounds;
    protected double[] minValueBounds;
    protected double[] maxValueBounds;

    public MOOPProblem(int numberOfObjectives, double[] minArgumentBounds, double[] maxArgumentBounds
            , double[] minValueBounds, double[] maxValueBounds) {
        this.numberOfObjectives = numberOfObjectives;
        this.minArgumentBounds = minArgumentBounds;
        this.maxArgumentBounds = maxArgumentBounds;
        this.minValueBounds = minValueBounds;
        this.maxValueBounds = maxValueBounds;
    }

    public int getNumberOfObjectives(){
        return numberOfObjectives;
    }

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
