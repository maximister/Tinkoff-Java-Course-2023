package edu.project2.generator;

import edu.project2.structures.Maze;


public interface Generator {
    Maze generate(int height, int width);
}
