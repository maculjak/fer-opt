package hr.fer.zemris.optjava.dz3;

public interface IDecoder<T> {

    double[] decode(T solution);
    void decode(T solution, double[] array);
}
