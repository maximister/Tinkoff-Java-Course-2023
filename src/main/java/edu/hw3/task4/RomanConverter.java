package edu.hw3.task4;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("checkstyle:MagicNumber")
public class RomanConverter {
    private static final int MAX_ROMAN_NUMBER = 4000;
    private final static LinkedHashMap<Integer, String> COVERSION_TABLE;

    static {
        COVERSION_TABLE = new LinkedHashMap<>();
        COVERSION_TABLE.put(1000, "M");
        COVERSION_TABLE.put(900, "CM");
        COVERSION_TABLE.put(500, "D");
        COVERSION_TABLE.put(400, "CD");
        COVERSION_TABLE.put(100, "C");
        COVERSION_TABLE.put(90, "XC");
        COVERSION_TABLE.put(50, "L");
        COVERSION_TABLE.put(40, "XL");
        COVERSION_TABLE.put(10, "X");
        COVERSION_TABLE.put(9, "IX");
        COVERSION_TABLE.put(5, "V");
        COVERSION_TABLE.put(4, "IV");
        COVERSION_TABLE.put(1, "I");
    }

    private RomanConverter() {
    }

    public static String convertToRoman(int decimalNumber) {
        checkInput(decimalNumber);
        int number = decimalNumber;
        StringBuilder romanNumber = new StringBuilder();

        while (number > 0) {
            Set conversionTable = COVERSION_TABLE.entrySet();
            Iterator iterator = conversionTable.iterator();

            while (iterator.hasNext()) {
                Map.Entry item = (Map.Entry) iterator.next();
                if ((Integer) item.getKey() <= number) {
                    romanNumber.append(item.getValue());
                    number -= (Integer) item.getKey();
                    break;
                }
            }
        }

        return romanNumber.toString();
    }

    private static void checkInput(int decimalNumber) {
        if (decimalNumber <= 0) {
            throw new IllegalArgumentException("your decimal number must be natural!");
        }
        if (decimalNumber >= MAX_ROMAN_NUMBER) {
            throw new IllegalArgumentException("yourr decimal number must be less than 4000!");
        }
    }
}
