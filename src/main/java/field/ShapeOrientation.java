package field;

/**
 * Created by johnunderwood on 11/8/15.
 */
public enum ShapeOrientation {
    UP,RIGHT,DOWN,LEFT;

    public ShapeOrientation rotateLeft() {
        return ShapeOrientation.values()[(this.ordinal() + 3) % ShapeOrientation.values().length];
    }

    public ShapeOrientation rotateRight() {
        return ShapeOrientation.values()[(this.ordinal() + 1) % ShapeOrientation.values().length];
    }
}
