package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

public interface IHFunction extends IFunction{

    RealMatrix getHessian(Tuple<Double> point);
}
