package edu.project1.dictionary;

import java.util.ArrayList;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class GameDictionary implements Dictionary {
    private final ArrayList<String> dictionary;
    private final int size;
    private Random random = new Random();

    public GameDictionary() {
        dictionary = new ArrayList<>();
        dictionary.add("miserable");
        dictionary.add("despair");
        dictionary.add("astenia");
        dictionary.add("amerima");
        dictionary.add("sushi");
        dictionary.add("blitzkrieg");
        dictionary.add("tinkoff");
        dictionary.add("oguzok");

        size = dictionary.size();
    }

    public GameDictionary(String[] words) {
        dictionary = new ArrayList<>();
        for (String word : words) {
            if (isWord(word)) {
                dictionary.add(word.toLowerCase());
            }
        }

        size = dictionary.size();
    }

    private boolean isWord(String word) {
        if (word.isEmpty()) {
            return false;
        }

        boolean flag = true;
        for (char letter : word.toCharArray()) {
            if (!Character.isLetter(letter)) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    public ArrayList<String> getDictionary() {
        return dictionary;
    }

    @Override
    public @NotNull String getRandomWord() {
        return dictionary.get(random.nextInt(size));
    }
}
