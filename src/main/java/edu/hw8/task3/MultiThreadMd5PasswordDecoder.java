package edu.hw8.task3;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.codec.digest.DigestUtils;

public class MultiThreadMd5PasswordDecoder extends AbstractMd5PasswordDecoder {
    private final int threads;
    private final int wordsPerThread;
    private final ExecutorService executor;
    private final CountDownLatch latch;

    public MultiThreadMd5PasswordDecoder(int threads, int wordsPerThread, Map<String, String> passwords) {
        super(passwords);
        this.threads = threads;
        this.wordsPerThread = wordsPerThread;
        this.executor = Executors.newFixedThreadPool(threads);
        this.latch = new CountDownLatch(passwords.size());
    }

    @Override
    public void decode() {
        for (int i = MIN_PASSWORD_LENGTH; i <= MAX_PASSWORD_LENGTH; i++) {
            int wordLength = i;
            for (int j = 0; j < ALPHABET.length; j += wordsPerThread) {
                int startIndex = j;
                executor.execute(() -> getNextPassword(wordLength, startIndex));
            }
        }
        executor.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getNextPassword(int wordLength, int startIndex) {
        int[] index = new int[wordLength];
        index[0] = startIndex;
        while (index[0] < startIndex + wordsPerThread && !encodedPasswords.isEmpty()) {
            StringBuilder password = new StringBuilder();
            for (int j : index) {
                password.append(ALPHABET[j]);
            }

            checkPassword(password.toString());

            for (int i = index.length - 1; ; --i) {
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

    @Override
    protected void checkPassword(String generatedPassword) {
        String md5Password = DigestUtils.md5Hex(generatedPassword);
        if (encodedPasswords.containsKey(md5Password)) {
            LOGGER.info("Password was found");
            decodedPasswords.put(encodedPasswords.get(md5Password), generatedPassword);
            encodedPasswords.remove(md5Password);
            LOGGER.info(encodedPasswords.size() + " passwords left");
            latch.countDown();
        }
    }
}
