package hr.fer.zemris.optjava.GUI;

import hr.fer.zemris.optjava.dz12.Ant;
import hr.fer.zemris.optjava.dz12.Solution;
import hr.fer.zemris.optjava.dz12.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorldFrame extends JFrame {

    private boolean[][] map;
    private JPanel[][] cells;
    private List<WorldListener> listeners;
    private Ant ant;
    private JButton startButton;
    private JButton restartButton;
    private SimulationThread thread;

    public WorldFrame(boolean[][] map, Solution solution) {
        this.map = Util.copy2DArray(map);
        this.cells = new JPanel[map.length][map[0].length];
        this.ant = new Ant(0, 0, 0, map.length, map[0].length, this.map);
        this.setLayout(new BorderLayout());

        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(map.length, map[0].length));
        add(cellPanel, BorderLayout.CENTER);

        startButton = new JButton("Start simulation");
        restartButton = new JButton("Reset simulation");

        JPanel buttons = new JPanel();
        buttons.add(startButton);
        buttons.add(restartButton);
        restartButton.setVisible(false);

        this.add(buttons, BorderLayout.SOUTH);

        listeners = new ArrayList<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(map[i][j] ? Color.GRAY  : Color.WHITE);
                cells[i][j] = cell;
                cell.setBorder(BorderFactory.createEtchedBorder());
                cellPanel.add(cells[i][j]);
            }
            int antRow = ant.getRow();
            int antCol = ant.getCol();
            cells[antRow][antCol].setBackground(Color.BLUE);
            cells[antRow][antCol].setBorder(BorderFactory.createEtchedBorder());
        }



        listeners.add(() -> SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < this.map.length; i++) {
                for (int j = 0; j < this.map[0].length; j++) {
                    cells[i][j].setBackground(this.map[i][j] ? Color.GRAY  : Color.WHITE);
                }
            }
            int antRow = ant.getRow();
            int antCol = ant.getCol();
            cells[antRow][antCol].setBackground(Color.BLUE);
            cells[antRow][antCol].setBorder(BorderFactory.createEtchedBorder());
        }));

        Queue<Boolean> queue = new LinkedBlockingQueue<>();


        startButton.addActionListener((l) -> {
            solution.setRun(true);
            this.thread = new SimulationThread(solution, this);
            thread.start();
            startButton.setVisible(false);
            restartButton.setVisible(true);
        });

        restartButton.addActionListener((l) -> {
            this.thread.setRun(false);
            solution.setRun(false);
            this.map = Util.copy2DArray(map);
            this.ant = new Ant(0, 0, 0, map.length, map[0].length, this.map);
            updateFrame();
            startButton.setVisible(true);
            restartButton.setVisible(false);
        });


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    public void moveAnt() {
        ant.move();
        int antRow = ant.getRow();
        int antCol = ant.getCol();
        if (map[antRow][antCol]) map[antRow][antCol] = false;
        updateFrame();
    }

    public void rotateAnt(String direction) {
        if (direction.equals("R")) ant.rotateRight();
        else ant.rotateLeft();

    }

    public boolean isFoodInFrontOfAnt() {
        String indexInFront = ant.getIndexInFront();
        char rowOrCol = indexInFront.charAt(0);
        int index = Integer.parseInt(indexInFront.substring(1));

        if (rowOrCol == 'R') return map[index][ant.getCol()];
        else return map[ant.getRow()][index];
    }

    public void updateFrame() {
        listeners.forEach(WorldListener::worldChanged);
    }
}
