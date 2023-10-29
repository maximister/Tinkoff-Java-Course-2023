package edu.hw4;

import com.google.common.primitives.Ints;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;


//solutions for tasks 1 - 18
public class Solution {
    private static final String INVALID_ARGUMENT_MESSAGE = "Invalid value of K";
    private static final String INVALID_ARGUMENTS_MESSAGE = "K and L must be bigger or equal to 0!";
    private static final int VERY_DANGEROUS_ANIMAL_HEIGHT = 100;

    private Solution() {
    }

    //task1
    public static List<Animal> sortAnimalsByHeight(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparingInt(Animal::height)).toList();
    }

    //task2
    public static List<Animal> getKHeaviestAnimals(List<Animal> animals, int k) {
        if (k < 0) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
        }
        return animals.stream().sorted(Comparator.comparingInt(Animal::weight).reversed()).limit(k).toList();
    }

    //task3
    public static Map<Animal.Type, Integer> getAmountOfAnimalsByTypes(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(i -> 1)));
    }

    //task4
    public static Animal getLongestNameAnimal(List<Animal> animals) {
        return animals.stream().max(Comparator.comparingInt(a -> a.name().length())).get();
    }

    //task5
    public static Animal.Sex getMostFrequentSex(List<Animal> animals) {
        int ans = animals.stream()
            .reduce(0, (sum, a) -> ((a.sex() == Animal.Sex.M) ? sum + 1 : sum - 1), Integer::sum);
        return ans >= 0 ? Animal.Sex.M : Animal.Sex.F;
    }

    //task6
    public static Map<Animal.Type, Animal> getHeaviestAnimalOfEachType(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(Animal::type, Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))
            ));
    }

    //task7
    public static Animal getKthOldestAnimal(List<Animal> animals, int k) {
        if (k <= 0 || k > animals.size()) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
        }
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age))
            .skip(k - 1)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    //task8
    public static Optional<Animal> getHeaviestAnimalLowerThanK(List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("K must be bigger than 0");
        }

        return animals.stream()
            .filter(a -> a.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    //task9
    public static Integer getSumOfPaws(List<Animal> animals) {

        return animals.stream().mapToInt(Animal::paws).sum();
    }

    //task10
    public static List<Animal> getListOfAnimalsWhoseAgeAreNotEqualToPaws(List<Animal> animals) {
        return animals.stream().filter(a -> (a.age() != a.paws())).toList();
    }

    //task11
    public static List<Animal> getBitesTheDustList(List<Animal> animals) {
        /*
        (bites == null или true)
        этот пункт не совсем понял, bites же примив, какой еще null
         */
        return animals.stream().filter((a) -> (a.bites() && a.height() > VERY_DANGEROUS_ANIMAL_HEIGHT)).toList();
    }

    //task12
    public static Integer countFatAnimals(List<Animal> animals) {
        return Ints.checkedCast(animals.stream().filter((a) -> (a.height() < a.weight())).count());
    }

    //task13
    public static List<Animal> getAnimalsWithLongFullName(List<Animal> animals) {
        return animals.stream().filter((a) -> (a.name().split(" ").length > 2)).toList();
    }

    //task14
    public static Boolean hasDogHigherThanKInList(List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
        }
        return animals.stream().anyMatch((a) -> (a.height() > k));
    }

    //task15
    /*
    Найти суммарный вес животных каждого вида, которым от k до l лет -> Integer
    Не совсем понял условие про каждый вид - нужно просто суммировать вес всех животных
    или нужно найти суммарный вес для каждого вида? Судя по требуемому типу Integer,
    нужен 1 вариант, судя по формулировке задачи - второй
    Сделал оба варианта)
     */
    public static Integer getTotalWeightOfAnimalsWithAgeBetweenKAndL(List<Animal> animals, int k, int l) {
        if (k < 0 || l < 0 || l < k) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS_MESSAGE);
        }

        return animals.stream().filter((a) -> (a.age() >= k && a.age() <= l)).mapToInt(Animal::weight).sum();
    }

    //task15v2
    public static Map<Animal.Type, Integer> getTotalWeightOfAnimalsWithAgeBetweenKAndLByTypes(
        List<Animal> animals,
        int k,
        int l
    ) {
        if (k < 0 || l < 0 || l < k) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS_MESSAGE);
        }

        return animals.stream().filter((a) -> (a.age() >= k && a.age() <= l))
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    //task16
    /*
    Список животных, отсортированный по виду, затем по полу, затем по имени -> List<Integer>
    не пон, почему Integer, заменил на Animal
     */
    public static List<Animal> sortAnimalsByTypeSexAndName(List<Animal> animals) {
        return animals.stream().sorted(
            Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name)
        ).toList();
    }

    //task17
    public static Boolean doSpidersBitesMoreOftenThanDogs(List<Animal> animals) {
        long spidersBitesAmount = animals.stream()
            .filter((a) -> (a.type() == Animal.Type.SPIDER && a.bites())).count();
        long dogsBitesAmount = animals.stream()
            .filter((a) -> (a.type() == Animal.Type.DOG && a.bites())).count();

        return spidersBitesAmount >= dogsBitesAmount;
    }

    //task18
    @SafeVarargs
    public static Animal getHeaviestFish(List<Animal>... animals) {
        return Arrays.stream(animals).flatMap(List::stream)
            .filter((a) -> (a.type() == Animal.Type.FISH))
            .max(Comparator.comparingInt(Animal::weight)).orElseThrow();
    }
}
