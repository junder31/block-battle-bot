package bot

import field.Cell
import field.Field
import field.Shape

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
        Cell[][] grid1

        int c = compareOpeness(s1, s2);
        if(c == 0) {
            c = compareLinesCompleted(s1, s2)
            if (c == 0) {
                c = compareHeight(s1, s2)
            }
        }

        return c
    }

    private Cell[][] getGrid(Shape shape) {
        Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];

        for(int x = 0; x < field.getWidth(); x++) {
            for(int y = 0; y < field.getHeight(); y++) {
                grid[x,y] = new Cell(x, y, field.getCell(x, y).getState());
            }
        }

        for(Cell cell : shape.getBlocks()) {
            grid[cell.location.x, cell.location.y] = cell;
        }
    }

    private int compareOpeness(Shape s1, Shape s2) {

    }

    private int compareLinesCompleted(Shape s1, Shape s2) {

    }

    private int compareHeight(Shape s1, Shape s2) {

    }
}
