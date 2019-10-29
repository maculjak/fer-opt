package hr.fer.zemris.optjava.dz4.part2;

import java.util.*;
import java.util.function.Consumer;

public class Column implements Iterable<Stick> {

    private ArrayList<Stick> sticks;
    private int maxCapacity;
    private int capacity;

    public Column(int maxCapacity) {
        this.sticks = sticks = new ArrayList<>();
        this.capacity = 0;
        this.maxCapacity = maxCapacity;
    }

    public boolean isEmpty() {
        return sticks.isEmpty();
    }

    public boolean addStick(Stick stick) {
        if (stick.getHeight() + capacity > maxCapacity) return false;

        sticks.add(stick);
        updateCapacity();

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        return Objects.equals(sticks, column.sticks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sticks, getCapacity());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Stick stick : sticks) {
            sb.append(String.format("█", stick.getIndex()).repeat(Math.max(0, stick.getHeight() - 1)));
            sb.append("▒");
        }
        sb.append("░".repeat(maxCapacity - capacity));

        return sb.toString();
    }

    @Override
    public void forEach(Consumer<? super Stick> action) {
        sticks.forEach(action);
    }

    @Override
    public Spliterator<Stick> spliterator() {
        return sticks.spliterator();
    }

    @Override
    public Iterator<Stick> iterator() {
        return sticks.iterator();
    }

    public boolean containsStick(Stick s) {
        return sticks.contains(s);
    }

    public ArrayList<Stick> getSticks() {
        return sticks;
    }

    public boolean removeAllSticks (Collection<Stick> sticks) {
        boolean result = this.sticks.removeAll(sticks);
        updateCapacity();

        return result;
    }

    public Column duplicate() {
        Column column = new Column(this.maxCapacity);
        for (Stick s : sticks)
            column.addStick(s.duplicate());

        column.updateCapacity();

        return column;
    }

    public int getCapacity() {
        return capacity;
    }
    public void updateCapacity() {
        this.capacity = sticks.stream().mapToInt(Stick::getHeight).sum();
    }

    public int sumOfStickHeights() {
        return sticks.stream().mapToInt(Stick::getHeight).sum();
    }
}
