package edu.hw3.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TreeMapNullComparatorTest {
    @Test
    @DisplayName("Test with different keys")
    public void nullComparatorTest() {
        TreeMap<String, String> tree = new TreeMap<>(new TreeMapNullComparator<>());
        tree.put(null, "test");
        tree.put(null, "test2");
        tree.put("key1", null);

        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.get(null)).isEqualTo("test2");
        assertThat(tree.get("key1")).isEqualTo(null);
    }
}
