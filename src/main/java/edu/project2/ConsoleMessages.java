package edu.project2;

public enum ConsoleMessages {
    WELCOME_MESSAGE("Добро пожаловать в генератор Лабиринтов!"),
    SELECT_SIZE("Ведите парметры лабиринта в формате NxM, где N и M - нечетные числа больше 5:"),
    SELECT_GENERATOR("Выберите алгоритм генерации:\n" + "1) DFS\n" + "2) че-то еще будет\n"),
    SELECT_SOLVER("Выберите алгоритм решения лабиринта:\n" + "1) DFS\n" + "2) что-то еще\n"),
    GENERATED_MAZE_MESSAGE("Лабиринт сгенерирован:\n"),
    SOLVED_MAZE_MESSAGE("Лабиринт решен:\n"),

    SELECT_COORDINATES("Укажите координаты начальной и конечной точки в следующем формате:  X1:Y1 X2:Y2"),
    EXIT_COMMAND("Для завершения работы введите команду EXIT до начала генерации лабиринта");

    private final String message;

    ConsoleMessages(String text) {
        this.message = text;
    }

    public String getMessage() {
        return message;
    }
}
