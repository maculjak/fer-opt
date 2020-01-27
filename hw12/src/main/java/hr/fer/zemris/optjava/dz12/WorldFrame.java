package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.Ant;
import hr.fer.zemris.optjava.Solution;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorldFrame extends JFrame {

    private boolean[][] map;
    private JPanel[][] cells;
    private JPanel cellPanel;
    private List<WorldListener> listeners;
    private Simulation simulation;
    private Ant ant;

    public WorldFrame(boolean[][] map, Solution solution) {
        this.map = map;
        this.cells = new JPanel[map.length][map[0].length];
        this.ant = new Ant(0, 0, 0, map.length, map[0].length, map);

        cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(map.length, map[0].length));
        add(cellPanel);
        listeners = new ArrayList<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(map[i][j] ? Color.GRAY  : Color.WHITE);
                cells[i][j] = cell;
                cell.setBorder(BorderFactory.createEtchedBorder());
                cellPanel.add(cells[i][j]);
            }
        }

        listeners.add(() -> SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    cells[i][j].setBackground(map[i][j] ? Color.GRAY  : Color.WHITE);
                }
            }
            int antRow = ant.getRow();
            int antCol = ant.getCol();
            cells[antRow][antCol].setBackground(Color.BLUE);
            cells[antRow][antCol].setBorder(BorderFactory.createEtchedBorder());
        }));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        simulation = new Simulation(this, solution);
        simulation.start();
    }

    public void updateCell(int row, int col, boolean value) {
        map[row][col] = value;
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


    public boolean[][] getMap() {
        return map;
    }
}
