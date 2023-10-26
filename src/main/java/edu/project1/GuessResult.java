package edu.project1;

import org.jetbrains.annotations.NotNull;

sealed interface GuessResult {
    Answer state();

    int attempt();

    int maxAttempts();

    @NotNull String message();

    record Defeat(Answer state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "You lost!";
        }
    }

    record Win(Answer state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "You won!";
        }
    }

    record SuccessfulGuess(Answer state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Hit!";
        }
    }

    record FailedGuess(Answer state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return String.format("Missed, mistake %d out of %d.", attempt(), maxAttempts());
        }
    }

    record Repeat(Answer state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "You have already entered this letter!";
        }
    }
}
