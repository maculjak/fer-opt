package hr.fer.zemris.optjava.dz6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TSPFileParser {

    World world;

    public TSPFileParser(String file) {
        try {
            System.out.println(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)));

            world = new World();

            while (true) {
                String line = br.readLine();
                if (line.equals("EOF")) break;
                if (line.matches("[0-9]+\\s.*")) {
                    String[] coordinates = line.split(" ");
                    world.addCity(new City(Double.parseDouble(coordinates[1])
                            , Double.parseDouble(coordinates[2])
                            , Integer.parseInt(coordinates[0]) - 1));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public World getWorld() {
        return world;
    }
}
