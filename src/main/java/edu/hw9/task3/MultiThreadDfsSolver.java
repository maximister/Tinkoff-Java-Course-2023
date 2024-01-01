package edu.hw9.task3;

import edu.project2.solver.AbstractSolver;
import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MultiThreadDfsSolver extends AbstractSolver {
    //с каким же кайфом я в одном месте забыл, что х - столбец, у - строка, и искал ошибку час(
    private ForkJoinPool forkJoinPool;

    @Override
    public synchronized List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        List<Coordinate> res;
        maze.resetVisitedCells();
        try (ForkJoinPool pool = new ForkJoinPool()) {
            res = pool.invoke(new RecursiveSolverTask(start.col(), start.row(), end.col(), end.row()));
        }
        return res;
    }

    private final class RecursiveSolverTask extends RecursiveTask<List<Coordinate>> {
        private final int x;
        private final int y;
        private final int endX;
        private final int endY;

        RecursiveSolverTask(int x, int y, int endX, int endY) {
            this.x = x;
            this.y = y;
            this.endX = endX;
            this.endY = endY;
        }

        @Override
        protected List<Coordinate> compute() {
            maze.getCell(y, x).visitThisCell();
            List<Coordinate> coordinates = new ArrayList<>();

            if (x == endX && y == endY) {
                coordinates.add(new Coordinate(y, x));
                return coordinates;
            }

            List<ForkJoinTask<List<Coordinate>>> subTasks = createNeededTasks();
            for (var subTask : subTasks) {
                List<Coordinate> subTaskResult = subTask.join();
                if (!subTaskResult.isEmpty()) {
                    coordinates.add(new Coordinate(y, x));
                    coordinates.addAll(subTaskResult);
                }
            }
            return coordinates;
        }

        private List<ForkJoinTask<List<Coordinate>>> createNeededTasks() {
            List<ForkJoinTask<List<Coordinate>>> tasks = new ArrayList<>();
            List<Cell> neighbours = getNeighbour(maze.getCell(y, x));
            if (neighbours != null) {
                neighbours.forEach(cell -> {
                    tasks.add(new RecursiveSolverTask(cell.getCol(), cell.getRow(), endX, endY).fork());
                });
            }

            return tasks;
        }
    }
}
