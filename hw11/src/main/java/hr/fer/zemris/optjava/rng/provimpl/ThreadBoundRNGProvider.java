package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

public class ThreadBoundRNGProvider implements IRNGProvider {

    public IRNG getRNG() {
        return ((IRNGProvider)Thread.currentThread()).getRNG();
    }
}
