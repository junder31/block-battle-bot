package bot;

import field.Field;
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
        Set<Path> pathSet = new HashSet<>();
        Path startPath = new Path(new Shape(end));
        pathSet.add(startPath.applyMove(MoveType.DROP));

        while (!pathSet.isEmpty()) {
            log.trace("Evaluating paths: %s", pathSet);
            Set<Path> nextPathSets = new HashSet<>();
            for (Path path : getNextPaths(pathSet)) {
                if (path.shape.equals(start)) {
                    return path.moves;
                } else if (!visitedLocations.contains(path.shape) && path.isValid()) {
                    nextPathSets.add(path);
                    visitedLocations.add(path.shape);
                }
            }
            pathSet = nextPathSets;
        }

        throw new NoPathAvailableException();
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
            newMoves.push(move);

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

            return new Path(newMoves, newShape);
        }

        public boolean isValid() {
            return field.areShapeCellsEmpty(shape);
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
