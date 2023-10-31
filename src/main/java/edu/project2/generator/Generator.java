package edu.project2.generator;

import edu.project2.structures.Maze;

/*
делает
бим бим бам бам
и лабиринт готов
 */
//TODO: расширить различными алгоритмами
//TODO: общие черты генерации и обработки ошибок можно вынести в абстрактный класс
public interface Generator {
    Maze generate(int height, int width);
}
