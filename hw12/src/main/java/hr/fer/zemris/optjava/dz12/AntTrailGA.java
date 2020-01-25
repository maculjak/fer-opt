package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.Tree;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AntTrailGA {

    public static void main(String[] args) {

        String mapPath = args[0];
        int GENERATIONS_MAXIMUM = Integer.parseInt(args[1]);
        int POPULATION_MAXIMUM = Integer.parseInt(args[2]);
        double FITNESS_MINIMUM = Double.parseDouble(args[3]);
        String outputPath = args[4];

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(mapPath)));
            String[] worldDimensions = br.readLine().split("x");

            int rows = Integer.parseInt(worldDimensions[0]);
            int cols = Integer.parseInt(worldDimensions[1]);

            boolean[][] map = new boolean[rows][cols];

            for (int i = 0; i < rows; i++) {
                String[] mapRow = br.readLine().split("");
                for (int j = 0; j < cols; j++) {
                    map[i][j] = mapRow[j].equals("1");
                }
            }

            SwingUtilities.invokeLater(() -> {
                WorldFrame worldFrame = new WorldFrame(map);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
