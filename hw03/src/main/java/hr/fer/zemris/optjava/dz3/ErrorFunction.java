package hr.fer.zemris.optjava.dz3;

public class ErrorFunction implements IFunction {

    private double[][] x;
    private double[] y;
    private int numberOfVariables;

    public ErrorFunction(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getNumberOfVariables() {
        return 6;
    }

    public double getValue(double[] point) {
        double value = 0;
        for(int i = 0; i < x.length; i++) value += Math.pow(f(point, i) - y[i], 2);
        return value / x.length;
    }

    private double f(double[] point, int i) {
        double x1, x2, x3, x4, x5;
        double a, b, c, d, e, f;

        x1 = x[i][0];
        x2 = x[i][1];
        x3 = x[i][2];
        x4 = x[i][3];
        x5 = x[i][4];

        a = point[0];
        b = point[1];
        c = point[2];
        d = point[3];
        e = point[4];
        f = point[5];

        return a*x1 + b*x1*x1*x1*x2 + c*Math.exp(d*x3)*(1 + Math.cos(e*x4)) + f*x4*x5*x5;
    }
}
