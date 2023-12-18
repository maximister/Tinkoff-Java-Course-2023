package edu.hw11.task2;

import lombok.experimental.UtilityClass;

//Дисклеймер: название этого класса - шутка, джока, ржомба, рофл, не несущий цели задеть кого-то
@UtilityClass
public class JewishDebtCalculator {
    //пока не до конца осознал, почему этот метод должен быть статик, чуть позже прогуглю
    public static int calculateDebtPerDay(int a, int b) {
        return a * b;
    }
}
