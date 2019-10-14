package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public interface IHFunction extends IFunction{

    Array2DRowRealMatrix getHessian(Array2DRowRealMatrix point);
}
