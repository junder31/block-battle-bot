package bot;

import field.*;

import java.util.*;

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
        Point spawn = new Point(4, 0);
        HashSet<CellType> types = new HashSet<>();
        types.add(CellType.EMPTY);
        field.getConnectedCells(spawn, types).size();

        Field field1 = new Field(getGrid(s1));
        Field field2 = new Field(getGrid(s2));

        int c = compareCompletedLines(field1, field2);
        if (c == 0) {
            field1 = removeCompletedLines(field1);
            field2 = removeCompletedLines(field2);

            Set<Set<Cell>> emptyRegions1 = field1.getEmptyRegions();
            Set<Set<Cell>> emptyRegions2 = field2.getEmptyRegions();

            int p1 = 0;
            for (Set<Cell> region : emptyRegions1) {
                p1 += getPerimeter(region, field1);
            }
            int p2 = 0;
            for (Set<Cell> region : emptyRegions2) {
                p2 += getPerimeter(region, field2);
            }
            c = p1 - p2;
            if (c == 0) {
                c = emptyRegions1.size() - emptyRegions2.size();
                if (c == 0) {
                    c = s2.getLocation().y - s1.getLocation().y;
                    if (c == 0) {
                        c = s2.getLocation().x - s1.getLocation().x;
                        if (c == 0) {
                            c = s1.getOrientation().ordinal() - s2.getOrientation().ordinal();
                        }
                    }
                }
            }
        }

        return c;
    }

    private int getPerimeter(Set<Cell> cells, Field field) {
        int perimeter = 0;

        for (Cell cell : cells) {
            perimeter += getNeighborCount(cell, field);
        }

        return perimeter;
    }

    private int getNeighborCount(Cell cell, Field field) {
        int neighbors = 0;

        Cell l = field.getLeftNeighbor(cell);
        if (l == null || !l.isEmpty()) {
            neighbors++;
        }
        Cell r = field.getRightNeighbor(cell);
        if (r == null || !r.isEmpty()) {
            neighbors++;
        }
        Cell u = field.getUpNeighbor(cell);
        if (u == null || !u.isEmpty()) {
            neighbors++;
        }
        Cell d = field.getDownNeighbor(cell);
        if (d == null || !d.isEmpty()) {
            neighbors++;
        }

        return neighbors;
    }

    private Cell[][] getGrid(Shape shape) {
        Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];

        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                Cell cell = field.getCell(x, y);
                if (cell.isShape()) {
                    cell = new Cell(x, y, CellType.EMPTY);
                }
                grid[x][y] = new Cell(x, y, cell.getState());
            }
        }

        for (Cell cell : shape.getBlocks()) {
            grid[cell.getLocation().x][cell.getLocation().y] = cell;
        }

        return grid; //removeCompletedLines(grid);
    }

    private Field removeCompletedLines(Field field) {
        List<Integer> completedLines = getCompletedLines(field);

        if (completedLines.isEmpty()) {
            return field;
        } else {
            Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];
            int i = 0;
            int y;
            for (y = field.getHeight() - 1; y >= 0; y--) {
                if (i < completedLines.size() && y == completedLines.get(i)) {
                    i++;
                } else {
                    for (int x = 0; x < field.getWidth(); x++) {
                        grid[x][y + i] = new Cell(x, y + i, field.getCell(x, y).getState());
                    }
                }
            }

            for (y = 0; y < i; y++) {
                for (int x = 0; x < field.getWidth(); x++) {
                    grid[x][y] = new Cell(x, y, CellType.EMPTY);
                }
            }

            return new Field(grid);
        }
    }

    private List<Integer> getCompletedLines(Field field) {
        List<Integer> completedLines = new ArrayList<>();

        for (int y = field.getHeight() - 1; y >= 0; y--) {
            boolean isComplete = true;

            for (int x = 0; x < field.getWidth(); x++) {
                if (field.getCell(x, y).isEmpty()) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                completedLines.add(y);
            }
        }

        return completedLines;
    }

    private int compareCompletedLines(Field field1, Field field2) {
        int comp1 = getCompletedLines(field1).size();
        int comp2 = getCompletedLines(field2).size();

        if (comp1 > 2 || comp2 > 2) {
            return comp2 - comp1;
        } else {
            return 0;
        }
    }

    public String printField(Field field) {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                sb.append(field.getCell(x, y).getState().ordinal());
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public String printNeighborCounts(Field field) {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                sb.append(getNeighborCount(field.getCell(x, y), field));
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
