package bot

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

    void "test limiting overhang.  T shape empty grid"() {
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
}
