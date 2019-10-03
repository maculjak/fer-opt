package hr.fer.zemris.trisat;

import java.io.*;

import java.util.Scanner;

public class TriSATSolver {
    public static void main (String[] args) {

        int algorithm;
        String filename;

        if(args.length != 2) {
            System.err.println("Invalid number of arguments, closing the program...");
            System.exit(1);
        }

        algorithm = Integer.parseInt(args[0]);
        filename = args[1];

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while(true) {
                String line = br.readLine();
                if(line.equals("%")) break;
                if(line.startsWith("c")) continue;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (algorithm) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            default:
                System.err.println("Invalid algorithm index, closing the program...");
                System.exit(1);
        }

    }
}
