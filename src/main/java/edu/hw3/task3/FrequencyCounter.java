package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;

public class FrequencyCounter {
    private FrequencyCounter() {
    }

    public static <T> HashMap<T, Integer> getFrequencyDict(List<T> objects) {
        checkList(objects);

        HashMap<T, Integer> resultMap = new HashMap<>();
        for (T object : objects) {
            if (!resultMap.containsKey(object)) {
                resultMap.put(object, 1);
            } else {
                resultMap.compute(object, (key, value) -> value + 1);
            }
        }

        return resultMap;
    }

    private static <T> void checkList(List<T> objects) {
        if (objects == null) {
            throw new NullPointerException("List must not be null!");
        }
        if (objects.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty!");
        }
    }
}
