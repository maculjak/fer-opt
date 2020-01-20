package hr.fer.zemris.optjava.generic.ga.jobs;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.generic.ga.Solution;

public class Evaluate implements Runnable{

    private Evaluator evaluator;
    private GrayScaleImage image;

    public Evaluate(GrayScaleImage image) {
        this.image = image;
        this.evaluator = new Evaluator(image);
    }

    @Override
    public void run() {

    }
}
