package edu.hw4.task19_20;

import edu.hw4.Animal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalErrorValidator {
    private final static List<ValidationError> ERROR_LIST = List.of(
        new ValidationError.HeightValidationError("Height must be natural number!"),
        new ValidationError.NameValidationError("Name must not be empty!"),
        new ValidationError.AgeValidationError("Age must be positive integer!"),
        new ValidationError.WeightValidationError("Weight must be natural numder!"),
        new ValidationError.SexValidationError("You live in Russia, so your animal can not be non-binary person!")
    );

    private AnimalErrorValidator() {
    }

    private static Set<ValidationError> checkAnimal(Animal animal) {
        return ERROR_LIST.stream()
            .filter(error -> !error.isValid(animal))
            .collect(Collectors.toSet());
    }

    public static Map<String, Set<ValidationError>> checkAnimals(List<Animal> animals) {
        return animals.stream()
            //validating animals
            .collect(Collectors.toMap(Animal::name, AnimalErrorValidator::checkAnimal))
            //deleting correct animals
            .entrySet().stream()
            .filter(e -> (!e.getValue().isEmpty()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, String> checkAnimalsWithReadableInput(List<Animal> animals) {
        return checkAnimals(animals).entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                (e) -> e.getValue().toString().replace("]", "").replace("[", "")
            ));
    }

}
