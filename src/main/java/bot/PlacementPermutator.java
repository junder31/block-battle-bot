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
    private int additionalTurns;

    public PlacementPermutator(Field startingField, ShapeType t1, ShapeType t2, int additionalTurns) {
        this.startingField = removeShapeBlocks(startingField);
        this.t1 = t1;
        this.t2 = t2;
        this.additionalTurns = additionalTurns;
    }

    public List<PlacementTree> getPossibleResultingFields() {
        List<PlacementTree> placementTrees = new ArrayList<>();

        log.debug("Generating possible placements for first generation.");
        Set<Shape> t1Placements = getPossiblePlacements(t1, startingField);
        for (Shape shape : t1Placements) {
            placementTrees.add(new PlacementTree(shape, startingField.getResultingField(shape)));
        }
        log.debug("Generated %d possible placements for first generation.", placementTrees.size());

        log.debug("Generating possible placements for second generation.");
        Set<PlacementTree> lastGeneration = new HashSet<>();
        for (PlacementTree pt : placementTrees) {
            Set<Shape> t2Placements = getPossiblePlacements(t2, pt.field);
            for (Shape shape : t2Placements) {
                PlacementTree ptt = new PlacementTree(shape, pt.field.getResultingField(shape));
                pt.addChild(ptt);
                lastGeneration.add(ptt);
            }
        }
        log.debug("Generated %d possible placements for second generation.", lastGeneration.size());

        for (int i = 0; i < Math.max(1, additionalTurns); i++) {
            log.debug("Generating possible placements for %d generation.", i + 3);
            Set<PlacementTree> nextGeneration = new HashSet<>();
            for (PlacementTree pt : lastGeneration) {
                for (ShapeType st : ShapeType.values()) {
                    Set<Shape> placements = getPossiblePlacements(st, pt.field);
                    for (Shape shape : placements) {
                        PlacementTree ptt = new PlacementTree(shape, pt.field.getResultingField(shape));
                        pt.addChild(ptt);
                        nextGeneration.add(ptt);
                    }
                }
            }
            lastGeneration = nextGeneration;
            log.debug("Generated %d possible placements for %d generation.", lastGeneration.size(), i + 3);
        }

        log.debug("Finished generating possible placements.");
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
