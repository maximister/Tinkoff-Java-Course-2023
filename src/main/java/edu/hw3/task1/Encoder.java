package edu.hw3.task1;

final class Encoder {
    private Encoder() {
    }

    public static String encodeAtbash(String input) {
        if (input == null) {
            throw new NullPointerException("your string must not be null!");
        }

        /*
        Если вдруг проблема не в кириллице....
        (осуждаю, я мирный добрый хороший)
        if (input.toUpperCase().contains("JEW")) {
            throw new IllegalArgumentException();
        }
        */

        StringBuilder encodedString = new StringBuilder();

        for (char symbol : input.toCharArray()) {
            char encodedLetter;
            if (Character.isLetter(symbol)) {

                if (!isLatinLetter(symbol)) {
                    throw new IllegalArgumentException("Please use only latin letters");
                }

                if (Character.isLowerCase(symbol)) {
                    encodedLetter = (char) ('z' - (symbol - 'a'));
                } else {
                    encodedLetter = (char) ('Z' - (symbol - 'A'));
                }
            } else {
                encodedLetter = symbol;
            }

            encodedString.append(encodedLetter);
        }

        return encodedString.toString();
    }

    private static boolean isLatinLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
}
