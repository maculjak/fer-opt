package hr.fer.zemris.optjava;

public class Ant {
    private int row;
    private int col;
    private int facingDirection;
    private final int rowBound;
    private final int colBound;
    private int score;
    private boolean[][] map;
    private int movesLeft;

    public Ant(int row, int col, int facingDirection, int rowBound, int colBound, boolean[][] map) {
        this.row = row;
        this.col = col;
        this.facingDirection = facingDirection;
        this.rowBound = rowBound;
        this.colBound = colBound;
        this.movesLeft = 600;
        this.map = map;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(int facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void rotateLeft() {
        if (movesLeft > 0) {
            facingDirection = facingDirection == 0 ? 3 : (facingDirection - 1) % 4;
            movesLeft--;
        }
    }

    public void rotateRight() {
        if (movesLeft > 0) {
            facingDirection = (facingDirection + 1) % 4;
            movesLeft--;
        }

    }

    public void move() {
        if (movesLeft > 0) {
            if (isFoodInFront()) score++;
            if (facingDirection == 0) incCol();
            else if (facingDirection == 1) incRow();
            else if (facingDirection == 2) decCol();
            else if (facingDirection == 3) decRow();
            movesLeft--;
            if (map[row][col]) map[row][col] = false;
        }
    }

    public void incRow() {
        row = (row + 1) % rowBound;
    }

    public void decRow() {
        row = row == 0 ? 31 : (row - 1) % 32;
    }

    public void incCol() {
        col = (col + 1) % colBound;
    }

    public void decCol() {
        col = col == 0 ? 31 : (col - 1) % 32;
    }

    public String getIndexInFront() {
        if (facingDirection == 0) return "C" + (col + 1) % colBound;
        else if (facingDirection == 1) return "R" + (row + 1) % 32;
        else if (facingDirection == 2) return "C" + (col == 0 ? 31 : (col - 1) % 32);
        else if (facingDirection == 3) return "R" + (row == 0 ? 31 : (row - 1) % 32);
        else return "";
    }

    public boolean isFoodInFront() {
        String indexInFront = getIndexInFront();
        char rowOrCol = indexInFront.charAt(0);
        int index = Integer.parseInt(indexInFront.substring(1));

        if (rowOrCol == 'R') return map[index][col];
        else return map[row][index];
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
