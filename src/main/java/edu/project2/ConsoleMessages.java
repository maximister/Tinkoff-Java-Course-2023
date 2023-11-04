package edu.project2;

public enum ConsoleMessages {
    WELCOME_MESSAGE("Добро пожаловать в генератор Лабиринтов!"),
    SELECT_SIZE("Введите парметры лабиринта в формате NxM, где N и M - нечетные числа больше или равные 5:"),
    SELECT_GENERATOR("Выберите алгоритм генерации:\n1) DFS\n2) Алгоритм Прима\n"),
    SELECT_SOLVER("Выберите алгоритм решения лабиринта:\n1) DFS\n2) что-то еще\n"),
    GENERATED_MAZE_MESSAGE("Лабиринт сгенерирован:\n"),
    SOLVED_MAZE_MESSAGE("Лабиринт решен:\n"),
    SELECT_COORDINATES("Укажите координаты начальной и конечной точки в следующем формате:  X1:Y1 X2:Y2"),
    EXIT_COMMAND("Для завершения работы введите команду EXIT вместо размеров лабиринта"),
    USER_INPUT("-> "),
    END_MESSAGE("Работа завершена");

    private final String message;

    ConsoleMessages(String text) {
        this.message = text;
    }

    public String getMessage() {
        return message;
    }
}
