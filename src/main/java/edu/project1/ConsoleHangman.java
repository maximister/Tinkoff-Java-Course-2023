package edu.project1;

import edu.project1.dictionary.GameDictionary;
import edu.project1.input_and_output.InputHandler;
import edu.project1.input_and_output.Printer;

class ConsoleHangman {
    private final Printer printer;
    private GameDictionary dictionary;
    private final Session session;
    private final InputHandler inputHandler;

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    ConsoleHangman(int maxAttempts) {
        if (maxAttempts <= 0) {
            throw  new IllegalArgumentException("Error: maxAttempts is a natural number!");
        }
        dictionary = new GameDictionary();
        this.inputHandler = new InputHandler();
        this.printer = new Printer();
        this.session = new Session(new Answer(dictionary.getRandomWord()), maxAttempts);

        this.run();
    }

    ConsoleHangman(int maxAttempts, String[] words) throws Exception {
        if (maxAttempts <= 0) {
            throw  new IllegalArgumentException("Error: maxAttempts is a natural number!");
        }
        dictionary = new GameDictionary(words);
        if (dictionary.isEmpty()) {
            throw new Exception("Error! Your dictionary is empty");
        }

        this.inputHandler = new InputHandler();
        this.printer = new Printer();
        this.session = new Session(new Answer(dictionary.getRandomWord()), maxAttempts);

        this.run();
    }

    private void run() {
        GuessResult result = null;
        printer.printMessage("Welcome to the Hangman game");
        printer.printMessage("If you want to give up, enter \"exit\"");

        while (true) {
            printer.printMessage("Guess a letter:");
            result = tryGuess();
            if (result instanceof GuessResult.Win || result instanceof GuessResult.Defeat) {
                break;
            }
        }
    }

    private GuessResult tryGuess() {
        GuessResult result;
        String exitCommand = "exit";
        String command = inputHandler.next();

        while (command.length() > 1 && !command.equals(exitCommand)) {
            printer.printMessage(command);
            printer.printMessage("Try again:");

            command = inputHandler.next();
        }
        if (command.equals(exitCommand)) {
            result = session.giveUp();
        } else {
            char letter = command.charAt(0);
            result = session.guess(letter);
        }

        printState(result);
        return result;

    }

    private void printState(GuessResult result) {
        printer.printMessage(result.message());
        printer.printMessage(session.getAnswer());
    }
}
