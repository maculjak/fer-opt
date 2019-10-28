package hr.fer.zemris.optjava.dz4.part2;

import java.util.Objects;

public class Stick {

    private int height;
    private int index;

    public Stick(int height, int index) {
        this.height = height;
        this.index = index;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return index + " " + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stick stick = (Stick) o;

        return index == stick.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public Stick duplicate() {
        return new Stick(height, index);
    }
}
