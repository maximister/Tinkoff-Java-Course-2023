package edu.hw8.task3;

import java.util.Map;

public class SingleThreadMd5PasswordDecoder extends AbstractMd5PasswordDecoder {

    public SingleThreadMd5PasswordDecoder(Map<String, String> encodedPasswords) {
        super(encodedPasswords);
    }

    @Override
    public void decode() {
        while (!encodedPasswords.isEmpty()) {
            for (int i = MIN_PASSWORD_LENGTH; i <= MAX_PASSWORD_LENGTH; i++) {
                getNextPassword(i, 0);
            }
        }
    }

    @Override
    public void getNextPassword(int wordLength, int start) {
        int[] index = new int[wordLength];

        while (!encodedPasswords.isEmpty()) {
            StringBuilder password = new StringBuilder();
            for (int j : index) {
                password.append(ALPHABET[j]);
            }

            checkPassword(password.toString());

            for (int i = wordLength - 1; ; --i) {
                if (i < 0) {
                    return;
                }
                index[i]++;
                if (index[i] == ALPHABET.length) {
                    index[i] = 0;
                } else {
                    break;
                }
            }
        }
    }
}
