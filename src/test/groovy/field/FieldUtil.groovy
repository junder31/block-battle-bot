package field

/**
 * Created by johnunderwood on 11/8/15.
 */
class FieldUtil {
    public static Field getEmptyField(int width, int height) {
        String row = (1..width).collect { "0" }.join(",")
        String grid = (1..height).collect { row }.join(";")
        return new Field(width, height, grid)
    }

    public static Field getResultingField(Shape shape, Field field) {
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

        return removeCompletedLines(new Field(grid));
    }

    private static Field removeCompletedLines(Field field) {
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

    private static List<Integer> getCompletedLines(Field field) {
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
}
