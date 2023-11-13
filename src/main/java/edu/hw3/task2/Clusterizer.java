package edu.hw3.task2;

import java.util.ArrayList;

public final class Clusterizer {
    private Clusterizer() {
    }

    public static ArrayList<String> clusterize(String input) {
        checkInput(input);

        ArrayList<String> result = new ArrayList<>();
        StringBuilder cluster = new StringBuilder();
        int openedCnt = 0;

        for (char bracket: input.toCharArray()) {
            cluster.append(bracket);

            if (bracket == '(') {
                openedCnt++;
            } else {
                openedCnt--;
                if (openedCnt == 0) {
                    result.add(cluster.toString());
                    cluster = new StringBuilder();
                }
            }
        }

        return result;
    }

    private static void checkInput(String input) {
        int closedCnt = 0;
        int openedCnt = 0;

        if (input == null) {
            throw new NullPointerException("your input string must not be null!");
        } else if (input.isEmpty()) {
            throw new IllegalArgumentException("your input string must not be empty!");
        } else {
            for (char c: input.toCharArray()) {
                if (c == '(') {
                    openedCnt++;
                } else if (c == ')') {
                    closedCnt++;
                } else {
                    throw new IllegalArgumentException("your input string must contain only \')\' and \'(\'");
                }
            }

            if (openedCnt != closedCnt) {
                throw new IllegalArgumentException("amount of opened and closed brackets must be equal!");
            }
        }
    }
}
