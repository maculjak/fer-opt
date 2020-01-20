package hr.fer.zemris.optjava;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.algorithms.PEGA;

import java.io.File;
import java.io.IOException;

public class Pokretac1 {

    public static void main(String[] args) {
        final String SRC_IMAGE_PATH = args[0];
        final int RECTANGLES = Integer.parseInt(args[1]);
        final int POPULATION_MAXIMUM = Integer.parseInt(args[2]);
        final int GENERATIONS_MAXIMUM = Integer.parseInt(args[3]);
        final double FITNESS_MINIMUM = Double.parseDouble(args[4]);
        final String DEST_PARAMS_PATH = args[5];
        final String DEST_IMAGE_PATH = args[6];
        GrayScaleImage image = null;

        try {
            image = GrayScaleImage.load(new File(SRC_IMAGE_PATH));
            PEGA.solve(POPULATION_MAXIMUM, GENERATIONS_MAXIMUM, FITNESS_MINIMUM, RECTANGLES, image);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        PEGA.solve(POPULATION_MAXIMUM, GENERATIONS_MAXIMUM, FITNESS_MINIMUM, RECTANGLES, image);

    }
}
