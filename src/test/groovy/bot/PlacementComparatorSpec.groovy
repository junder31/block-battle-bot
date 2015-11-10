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

//    void "test limiting overhang. T shape empty grid"() {
//        given:
//        Field field = FieldUtil.getEmptyField(4, 4)
//        List<Shape> positions = [new Shape(ShapeType.T, new Point(0, 1)).turnRight(),
//                                 new Shape(ShapeType.T, new Point(0, 1)).turnRight().turnRight(),
//                                 new Shape(ShapeType.T, new Point(0, 2)),
//                                 new Shape(ShapeType.T, new Point(0, 1)).turnRight().turnRight().turnRight()]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        ShapeOrientation.UP == positions.first().orientation
//    }
//
//    void "test limiting overhang. I shape bottom left filled"() {
//        given:
//        Field field = new Field(6, 6,
//                "0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0;" +
//                        "2,0,0,0,0,0")
//        List<Shape> positions = [new Shape(ShapeType.I, new Point(-2, 1)).turnRight(),
//                                 new Shape(ShapeType.I, new Point(0, 3)),
//                                 new Shape(ShapeType.I, new Point(1, 4))]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        [new Shape(ShapeType.I, new Point(1, 4)),
//                new Shape(ShapeType.I, new Point(-2, 1)).turnRight(),
//                new Shape(ShapeType.I, new Point(0, 3))] == positions
//    }
//
//    void "test minimizing perimeter"() {
//        given:
//        Field field = new Field(10, 20,
//                "0,0,0,1,1,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,2,2,0,0,0,0,2,2;" +
//                        "0,0,0,2,2,0,0,0,2,2")
//        List<Shape> positions = [new Shape(ShapeType.S, new Point(-1, 17)).turnRight(),
//                                 new Shape(ShapeType.S, new Point(4, 17)).turnLeft()]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        [new Shape(ShapeType.S, new Point(4, 17)).turnLeft(),
//         new Shape(ShapeType.S, new Point(-1, 17)).turnRight()] == positions
//    }
//
//    void "test minimizing perimeter 2"() {
//        given:
//        Field field = new Field(10, 20,
//                "0,0,0,1,1,1,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,2,2,0,0,0,0;" +
//                        "0,0,0,2,2,2,2,0,0,0;" +
//                        "0,0,0,2,2,2,2,0,0,2;" +
//                        "2,2,2,2,2,2,0,0,0,2")
//        List<Shape> positions = [new Shape(ShapeType.T, new Point(0, 17)),
//                                 new Shape(ShapeType.T, new Point(6, 18))]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        [new Shape(ShapeType.T, new Point(6, 18)),
//         new Shape(ShapeType.T, new Point(0, 17))] == positions
//    }
//
//    void "test limiting overhang. I shape bottom partially filled"() {
//        given:
//        Field field = new Field(10, 20,
//                "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,2,2,2,0,0,0;" +
//                        "0,2,2,2,2,2,2,2,0,0")
//
//        List<Shape> positions = [new Shape(ShapeType.I, new Point(-2, 16)).turnRight(),
//                                 new Shape(ShapeType.I, new Point(7, 16)).turnRight()]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        -2 == positions.first().location.x
//    }
//
//    void "test S piece with position from in game"() {
//        given:
//        Field field = new Field(10, 20,
//                "0,0,0,1,1,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,2,2,0,0,0,0,2,2;" +
//                        "0,0,0,2,2,0,0,0,2,2")
//        List<Shape> positions = [new Shape(ShapeType.S, new Point(5, 18)),
//                                 new Shape(ShapeType.S, new Point(4, 17)).turnLeft()]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        new Shape(ShapeType.S, new Point(4, 17)).turnLeft() == positions.first()
//    }
//
//    void "test S piece with position from in game 56401f2a1c687b457caf481f"() {
//        given:
//        Field field = new Field(10, 20,
//                "0,0,0,1,1,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,2,0,0,0,2,2,2,2,0;" +
//                        "2,2,2,0,0,2,2,2,2,0")
//        List<Shape> positions = [new Shape(ShapeType.S, new Point(0, 16)).turnRight(),
//                                 new Shape(ShapeType.S, new Point(2, 17)).turnLeft()]
//
//        when:
//        Collections.sort(positions, new FieldCompatator(field))
//
//        then:
//        new Shape(ShapeType.S, new Point(2, 17)).turnLeft() == positions.first()
//    }
//

//

//

//

}
