package hr.fer.zemris.optjava.dz3;

public interface ITempSchedule {
    double getNextTemperature();
    int getInnerLoopCounter();
    int getOuterLoopCounter();
    double getInitialTemperature();
}
