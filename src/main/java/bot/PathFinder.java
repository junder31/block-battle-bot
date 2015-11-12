package bot;

import field.Cell;
import field.Field;
import field.Point;
import field.Shape;
import log.Logger;
import moves.MoveType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by johnunderwood on 11/8/15.
 */
public class PathFinder {
    private static Logger log = new Logger(PathFinder.class.getSimpleName());
    private Set<Shape> visitedLocations = new HashSet<>();
    private final Shape start;
    private final Shape end;
    private final Field field;

    public PathFinder(Shape start, Shape end, Field field) {
        this.start = start;
        this.end = end;
        this.field = field;
        visitedLocations.add(end);
    }

    public List<MoveType> findPath() {
        Path startPath = new Path(new Shape(end));
        Path path = pathExistsHelper(startPath.applyMove(MoveType.DROP), visitedLocations, -100);

        if(path != null) {
            return path.moves;
        } else {
            throw new NoPathAvailableException();
        }
    }

    public boolean pathExists() {
        Path startPath = new Path(new Shape(end));
        int clearHeight = field.getHeight() - (field.getMaxHeight() + end.getSize());
        return pathExistsHelper(startPath.applyMove(MoveType.DROP), visitedLocations, clearHeight) != null;
    }

    private Path pathExistsHelper(Path currentPath, Set<Shape> visited, int height) {

        for (MoveType moveType : MoveType.getPathMoveTypes()) {
            Path nextPath = currentPath.applyMove(moveType);
            if(nextPath.shape.equals(start) || nextPath.shape.getLocation().y <= height) {
                return nextPath;
            } else if(!visitedLocations.contains(nextPath.shape) && nextPath.isValid()){
                visited.add(nextPath.shape);
                Path rVal = pathExistsHelper(nextPath, visited, height);
                if(rVal != null) {
                    return rVal;
                }
            }
        }

        return null;
    }

    private Set<Path> getNextPaths(Set<Path> lastPaths) {
        Set<Path> nextPaths = new HashSet<>();

        for (Path lastPath : lastPaths) {
            for (MoveType moveType : MoveType.getPathMoveTypes()) {
                nextPaths.add(lastPath.applyMove(moveType));
            }
        }

        return nextPaths;
    }

    private class Path {
        protected final LinkedList<MoveType> moves;
        protected final Shape shape;

        protected Path(Shape end) {
            moves = new LinkedList<>();
            shape = new Shape(end);
        }

        private Path(LinkedList<MoveType> moves, Shape shape) {
            this.moves = moves;
            this.shape = shape;
        }

        public Path applyMove(MoveType move) {
            LinkedList<MoveType> newMoves = new LinkedList<>(moves);

            Shape newShape;
            switch (move) {
                case DOWN:
                    newShape = shape.oneUp();
                    break;
                case LEFT:
                    newShape = shape.oneRight();
                    break;
                case RIGHT:
                    newShape = shape.oneLeft();
                    break;
                case TURNRIGHT:
                    newShape = shape.turnLeft();
                    break;
                case TURNLEFT:
                    newShape = shape.turnRight();
                    break;
                default:
                    newShape = shape;
            }

            if(!(move == MoveType.DOWN && moves.getFirst() == MoveType.DROP)) {
                newMoves.push(move);
            }

            return new Path(newMoves, newShape);
        }

        public boolean isValid() {
            Cell[] cells = shape.getBlocks();

            for (Cell cell : cells) {
                Point p = cell.getLocation();
                Cell fieldCell = field.getCell(p);
                if ((fieldCell == null || (!fieldCell.isEmpty() && (!fieldCell.isShape() || p.y != 0)))) {
                    if( !(p.y == -1 && p.x >= 0 && p.x < field.getWidth())) {
                        return false;
                    }
                }
            }

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Path path = (Path) o;

            if (!moves.equals(path.moves)) return false;
            return shape.equals(path.shape);
        }

        @Override
        public int hashCode() {
            int result = moves.hashCode();
            result = 31 * result + shape.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "moves=" + moves +
                    ", shape=" + shape +
                    '}';
        }
    }
}
