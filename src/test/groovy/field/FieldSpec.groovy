package field

import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class FieldSpec extends Specification {

    void "test isLocationEmpty for empty cell"() {
        given:
        Field field = new Field(2, 2, "0,0;0,0")
        Point p = new Point(1,1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        true == rVal
    }

    void "test isLocationEmpty for invalid cell"() {
        given:
        Field field = new Field(2, 2, "0,0;0,0")
        Point p = new Point(-1,-1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        false == rVal
    }

    void "test isLocationEmpty for non empty cell"() {
        given:
        Field field = new Field(2, 2, "0,0;0,2")
        Point p = new Point(1,1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        false == rVal
    }

    void "test isValidPosition for I on left side of grid"() {
        given:
        Field field = FieldUtil.getEmptyField(4,4)
        Shape shape = new Shape(ShapeType.I, new Point(-1,0)).turnLeft()

        when:
        boolean rVal = field.isValidPosition(shape)

        then:
        true == rVal
    }
}
