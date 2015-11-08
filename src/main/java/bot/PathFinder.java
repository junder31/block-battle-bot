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
        Set<List<MoveType>> pathSet = new HashSet<>();
        LinkedList<MoveType> startPath = new LinkedList<>();
        startPath.push(MoveType.DROP);
        pathSet.add(startPath);

        while(!pathSet.isEmpty()) {
            log.trace("Evaluating paths: %s", pathSet);
            Set<List<MoveType>> nextPathSets = new HashSet<>();
            for (List<MoveType> path : getNextPaths(pathSet)) {
                PathEvaluator pathEvaluator = new PathEvaluator(path);
                if (pathEvaluator.isComplete) {
                    return path;
                } else if (pathEvaluator.isValid) {
                    nextPathSets.add(path);
                }
            }
            pathSet = nextPathSets;
        }

        throw new NoPathAvailableException();
    }

    private Set<List<MoveType>> getNextPaths(Set<List<MoveType>> lastPaths) {
        Set<List<MoveType>> nextPaths = new HashSet<>();

        for (List<MoveType> lastPath : lastPaths) {
            for (MoveType moveType : MoveType.getPathMoveTypes()) {
                LinkedList<MoveType> nextPath = new LinkedList<>(lastPath);
                nextPath.push(moveType);
                nextPaths.add(nextPath);
            }
        }

        return nextPaths;
    }

    private class PathEvaluator {
        private final boolean isValid;
        private final boolean isComplete;

        public PathEvaluator(List<MoveType> moves) {
            Shape shape = new Shape(end);

            for (int i = moves.size() - 1; i >= 0; i--) {
                MoveType move = moves.get(i);
                applyMove(shape, move);
            }

            if (visitedLocations.contains(shape)) {
                isValid = false;
                isComplete = false;
            } else if (field.isValidPosition(shape)) {
                isValid = true;
                isComplete = shape.equals(start);
            } else {
                isValid = false;
                isComplete = false;
            }
        }

        private void applyMove(Shape shape, MoveType move) {
            switch (move) {
                case DOWN:
                    shape.oneUp();
                    break;
                case LEFT:
                    shape.oneRight();
                    break;
                case RIGHT:
                    shape.oneLeft();
                    break;
                case TURNRIGHT:
                    shape.turnLeft();
                    break;
                case TURNLEFT:
                    shape.turnRight();
                    break;
            }
        }
    }
}
