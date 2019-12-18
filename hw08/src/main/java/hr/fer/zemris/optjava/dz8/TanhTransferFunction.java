package hr.fer.zemris.optjava.dz8;

public class TanhTransferFunction extends SigmoidTransferFunction {

    @Override
    public double output(double x) {
        return super.output(x) * 2 - 1;
    }
}
