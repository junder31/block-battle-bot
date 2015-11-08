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
        ShapeOrientation.RIGHT == positions.first().orientation
    }

    void "test limiting overhang. I shape bottom left filled"() {
        given:
        Field field = FieldUtil.getEmptyField(4, 4)
        field.getCell(0, 3).state = CellType.BLOCK
        field.getCell(1, 3).state = CellType.BLOCK
        field.getCell(0, 2).state = CellType.BLOCK
        List<Shape> positions = [new Shape(ShapeType.I, new Point(0,0)).turnRight(),
                                 new Shape(ShapeType.I, new Point(0,-1)).turnRight().turnRight(),
                                 new Shape(ShapeType.I, new Point(0,0))]

        when:
        Collections.sort(positions, new PlacementComparator(field))

        then:
        ShapeOrientation.RIGHT == positions.first().orientation
    }
}
