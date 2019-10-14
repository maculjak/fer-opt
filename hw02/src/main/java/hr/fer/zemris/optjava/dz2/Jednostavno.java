package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static hr.fer.zemris.optjava.dz2.PointUtilities.easyCreatePoint;

public class Jednostavno {

    public static void main(String[] args) {

        ArrayList<Array2DRowRealMatrix> solutions = null;

        if (args.length == 2) {
            switch(args[0]) {
                case "1a":
                    solutions = NumOptAlgorithms.gradientDescent(new Function1(), Integer.parseInt(args[1]), -5, 5);
                    break;
                case "1b":
                    solutions = NumOptAlgorithms.newtonsMethod(new Function1(), Integer.parseInt(args[1]), -5, 5);
                    break;
                case "2a":
                    solutions = NumOptAlgorithms.gradientDescent(new Function2(), Integer.parseInt(args[1]), -5, 5);
                    break;
                case "2b":
                    solutions = NumOptAlgorithms.newtonsMethod(new Function2(), Integer.parseInt(args[1]), -5, 5);
                    break;
                default:
                    System.err.println("Invalid argument. Closing the program...");
                    System.exit(1);
            }
        } else if (args.length == 4) {
            switch (args[0]) {
                case "1a":
                    solutions = NumOptAlgorithms.gradientDescent(new Function1(), Integer.parseInt(args[1]), easyCreatePoint(Double.parseDouble(args[2]), Double.parseDouble(args[3])));
                    break;
                case "1b":
                    solutions = NumOptAlgorithms.newtonsMethod(new Function1(), Integer.parseInt(args[1]), easyCreatePoint(Double.parseDouble(args[2]), Double.parseDouble(args[3])));
                    break;
                case "2a":
                    solutions = NumOptAlgorithms.gradientDescent(new Function2(), Integer.parseInt(args[1]), easyCreatePoint(Double.parseDouble(args[2]), Double.parseDouble(args[3])));
                    break;
                case "2b":
                    solutions = NumOptAlgorithms.newtonsMethod(new Function2(), Integer.parseInt(args[1]), easyCreatePoint(Double.parseDouble(args[2]), Double.parseDouble(args[3])));
                    break;
                default:
                    System.err.println("Invalid argument. Closing the program...");
                    System.exit(1);
            }
        } else {
            System.err.println("Invalid number of arguments. Closing the program...");
            System.exit(1);
        }

        DefaultXYDataset ds = new DefaultXYDataset();
        double[][] data = new double[2][solutions.size()];
        int i = 0;

        for(Array2DRowRealMatrix solution : solutions) {
            System.out.println("Step " + i);
            PointUtilities.printPoint(solution);
            data[0][i] = solution.getEntry(0, 0);
            data[1][i++] = solution.getEntry(1, 0);
        }

        ds.addSeries(args[0], data);

        JFreeChart chart = ChartFactory.createXYLineChart("Trajectory - " + args[0], "x1", "x2", ds, PlotOrientation.VERTICAL, false, true, false);

        JFrame chartFrame = new JFrame();
        chartFrame.setSize(1280, 720);

        ChartPanel cp = new ChartPanel(chart);
        chartFrame.add(cp);
        chartFrame.setVisible(true);

        BufferedImage bi = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);

        Graphics g = bi.createGraphics();
        cp.printAll(g);
        g.dispose();

        try {
            ImageIO.write(bi, "png", new File(args[0] + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
