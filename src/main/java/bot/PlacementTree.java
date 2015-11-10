package bot;

import field.Field;
import field.Shape;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by johnunderwood on 11/9/15.
 */
public class PlacementTree implements Comparable<PlacementTree> {
    public final Shape shape;
    public final Field field;
    public final Set<PlacementTree> children = new HashSet<>();

    public PlacementTree(Shape shape, Field field) {
        this.shape = shape;
        this.field = field;
    }

    public void addChild(PlacementTree child) {
        children.add(child);
    }

    public Set<PlacementTree> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlacementTree that = (PlacementTree) o;

        if (!shape.equals(that.shape)) return false;
        if (!field.equals(that.field)) return false;
        return children.equals(that.children);

    }

    @Override
    public int hashCode() {
        int result = shape.hashCode();
        result = 31 * result + field.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }

    public long getScore() {
        if(hasChildren()) {
            long score = 0;
            for(PlacementTree child : children) {
                score += child.getScore();
            }
            return score / children.size();
        } else {
            return field.getScore();
        }
    }

    @Override
    public int compareTo(PlacementTree o) {
        long myScore = getScore();
        long otherScore = o.getScore();

        if(myScore > otherScore) {
            return -1;
        } else if(myScore < otherScore) {
            return 1;
        } else {
            return 0;
        }
    }
}
