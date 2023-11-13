package edu.hw3.task7;

import java.util.Comparator;

public class TreeMapNullComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null || o2 == null) {
            return -1;
        } else {
            return o1.compareTo(o2);
        }
    }
}
