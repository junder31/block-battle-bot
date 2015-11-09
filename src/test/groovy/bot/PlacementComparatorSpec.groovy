package bot

import field.CellType
import field.Field
import field.FieldUtil
import field.Point
import field.Shape
import field.ShapeOrientation
import field.ShapeType
import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class PlacementComparatorSpec extends Specification {

    void "test limiting overhang. T shape empty grid"() {
        given:
        Field field = FieldUtil.getEmptyField(4, 4)
        List<Shape> positions = [new Shape(ShapeType.T, new Point(0,1)).turnRight(),
                                 new Shape(ShapeType.T, new Point(0,1)).turnRight().turnRight(),
                                 new Shape(ShapeType.T, new Point(0,2)),
                                 new Shape(ShapeType.T, new Point(0,1)).turnRight().turnRight().turnRight()]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        ShapeOrientation.UP == positions.first().orientation
    }

    void "test limiting overhang. Z shape empty grid"() {
        given:
        Field field = FieldUtil.getEmptyField(4, 4)
        List<Shape> positions = [new Shape(ShapeType.Z, new Point(0,1)).turnRight(),
                                 new Shape(ShapeType.Z, new Point(0,1)).turnRight().turnRight(),
                                 new Shape(ShapeType.Z, new Point(0,2)),
                                 new Shape(ShapeType.Z, new Point(0,1)).turnRight().turnRight().turnRight()]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        ShapeOrientation.LEFT == positions.first().orientation
    }

    void "test limiting overhang. I shape bottom left filled"() {
        given:
        Field field = new Field(4, 4, "0,0,0,0;0,0,0,0;2,0,0,0;2,2,0,0")
        List<Shape> positions = [new Shape(ShapeType.I, new Point(0,0)).turnRight(),
                                 new Shape(ShapeType.I, new Point(0,-1)).turnRight().turnRight(),
                                 new Shape(ShapeType.I, new Point(0,0))]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        ShapeOrientation.RIGHT == positions.first().orientation
    }

    void "test limiting overhang. I shape bottom partially filled"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,0,0,0,0,0,0;" +
                "0,0,0,0,2,2,2,0,0,0;" +
                "0,2,2,2,2,2,2,2,0,0")

        List<Shape> positions = [new Shape(ShapeType.I, new Point(-2,16)).turnRight(),
                                 new Shape(ShapeType.I, new Point(7,16)).turnRight()]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        -2 == positions.first().location.x
    }

    void "test S piece with position from in game"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,2,2,0,0,0,0,2,2;" +
                        "0,0,0,2,2,0,0,0,2,2")
        List<Shape> positions = [new Shape(ShapeType.S, new Point(3,16)),
                                 new Shape(ShapeType.S, new Point(4,17)).turnLeft()]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        ShapeOrientation.LEFT == positions.first().orientation
    }

    void "test limiting height. I shape empty field"() {
        given:
        Field field = FieldUtil.getEmptyField(10, 5)

        List<Shape> positions = [new Shape(ShapeType.I, new Point(3,1)).turnRight(),
                                 new Shape(ShapeType.I, new Point(3,3))]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        ShapeOrientation.UP == positions.first().orientation
    }
}
