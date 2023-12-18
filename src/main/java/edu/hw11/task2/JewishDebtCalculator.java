package edu.hw11.task2;


//Дисклеймер: название этого класса - шутка, джока, ржомба, рофл, не несущий цели задеть кого-то
public final class JewishDebtCalculator {
    private JewishDebtCalculator() {
    }

    //пока не до конца осознал, почему этот метод должен быть статик, чуть позже прогуглю
    public static int calculateDebtPerDay(int a, int b) {
        return a * b;
    }
}
