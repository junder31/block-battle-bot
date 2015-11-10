package bot;

import field.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by johnunderwood on 11/9/15.
 */
public class PlacementPermutator {
    private final Field startingField;
    private final ShapeType t1;
    private final ShapeType t2;
    private int additionalTurns;

    public PlacementPermutator(Field startingField, ShapeType t1, ShapeType t2, int additionalTurns) {
        this.startingField = removeShapeBlocks(startingField);
        this.t1 = t1;
        this.t2 = t2;
        this.additionalTurns = additionalTurns;
    }

    public List<PlacementTree> getPossibleResultingFields() {
        List<PlacementTree> placementTrees = new ArrayList<>();

        Set<Shape> t1Placements = getPossiblePlacements(t1, startingField);
        for(Shape shape : t1Placements) {
            placementTrees.add(new PlacementTree(shape, getResultingField(shape, startingField)));
        }

        for(PlacementTree pt : placementTrees) {
            Set<Shape> t2Placements = getPossiblePlacements(t2, pt.field);
            for(Shape shape : t2Placements) {
                pt.addChild(new PlacementTree(shape, getResultingField(shape, pt.field)));
            }
        }

        return placementTrees;
    }

    private Set<Shape> getPossiblePlacements(ShapeType shapeType, Field field) {
        Set<Shape> placements = new HashSet<>();

        for (int x = -2; x < field.getWidth(); x++) {
            for (int y = field.getHeight() - 1; y >= -2; y--) {
                for (int i = 0; i < 4; i++) {
                    Shape shape = new Shape(shapeType, new Point(x, y));

                    for(int j = i; j > 0; j--) {
                        shape = shape.turnRight();
                    }

                    if(field.isValidPosition(shape)) {
                        placements.add(shape);
                    }
                }
            }
        }

        return placements;
    }

    private Field removeShapeBlocks(Field field) {
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

        return new Field((grid));
    }

    private Field getResultingField(Shape shape, Field field) {
        Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];

        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                grid[x][y] = field.getCell(x, y);
            }
        }

        for (Cell cell : shape.getBlocks()) {
            grid[cell.getLocation().x][cell.getLocation().y] = cell;
        }

        return removeCompletedLines(new Field(grid));
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
}
