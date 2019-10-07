package hr.fer.zemris.optjava.dz2;
import java.util.List;

import java.util.ArrayList;

public class Tuple<T> {

    private List<T> list;

    public Tuple(T...elements) {
        list = new ArrayList<>(elements.length);
        for(T t : elements) list.add(t);
    }

    public Tuple(int len) {
        list = new ArrayList<>(len);
        for(int i=0; i<len; i++) list.add(null);
    }

    public int len() {
        return list.size();
    }

    public T get(int index) {
        return list.get(index);
    }

    public void set(int index, T value) {
        list.set(index, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for(int i=0; i<list.size(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(list.get(i).toString());
        }
        sb.append(')');
        return sb.toString();
    }
}