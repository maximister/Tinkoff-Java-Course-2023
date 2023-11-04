package edu.project2.generator;

import edu.project2.structures.Cell;
import edu.project2.structures.Maze;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrimsGenerator extends AbstractGenerator {

    @Override
    public Maze generate(int height, int width) {
        maze = new Maze(height, width);
        maze.fillGrid();

        int startRow = RANDOM.nextInt(height / 2) * 2 + 1;
        int startCol = RANDOM.nextInt(width / 2) * 2 + 1;
        Cell start = maze.getCell(startRow, startCol);
        destroyWall(start);

        List<Cell> neighbours = getNeighbours(startRow, startCol);

        while (!neighbours.isEmpty()) {
            Cell currentCell = neighbours.get(RANDOM.nextInt(neighbours.size()));
            destroyWall(currentCell);

            int currentRow = currentCell.getRow();
            int currentCol = currentCell.getCol();
            neighbours.remove(currentCell);

            List<int[]> moves = Arrays.asList(MOVES_TO_NEIGHBOURS);
            Collections.shuffle(moves);

            for (int[] move : moves) {
                int neighbourRow = currentRow + move[0];
                int neighbourCol = currentCol + move[1];

                if ((neighbourRow > 0 && neighbourRow < height - 1)
                    && (neighbourCol > 0 && neighbourCol < width - 1)
                    && (maze.getCell(neighbourRow, neighbourCol).getType() == Cell.Type.PASSAGE)) {
                    destroyWall(currentCell, maze.getCell(neighbourRow, neighbourCol));
                    break;
                }
            }

            neighbours = Stream.concat(neighbours.stream(), getNeighbours(currentRow, currentCol).stream())
                .distinct()
                .collect(Collectors.toList());
        }

        return maze;
    }
}
