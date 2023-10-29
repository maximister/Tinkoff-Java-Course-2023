package edu.hw4.task19_20;

import edu.hw4.Animal;

public interface ValidationError {

    boolean isValid(Animal animal);

    record NameValidationError(String message) implements ValidationError {
        @Override
        public boolean isValid(Animal animal) {
            return (!(animal.name() == null || animal.name().isEmpty() || animal.name().isBlank()));
        }

        @Override
        public String toString() {
            return "Name: " + message;
        }
    }

    record HeightValidationError(String message) implements ValidationError {
        @Override
        public boolean isValid(Animal animal) {
            return animal.height() > 0;
        }

        @Override
        public String toString() {
            return "Height: " + message;
        }
    }

    record WeightValidationError(String message) implements ValidationError {
        @Override
        public boolean isValid(Animal animal) {
            return animal.weight() > 0;
        }

        @Override
        public String toString() {
            return "Weight: " + message;
        }
    }

    record AgeValidationError(String message) implements ValidationError {
        @Override
        public boolean isValid(Animal animal) {
            return animal.age() >= 0;
        }

        @Override
        public String toString() {
            return "Age: " + message;
        }
    }

    record SexValidationError(String message) implements ValidationError {
        @Override
        public boolean isValid(Animal animal) {
            return (animal.sex() != null);
        }

        @Override
        public String toString() {
            return "Sex: " + message;
        }
    }
}
