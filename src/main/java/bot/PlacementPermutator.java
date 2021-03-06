package bot;

import field.*;
import log.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by johnunderwood on 11/9/15.
 */
public class PlacementPermutator {
    private static final Logger log = new Logger(PlacementPermutator.class.getSimpleName());
    private final Field startingField;
    private final ShapeType t1;
    private final ShapeType t2;

    public PlacementPermutator(Field startingField, ShapeType t1, ShapeType t2) {
        this.startingField = removeShapeBlocks(startingField);
        this.t1 = t1;
        this.t2 = t2;
    }

    public List<PlacementTree> getPossibleResultingFields() {
        List<PlacementTree> placementTrees = new ArrayList<>();

        Set<Shape> t1Placements = getPossiblePlacements(t1, startingField);
        for (Shape shape : t1Placements) {
            Field field = startingField.getResultingField(shape);
            if (!field.areStartingPositionsBlocked()) {
                placementTrees.add(new PlacementTree(shape, field));
            }
        }

        for (PlacementTree pt : placementTrees) {
            Set<Shape> t2Placements = getPossiblePlacements(t2, pt.field);
            for (Shape shape : t2Placements) {
                Field field = pt.field.getResultingField(shape);
                if (!field.areStartingPositionsBlocked()) {
                    PlacementTree ptt = new PlacementTree(shape, field);
                    pt.addChild(ptt);
                }

            }
        }

        return placementTrees;
    }

    private Set<Shape> getPossiblePlacements(ShapeType shapeType, Field field) {
        Set<Shape> placements = new HashSet<>();
        int minValidY = field.getHeight() - field.getMaxHeight() - getShapeSize(shapeType);

        for (int y = field.getHeight() - 1; y >= Math.max(-1, minValidY); y--) {
            for (int x = -2; x < field.getWidth(); x++) {
                int rotations = getTimesToRotate(shapeType);
                for (int i = 0; i < rotations; i++) {
                    Shape shape = new Shape(shapeType, new Point(x, y));

                    for (int j = i; j > 0; j--) {
                        shape = shape.turnRight();
                    }

                    boolean isValid = field.isValidPosition(shape);

                    if (isValid) {
                        Shape start = new Shape(shape.type, getStartingLocation(shape.type));
                        PathFinder pathFinder = new PathFinder(start, shape, field);
                        if (pathFinder.pathExists()) {
                            placements.add(shape);
                        }
                    }
                }
            }
        }

        return placements;
    }

    private int getTimesToRotate(ShapeType shapeType) {
        int rotations;

        switch (shapeType) {
            case L:
            case J:
            case T:
                rotations = 4;
                break;
            case O:
                rotations = 1;
                break;
            default:
                rotations = 2;
                break;
        }

        return rotations;
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

    private Point getStartingLocation(ShapeType type) {
        switch (type) {
            case O:
                return new Point(4, -1);
            default:
                return new Point(3, -1);
        }
    }

    private int getShapeSize(ShapeType shapeType) {
        int size;

        switch (shapeType) {
            case I:
                size = 4;
                break;
            case O:
                size = 2;
                break;
            default:
                size = 3;
                break;
        }

        return size;
    }
}
