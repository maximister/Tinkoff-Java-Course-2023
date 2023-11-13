package edu.hw3.task8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MyBackwardIterator<T> implements Iterator<T> {
    private int pointer = 0;
    private final ArrayList<T> iterableObject;

    public MyBackwardIterator(Collection<T> collection) {
        iterableObject = new ArrayList<>(collection);
        pointer = collection.size() - 1;
    }

    @Override
    public void remove() {
        iterableObject.remove(pointer--);
    }

    @Override
    public boolean hasNext() {
        return (pointer >= 0);
    }

    @Override
    public T next() {
        if (hasNext()) {
            return iterableObject.get(pointer--);
        } else {
            return null;
        }
    }
}
