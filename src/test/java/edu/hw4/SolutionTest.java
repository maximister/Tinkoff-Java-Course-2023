package edu.hw4;

import edu.hw4.task19_20.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.hw4.Solution.countFatAnimals;
import static edu.hw4.Solution.doSpidersBitesMoreOftenThanDogs;
import static edu.hw4.Solution.getAmountOfAnimalsByTypes;
import static edu.hw4.Solution.getAnimalsWithLongFullName;
import static edu.hw4.Solution.getBitesTheDustList;
import static edu.hw4.Solution.getHeaviestAnimalLowerThanK;
import static edu.hw4.Solution.getHeaviestAnimalOfEachType;
import static edu.hw4.Solution.getHeaviestFish;
import static edu.hw4.Solution.getKHeaviestAnimals;
import static edu.hw4.Solution.getKthOldestAnimal;
import static edu.hw4.Solution.getListOfAnimalsWhoseAgeAreNotEqualToPaws;
import static edu.hw4.Solution.getLongestNameAnimal;
import static edu.hw4.Solution.getMostFrequentSex;
import static edu.hw4.Solution.getSumOfPaws;
import static edu.hw4.Solution.getTotalWeightOfAnimalsWithAgeBetweenKAndL;
import static edu.hw4.Solution.getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypes;
import static edu.hw4.Solution.hasDogHigherThanKInList;
import static edu.hw4.Solution.sortAnimalsByHeight;
import static edu.hw4.Solution.sortAnimalsByTypeSexAndName;
import static edu.hw4.task19_20.AnimalErrorValidator.checkAnimals;
import static edu.hw4.task19_20.AnimalErrorValidator.checkAnimalsWithReadableInput;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class SolutionTest {
    public static List<Animal> generateAnimalList() {
        Animal c = new Animal("Bites The Dust", Animal.Type.CAT, Animal.Sex.M, 4, 200, 20, true);
        Animal c2 = new Animal("Dora", Animal.Type.CAT, Animal.Sex.F, 16, 40, 50, true);
        Animal d = new Animal("Oleg", Animal.Type.DOG, Animal.Sex.F, 2, 37, 13, true);
        Animal d2 = new Animal("Nikitos", Animal.Type.DOG, Animal.Sex.M, 13, 120, 130, true);
        Animal b = new Animal("Sanechka", Animal.Type.BIRD, Animal.Sex.M, 4, 10, 1, false);
        Animal b2 = new Animal("Stopapupa", Animal.Type.BIRD, Animal.Sex.F, 63, 10, 111, true);
        Animal f = new Animal("Ivan Zolo", Animal.Type.FISH, Animal.Sex.M, 43, 20, 121, false);
        Animal f2 = new Animal("Shaylushay", Animal.Type.FISH, Animal.Sex.M, 23, 20, 20, true);
        Animal s = new Animal("Goshan", Animal.Type.SPIDER, Animal.Sex.F, 19, 2, 12, true);
        Animal s2 = new Animal("Jabrony", Animal.Type.SPIDER, Animal.Sex.M, 23, 180, 64, false);

        List<Animal> testList = List.of(c, c2, d, d2, b, b2, f, f2, s, s2);
        List<Animal> result = new ArrayList<>(testList);
        return result;
    }

    @Test
    @DisplayName("task 1 test")
    public void sortAnimalsByHeightTest() {
        List<Animal> testList = (generateAnimalList());
        List<Animal> result = sortAnimalsByHeight(testList);

        testList.sort(Comparator.comparingInt(Animal::height));

        assertThat(result).isEqualTo(testList);

    }

    @Test
    @DisplayName("task 2 test")
    public void getKHeaviestAnimalsTest() {
        List<Animal> testList = (generateAnimalList());
        List<Animal> result = getKHeaviestAnimals(testList, 2);

        testList.sort(Comparator.comparingInt(Animal::weight).reversed());
        List<Animal> expected = testList.subList(0, 2);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("task 2 invalid k test")
    public void getKHeaviestAnimalsWithInvalidKTest() {
        List<Animal> testList = (generateAnimalList());

        assertThrows(IllegalArgumentException.class, () ->  getKHeaviestAnimals(testList, -1));
    }

    @Test
    @DisplayName("task 3 test")
    public void getAmountOfAnimalsByTypesTest() {
        List<Animal> testList = (generateAnimalList());
        Map<Animal.Type, Integer> result = getAmountOfAnimalsByTypes(testList);

        Map<Animal.Type, Integer> expected = Map.of(
            Animal.Type.CAT, 2,
            Animal.Type.DOG, 2,
            Animal.Type.FISH, 2,
            Animal.Type.BIRD, 2,
            Animal.Type.SPIDER, 2);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("task 4 test")
    public void getLongestNameAnimalTest() {
        List<Animal> testList = (generateAnimalList());

        assertThat(getLongestNameAnimal(testList)).isEqualTo(testList.get(0));
    }

    @Test
    @DisplayName("task 5 test")
    public void getMostFrequentSexTest() {
        List<Animal> testList = (generateAnimalList());
        Animal.Sex result = getMostFrequentSex(testList);

        assertThat(result).isEqualTo(Animal.Sex.M);
    }

    @Test
    @DisplayName("task 6 test")
    public void getHeaviestAnimalOfEachTypeTest() {
        List<Animal> testList = (generateAnimalList());
        Map<Animal.Type, Animal> result = getHeaviestAnimalOfEachType(testList);

        Map<Animal.Type, Animal> expected = Map.of(
            Animal.Type.CAT, testList.get(1),
            Animal.Type.DOG, testList.get(3),
            Animal.Type.BIRD, testList.get(5),
            Animal.Type.FISH, testList.get(6),
            Animal.Type.SPIDER, testList.get(9)
        );

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("task 7 test")
    public void getKthOldestAnimalTest() {
        List<Animal> testList = (generateAnimalList());
        Animal result = getKthOldestAnimal(testList, 4);

        assertThat(result).isEqualTo(testList.get(3));
    }

    @Test
    @DisplayName("task 7 invalid k value test")
    public void getKthOldestAnimalWithInvalidKValueTest() {
        List<Animal> testList = (generateAnimalList());

        assertThrows(IllegalArgumentException.class, () -> getKthOldestAnimal(testList, 0));
        assertThrows(IllegalArgumentException.class, () -> getKthOldestAnimal(testList, 11));
    }

    @Test
    @DisplayName("task 8")
    public void getHeaviestAnimalLowerThanKTest() {
        List<Animal> testList = (generateAnimalList());
        Optional<Animal> result1 = getHeaviestAnimalLowerThanK(testList, 120);
        Optional<Animal> result2 = getHeaviestAnimalLowerThanK(testList, 1);

        Optional<Animal> expected1 = Optional.of(testList.get(6));
        Optional<Animal> expected2 = Optional.empty();

        assertThat(result1).isEqualTo(expected1);
        assertThat(result2).isEqualTo(expected2);
    }

    @Test
    @DisplayName("task 8 invalid k value test")
    public void getHeaviestAnimalLowerThanKInvalidKTest() {
        List<Animal> testList = (generateAnimalList());

        assertThrows(IllegalArgumentException.class, () -> getHeaviestAnimalLowerThanK(testList, 0));
    }

    @Test
    @DisplayName("task 9 test")
    public void getSumOfPawsTest() {
        List<Animal> testList = (generateAnimalList());
        Integer result = getSumOfPaws(testList);

        assertThat(result).isEqualTo(18 * 2);
    }

    @Test
    @DisplayName("task 10 test")
    public void getListOfAnimalsWhoseAgeAreNotEqualToPawsTest() {
        List<Animal> testList = (generateAnimalList());
        List<Animal> result = getListOfAnimalsWhoseAgeAreNotEqualToPaws(testList);

        testList.remove(0);
        assertThat(result).isEqualTo(testList);
    }

    @Test
    @DisplayName("task 11 test")
    public void getBitesTheDustListTest() {
        List<Animal> testList = (generateAnimalList());
        List<Animal> result = getBitesTheDustList(testList);

        List<Animal> expected = List.of(testList.get(0), testList.get(3));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("task 12 test")
    public void countFatAnimalsTest() {
        List<Animal> testList = (generateAnimalList());
        Integer result = countFatAnimals(testList);

        assertThat(result).isEqualTo(5);
    }

    @Test
    @DisplayName("task 13 test")
    public void getAnimalsWithLongFullNameTest() {
        List<Animal> testList = (generateAnimalList());
        List<Animal> result = getAnimalsWithLongFullName(testList);

        List<Animal> expected = List.of(testList.get(0));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("task 14 test")
    public void hasDogHigherThanKInListTest() {
        List<Animal> testList = (generateAnimalList());
        Boolean result1 = hasDogHigherThanKInList(testList, 50);
        Boolean result2 = hasDogHigherThanKInList(testList, 120);

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("task 14 with invalid K value test")
    public void hasDogHigherThanKInListWithInvalidKTest() {
        List<Animal> testList = (generateAnimalList());

        assertThrows(IllegalArgumentException.class, () -> hasDogHigherThanKInList(testList, 0));
    }

    @Test
    @DisplayName("task 15v1 test")
    public void getTotalWeightOfAnimalsWithAgeBetweenKAndLTest() {
        List<Animal> testList = (generateAnimalList());
        Integer result = getTotalWeightOfAnimalsWithAgeBetweenKAndL(testList, 1, 7);

        assertThat(result).isEqualTo(34);
    }

    @Test
    @DisplayName("task 15v1 with invalid k/l values test")
    public void getTotalWeightOfAnimalsWithAgeBetweenKAndLInvalidValuesTest() {
        List<Animal> testList = (generateAnimalList());

        assertThrows(IllegalArgumentException.class,
            () -> getTotalWeightOfAnimalsWithAgeBetweenKAndL(testList, -1, 7));
        assertThrows(IllegalArgumentException.class,
            () -> getTotalWeightOfAnimalsWithAgeBetweenKAndL(testList, 1, -1));
        assertThrows(IllegalArgumentException.class,
            () -> getTotalWeightOfAnimalsWithAgeBetweenKAndL(testList, 7, 0));
    }

    @Test
    @DisplayName("task 15v2 test")
    public void getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypesTest() {
        List<Animal> testList = (generateAnimalList());
        Map<Animal.Type, Integer> result =
            getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypes(testList, 1, 7);

        Map<Animal.Type, Integer> expected = Map.of(
            Animal.Type.CAT, 20,
            Animal.Type.DOG, 13,
            Animal.Type.BIRD, 1
        );

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("task 15v2 with invalid k/l values test")
    public void getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypesInvalidValuesTest() {
        List<Animal> testList = (generateAnimalList());

        assertThrows(IllegalArgumentException.class,
            () -> getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypes(testList, -1, 7));
        assertThrows(IllegalArgumentException.class,
            () -> getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypes(testList, 1, -1));
        assertThrows(IllegalArgumentException.class,
            () -> getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypes(testList, 7, 0));
    }

    @Test
    @DisplayName("task 16 test")
    public void sortAnimalsByTypeSexAndNameTest() {
        List<Animal> testList = (generateAnimalList());
        List<Animal> result = sortAnimalsByTypeSexAndName(testList);

        testList.sort(Comparator
            .comparing(Animal::type)
            .thenComparing(Animal::sex)
            .thenComparing(Animal::name)
        );

        assertThat(result).isEqualTo(testList);
    }

    @Test
    @DisplayName("task 17 test")
    public void doSpidersBitesMoreOftenThanDogsTest() {
        List<Animal> testList = (generateAnimalList());
        Boolean result = doSpidersBitesMoreOftenThanDogs(testList);

        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("task 18 test")
    public void getHeaviestFishTest() {
        List<Animal> testList = (generateAnimalList());
        Animal result = getHeaviestFish(testList);

        assertThat(result).isEqualTo(testList.get(6));
    }

    @ParameterizedTest
    @MethodSource("generateAnimalListWithInvalidElements")
    @DisplayName("task 19 test")
    public void checkAnimalsTest(List<Animal> testList) {
        Map<String, Set<ValidationError>> result = checkAnimals(testList);

        Map<String, Set<ValidationError>> expected = Map.of(
            "Bites The Dust", Set.of(new ValidationError.AgeValidationError("Age must be positive integer!")),
            "Andrew", Set.of(new ValidationError.HeightValidationError("Height must be natural number!")),
            "Dora", Set.of(new ValidationError.WeightValidationError("Weight must be natural number!")),
            " ", Set.of(new ValidationError.NameValidationError("Name must not be empty!")),
            "Nikitos", Set.of(new ValidationError.SexValidationError
                ("You live in Russia, so your animal can not be non-binary person!"))
        );

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("generateAnimalListWithInvalidElements")
    @DisplayName("task 20 test")
    public void checkAnimalsWithReadableInputTest(List<Animal> testList) {
        Map<String, String> result = checkAnimalsWithReadableInput(testList);

        Map<String, String> expected = Map.of(
            "Bites The Dust", "Age: " + "Age must be positive integer!",
            "Andrew", "Height: " + "Height must be natural number!",
            "Dora", "Weight: " + "Weight must be natural number!",
            " ", "Name: " + "Name must not be empty!",
            "Nikitos", "Sex: " + "You live in Russia, so your animal can not be non-binary person!"
        );

        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> generateAnimalListWithInvalidElements() {
        Animal c = new Animal("Bites The Dust", Animal.Type.CAT, Animal.Sex.M, -4, 200, 20, true);
        Animal c1 = new Animal("Andrew", Animal.Type.CAT, Animal.Sex.M, 4, -200, 20, true);
        Animal c2 = new Animal("Dora", Animal.Type.CAT, Animal.Sex.F, 16, 40, -50, true);
        Animal d = new Animal(" ", Animal.Type.DOG, Animal.Sex.F, 2, 37, 13, true);
        Animal d2 = new Animal("Nikitos", Animal.Type.DOG, null, 13, 120, 130, true);
        Animal b = new Animal("Sanechka", Animal.Type.BIRD, Animal.Sex.M, 4, 10, 1, false);

        List<Animal> testList = List.of(c, c1, c2, d, d2, b);

        return Stream.of(
            Arguments.of(new ArrayList<>(testList))
        );
    }
}
