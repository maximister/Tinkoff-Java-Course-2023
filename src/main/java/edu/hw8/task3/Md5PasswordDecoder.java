package edu.hw8.task3;

import java.util.Map;

public interface Md5PasswordDecoder {
    void decode();

    void getNextPassword(int wordLength, int start);

    Map<String, String> getDecodedPasswords();
}
