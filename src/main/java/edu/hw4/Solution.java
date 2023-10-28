package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {
    private Solution() {
    }

    //task1
    public static List<Animal> sortAnimalsByHeight(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparingInt(Animal::height)).toList();
    }

    //task2
    public static List<Animal> getKHeaviestAnimals(List<Animal> animals, int k) {
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
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))));
    }

    //task7

    /*public static void main(String[] args) {
        Animal c = new Animal("Dora", Animal.Type.CAT, Animal.Sex.F, 5, 30, 20, false);
        Animal d = new Animal("Oleg", Animal.Type.DOG, Animal.Sex.F, 2, 37, 13, true);
        Animal p = new Animal("Sanechka", Animal.Type.BIRD, Animal.Sex.M, 4, 10, 1, false);
        Animal f = new Animal("Ivan Zolo", Animal.Type.FISH, Animal.Sex.M, 43, 20, 121, false);
        Animal s = new Animal("Goshan", Animal.Type.SPIDER, Animal.Sex.M, 19, 2, 12, true);

        List<Animal> testList = List.of(c, d, p, f, s);

        System.out.println(getHeaviestAnimalOfEachType(testList).toString());
    }*/
}
