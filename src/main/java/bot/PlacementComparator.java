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
        Set<Cell> connected1 = field1.getConnectedCells(spawn, types);
        Field field2 = new Field(getGrid(s2));
        Set<Cell> connected2 = field2.getConnectedCells(spawn, types);

        int c = connected2.size() - connected1.size();
        if (c == 0) {
            c = getPerimeter(connected1, field1) - getPerimeter(connected2, field2);
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

        return c;
    }

    private int getPerimeter(Set<Cell> cells, Field field) {
        int perimeter = 0;

        for(Cell cell : cells) {
            Cell l = field.getLeftNeighbor(cell);
            if(l == null || !l.isEmpty()) {
                perimeter++;
            }
            Cell r = field.getLeftNeighbor(cell);
            if (r == null || !r.isEmpty()) {
                perimeter++;
            }
            Cell u = field.getUpNeighbor(cell);
            if(u == null || !u.isEmpty()) {
                perimeter++;
            }
            Cell d = field.getDownNeighbor(cell);
            if (d == null || !d.isEmpty()) {
                perimeter++;
            }
        }

        return perimeter;
    }

    private Cell[][] getGrid(Shape shape) {
        Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];

        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                Cell cell = field.getCell(x, y);
                if(cell.isShape()) {
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

    private Cell[][] removeCompletedLines(Cell[][] grid) {
        List<Integer> completedLines = new ArrayList<>();

        for (int y = field.getHeight() - 1; y >= 0; y--) {
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
                } else {
                    for(int x = 0; x < field.getWidth(); x++) {
                        grid[x][y+i] = new Cell(x, y+i, grid[x][y].getState());
                    }
                }
            }

            for(y = 0; y < i; y++) {
                for(int x = 0; x < field.getWidth(); x++) {
                    grid[x][y] = new Cell(x, y, CellType.EMPTY);
                }
            }
        }

        return grid;
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
