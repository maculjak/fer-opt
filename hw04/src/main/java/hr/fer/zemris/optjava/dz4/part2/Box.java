package hr.fer.zemris.optjava.dz4.part2;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Box implements Iterable<Column>{

    private LinkedList<Column> columns;
    private int numberOfSticks;
    private ArrayList<Stick> sticks;
    private int height;

    public Box(int numberOfSticks, ArrayList<Stick> sticks, int height) {
        this.columns = new LinkedList<>();
        this.numberOfSticks = numberOfSticks;
        this.sticks = sticks;
        this.height = height;
    }

    public Box duplicate() {
        ArrayList<Stick> sticksDuplicate = new ArrayList<>();
        for (Stick s : sticks) sticksDuplicate.add(s.duplicate());
        Box box = new Box(this.numberOfSticks, sticksDuplicate, this.height);

        for (Column column : columns) {
            Column c = column.duplicate();
            box.addColumn(c);
        }

        return box;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public boolean removeColumn(Column column) {
        return columns.remove(column);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        columns.forEach(column -> sb.append(column).append("\n"));

        return sb.toString();
    }

    public int getFill() {
        return columns.size();
    }

    public void randomize() {
        Random rand = new Random();
        Column[] allColumns = new Column[numberOfSticks];
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < numberOfSticks; i++) {
            allColumns[i] = new Column(height);
            indexes.add(i);
        }

        for (Stick stick : sticks) {
            ArrayList<Integer> temp = new ArrayList<>(indexes);
            int index = temp.get(rand.nextInt(temp.size()));

            while (!allColumns[index].addStick(stick)) {
                temp.remove(index);
                index = rand.nextInt(temp.size());
            }
        }

        for(int i = 0; i < allColumns.length; i++) {
            if(!allColumns[i].isEmpty()) this.addColumn(allColumns[i]);
        }
    }

    public List<Column> getColumnsBetweenIndexes(int index1, int index2) {
        List<Column> list = new LinkedList<>();

        for (int i = index1; i < index2; i++)
            list.add(columns.get(i).duplicate());

        return list;
    }

    @Override
    public void forEach(Consumer<? super Column> action) {
        columns.forEach(action);
    }

    @Override
    public Spliterator<Column> spliterator() {
        return columns.spliterator();
    }

    @Override
    public Iterator<Column> iterator() {
        return columns.iterator();
    }

    public void removeAllColumns(List<Column> columns) {
        this.columns.removeAll(columns);
    }

    public void sort() {
        columns = columns.stream()
                .sorted(Comparator.comparingInt(Column::getCapacity).reversed())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public int getHeight() {
        return height;
    }

    public Column get(int index) {
        return columns.get(index);
    }

    public void addAllColumns (Collection<Column> columns) {
        this.columns.addAll(columns);
    }

    public int sumOfStickHeights() {
        int sum = 0;

        for (Column c : columns)
            for (Stick s : c)
                sum += s.getHeight();

        return sum;
    }

    public void addStick(Stick s) {
        for (Column c : columns)
            if (c.addStick(s)) return;

        Column c = new Column(height);
        c.addStick(s);

        columns.add(c);
        sticks.add(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        return numberOfSticks == box.numberOfSticks &&
                height == box.height &&
                Objects.equals(columns, box.columns) &&
                Objects.equals(sticks, box.sticks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columns, numberOfSticks, sticks, height);
    }
}
