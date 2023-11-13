package edu.hw3.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MyBackwardIteratorTest {
    @ParameterizedTest
    @MethodSource("myBackwardIteratorTestArgs")
    @DisplayName("Testing iterator with different Collections")
    public void myBackwardIteratorTest(Collection collection) {
        collection.add(1);
        collection.add(2);
        collection.add(3);
        MyBackwardIterator iterator = new MyBackwardIterator(collection);

       assertThat(iterator.next()).isEqualTo(3);

       iterator.remove();
       assertThat(iterator.next()).isEqualTo(1);
       assertThat(iterator.next()).isEqualTo(null);
    }

    private static Stream<Arguments> myBackwardIteratorTestArgs() {
        return Stream.of(
            Arguments.of(new ArrayList<Integer>()),
            Arguments.of(new HashSet<Integer>()),
            Arguments.of(new TreeSet<Integer>()),
            Arguments.of(new PriorityQueue<Integer>())
        );
    }
}
