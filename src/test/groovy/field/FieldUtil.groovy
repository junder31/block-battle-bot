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
}
