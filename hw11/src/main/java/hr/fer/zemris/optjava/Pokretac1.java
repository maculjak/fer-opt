package hr.fer.zemris.optjava;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.Evaluator;
import hr.fer.zemris.optjava.generic.ga.Solution;
import hr.fer.zemris.optjava.generic.ga.algorithms.PEGA;

import java.io.*;

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
            Solution best = PEGA.solve(POPULATION_MAXIMUM, GENERATIONS_MAXIMUM, FITNESS_MINIMUM, RECTANGLES, image);
            GrayScaleImage imageToDraw = new Evaluator(image).draw(best, new GrayScaleImage(image.getWidth(), image.getHeight()));
            imageToDraw.save(new File(DEST_IMAGE_PATH));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DEST_PARAMS_PATH)));
            int[] data = best.getData();
            for (int i = 0; i < data.length; i++) {
                bw.write(data[i] + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
