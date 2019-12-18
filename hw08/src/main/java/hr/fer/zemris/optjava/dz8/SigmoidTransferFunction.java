package hr.fer.zemris.optjava.dz8;

public class SigmoidTransferFunction implements ITransferFunction{

    public double output(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
