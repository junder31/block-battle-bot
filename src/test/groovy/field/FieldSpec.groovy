package field

import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class FieldSpec extends Specification {
    public Field field

    void setup() {
        field = new Field(2, 2, "0,0;0,0")
    }

    void "test isLocationEmpty for empty cell"() {
        given:
        Point p = new Point(1,1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        true == rVal
    }

    void "test isLocationEmpty for invalid cell"() {
        given:
        Point p = new Point(-1,-1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        false == rVal
    }

    void "test isLocationEmpty for non empty cell"() {
        given:
        Point p = new Point(1,1)
        field.getCell(1, 1).state = CellType.BLOCK

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        false == rVal
    }

    void "test isValidPosition for I on left side of grid"() {
        given:
        Field field = FieldUtil.getEmptyField(4,4)
        Shape shape = new Shape(ShapeType.I, new Point(-1,0))
        shape.turnLeft()

        when:
        boolean rVal = field.isValidPosition(shape)

        then:
        true == rVal
    }
}
