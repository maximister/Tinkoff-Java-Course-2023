package edu.hw8.task3;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class AbstractMd5PasswordDecoder implements Md5PasswordDecoder {
    protected Map<String, String> encodedPasswords;
    protected Map<String, String> decodedPasswords;
    protected final static int MIN_PASSWORD_LENGTH = 4;
    protected final static int MAX_PASSWORD_LENGTH = 6;
    //Без заглавных буква 1 поток расшифровывал 1 пароль из 5 букв примерно за 8 сек, а с ними за 176,
    //поэтому, пожалуй, обойдусь без них))))
    protected final static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    protected final static Logger LOGGER = LogManager.getLogger();


    public AbstractMd5PasswordDecoder(Map<String, String> encodedPasswords) {
        if (encodedPasswords == null || encodedPasswords.isEmpty()) {
            throw new IllegalArgumentException("There are no passwords in your list");
        }
        this.encodedPasswords = new HashMap<>(encodedPasswords);
        decodedPasswords = new HashMap<>();
        LOGGER.info("Decoder was created");
    }

    protected void checkPassword(String generatedPassword) {
        String md5Password = DigestUtils.md5Hex(generatedPassword);
        if (encodedPasswords.containsKey(md5Password)) {
            LOGGER.info("Password was found");
            decodedPasswords.put(encodedPasswords.get(md5Password), generatedPassword);
            encodedPasswords.remove(md5Password);
            LOGGER.info(encodedPasswords.size() + " passwords left");
        }
    }

    @Override
    public Map<String, String> getDecodedPasswords() {
        if (decodedPasswords.isEmpty()) {
            decode();
        }

        return decodedPasswords;
    }
}
