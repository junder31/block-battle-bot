package bot;

import field.Cell;
import field.CellType;
import field.Field;
import field.Shape;

import java.util.Comparator;

/**
 * Created by johnunderwood on 11/8/15.
 */
class PlacementComparator implements Comparator<Shape> {
    private final Field field;

    public PlacementComparator(Field field) {
        this.field = field;
    }

    @Override
    public int compare(Shape s1, Shape s2) {
        Cell[][] grid1 = getGrid(s1);
        Cell[][] grid2 = getGrid(s2);

        int c = compareOpeness(grid1, grid2);
        if (c == 0) {
            c = compareLinesCompleted(grid1, grid2);
            if (c == 0) {
                c = compareHeight(grid1, grid2);
                if (c == 0) {
                    c = s1.getLocation().x - s2.getLocation().x;
                    if (c == 0) {
                        c = s2.getLocation().y - s2.getLocation().y;
                        if (c == 0) {
                            c = s1.getOrientation().ordinal() - s2.getOrientation().ordinal();
                        }
                    }
                }
            }
        }

        return c;
    }

    private Cell[][] getGrid(Shape shape) {
        Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];

        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                grid[x][y] = new Cell(x, y, field.getCell(x, y).getState());
            }
        }

        for (Cell cell : shape.getBlocks()) {
            grid[cell.getLocation().x][cell.getLocation().y] = new Cell(
                    cell.getLocation().x,
                    cell.getLocation().y,
                    CellType.SHAPE);
        }

        return grid;
    }

    private int compareOpeness(Cell[][] grid1, Cell[][] grid2) {
        return calculateOpeness(grid2) - calculateOpeness(grid1);
    }

    private int calculateOpeness(Cell[][] grid) {
        int score = 0;

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                Cell cell = grid[x][y];
                if (!cell.isEmpty()) {
                    if (cell.getLocation().y + 1 < field.getHeight() &&
                            grid[cell.getLocation().x][cell.getLocation().y + 1].isEmpty()) {
                        score -= 3;

                        if ((cell.getLocation().x + 1 >= field.getWidth() ||
                                !grid[cell.getLocation().x + 1][cell.getLocation().y + 1].isEmpty())
                                && (cell.getLocation().x - 1 < 0 ||
                                !grid[cell.getLocation().x - 1][cell.getLocation().y + 1].isEmpty())
                                ) {
                            score -= 5;
                        }
                    }
                }
            }
        }

        return score;
    }

    private int compareLinesCompleted(Cell[][] grid1, Cell[][] grid2) {
        return calculateLinesCompleted(grid2) - calculateLinesCompleted(grid1);
    }

    private int calculateLinesCompleted(Cell[][] grid) {
        int score = 0;

        for (int y = field.getHeight() - 1; y >= 0; y--) {
            boolean isComplete = true;

            for (int x = 0; x < field.getWidth(); x++) {
                if (grid[x][y].isEmpty()) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                score++;
            }
        }

        return score;
    }

    private int compareHeight(Cell[][] grid1, Cell[][] grid2) {
        return calculateHeight(grid1) - calculateHeight(grid2);
    }

    private int calculateHeight(Cell[][] grid) {
        int score = 0;

        for (int y = field.getHeight() - 1; y >= 0; y--) {
            boolean isEmpty = true;

            for (int x = 0; x < field.getWidth(); x++) {
                if (!grid[x][y].isEmpty()) {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                break;
            } else {
                score++;
            }
        }

        return score;
    }
}
