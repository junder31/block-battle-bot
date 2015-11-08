package bot;

import field.Field;
import field.Shape;
import moves.MoveType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by johnunderwood on 11/8/15.
 */
public class PathFinder {

    public List<MoveType> getPath(Shape start, Shape end, Field field) {
        Set<Stack<MoveType>> pathSet = new HashSet<>();
        Stack<MoveType> path = new Stack<>();
        path.push(MoveType.DROP);
        pathSet.add(path);



        return null;
    }
}
