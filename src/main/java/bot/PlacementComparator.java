package bot;

import field.Cell;
import field.CellType;
import field.Field;
import field.Shape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
            grid[cell.getLocation().x][cell.getLocation().y] = cell;
        }

        return removeCompletedLines(grid);
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
                        score -= 20;

                        if (cell.getLocation().x + 1 >= field.getWidth() ||
                                !grid[cell.getLocation().x + 1][cell.getLocation().y + 1].isEmpty()) {
                            score--;
                        }

                        if (cell.getLocation().x - 1 < 0 ||
                                !grid[cell.getLocation().x - 1][cell.getLocation().y + 1].isEmpty()) {
                            score--;
                        }
                    }

                    if (cell.getLocation().x + 1 < field.getWidth() &&
                            grid[cell.getLocation().x + 1][cell.getLocation().y].isEmpty() &&
                            cell.getLocation().x + 2 < field.getWidth() &&
                            !grid[cell.getLocation().x + 2][cell.getLocation().y].isEmpty()) {
                        score--;
                    }

                    if (cell.getLocation().x - 1 >= 0 &&
                            grid[cell.getLocation().x - 1][cell.getLocation().y].isEmpty() &&
                            cell.getLocation().x - 2 > 0 &&
                            !grid[cell.getLocation().x - 2][cell.getLocation().y].isEmpty()) {
                        score--;
                    }
                }
            }
        }

        return score;
    }

    private Cell[][] removeCompletedLines(Cell[][] grid) {
        List<Integer> completedLines = new ArrayList<>();

        for (int y = 0; y < field.getHeight(); y++) {
            boolean isComplete = true;

            for (int x = 0; x < field.getWidth(); x++) {
                if (grid[x][y].isEmpty()) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                completedLines.add(y);
            }
        }

        if(!completedLines.isEmpty()) {
            int i = 0;
            int y;
            for(y = field.getHeight() - 1; y > completedLines.size(); y--) {
                if(i < completedLines.size() && y == completedLines.get(i)) {
                    i++;
                }

                for(int x = 0; x < field.getWidth(); x++) {
                    grid[x][y] = grid[x][y-i];
                }
            }

            for(; y > 0; y--) {
                for(int x = 0; x < field.getWidth(); x++) {
                    grid[x][y] = new Cell(x, y, CellType.EMPTY);
                }
            }
        }

        return grid;
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

    public String printGrid(Cell[][] grid) {
        StringBuilder sb = new StringBuilder();

        for(int y = 0; y < field.getHeight(); y++) {
            for(int x = 0; x < field.getWidth(); x++) {
                sb.append(grid[x][y].getState().ordinal());
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
