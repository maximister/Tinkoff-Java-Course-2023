package edu.hw5.task8;

import java.util.regex.Pattern;

public final class ExtendedBinaryStringValidator {
    private ExtendedBinaryStringValidator() {
    }

    //нечетная длина
    public static boolean isLengthOdd(String binaryString) {
        /*как я понял, задать переменную длину в регулярках нельзя,
        но если я ошибся, то буду рад услышать джаст гугл ит, бат вот ансвер */
        return binaryString.matches("^[01]([01]{2})*$");
    }

    //начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
    public static boolean isZeroAndOddLenOrOneAndEvenLen(String binaryString) {
        return binaryString.matches("^0([01]{2})* || 1[01]([01]{2})*$");
    }

    //количество 0 кратно 3
    public static boolean isZerosMultipleTree(String binaryString) {
        /*считал, что отсутствие 0 тоже подходит
          если 0 обязательтъны то ^1*(1*01*01*0)+1*$ по идее*/
        return binaryString.matches("^1*(1*01*01*0)*1*$");
    }

    //любая строка, кроме 11 или 111
    public static boolean isNotTwoOrTreeOnes(String binaryString) {
        return binaryString.matches("(?!111$|11$)[10]*");
    }

    //каждый нечетный символ равен 1
    public static boolean isEveryOddSymbolIsOne(String binaryString) {
        //считал, что нумерация с 1
        return binaryString.matches("^(1[01])*1?$");
    }

    //содержит не менее двух 0 и не более одной 1
    public static boolean isMoreThan2ZerosAnd1OrNoOnes(String binaryString) {
        return binaryString.matches("^(1?0{2,}|0+1?0+|0{2,}1?)$");
    }

    //нет последовательных 1

}
