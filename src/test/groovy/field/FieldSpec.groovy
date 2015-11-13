package field

import bot.PlacementPermutator
import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class FieldSpec extends Specification {

    void "test isLocationEmpty for empty cell"() {
        given:
        Field field = new Field(2, 2, "0,0;0,0")
        Point p = new Point(1, 1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        true == rVal
    }

    void "test isLocationEmpty for invalid cell"() {
        given:
        Field field = new Field(2, 2, "0,0;0,0")
        Point p = new Point(-1, -1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        false == rVal
    }

    void "test isLocationEmpty for non empty cell"() {
        given:
        Field field = new Field(2, 2, "0,0;0,2")
        Point p = new Point(1, 1)

        when:
        boolean rVal = field.isLocationEmpty(p)

        then:
        false == rVal
    }

    void "test isValidPosition for I on left side of grid"() {
        given:
        Field field = FieldUtil.getEmptyField(4, 4)
        Shape shape = new Shape(ShapeType.I, new Point(-1, 0)).turnLeft()

        when:
        boolean rVal = field.isValidPosition(shape)

        then:
        true == rVal
    }

    void "test large J overhang"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,2,2,0,0,0;" +
                        "0,0,0,0,0,2,2,2,2,0;" +
                        "0,0,0,0,0,2,2,2,2,0;" +
                        "2,0,0,0,2,2,2,2,2,0;" +
                        "0,2,0,2,2,2,2,2,2,0;" +
                        "2,0,2,2,2,2,2,0,2,2;" +
                        "2,0,2,2,2,2,2,2,2,2;" +
                        "0,2,2,2,2,0,2,2,2,2;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        when:
        Field f1 = field.getResultingField(new Shape(ShapeType.J, new Point(7, 5)).turnRight().turnRight());
        Field f2 = field.getResultingField(new Shape(ShapeType.J, new Point(3, 6)).turnLeft())

        then:
        f2.getScore() > f1.getScore()
    }

    void "test limiting height. I shape empty field"() {
        given:
        Field field = FieldUtil.getEmptyField(10, 5)

        when:
        Field f1 = field.getResultingField(new Shape(ShapeType.I, new Point(3, 1)).turnRight());
        Field f2 = field.getResultingField(new Shape(ShapeType.I, new Point(3, 3)))

        then:
        f2.getScore() > f1.getScore()
    }

    void "test maximizing y"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "2,0,0,0,0,0,0,0,0,0;" +
                        "2,2,0,0,0,0,0,0,0,2;" +
                        "2,2,2,0,0,0,0,0,0,2;" +
                        "2,2,2,0,0,0,0,0,2,2;" +
                        "2,2,2,0,0,0,0,0,2,2;" +
                        "2,2,2,2,0,0,0,0,2,2;" +
                        "2,2,2,2,2,0,0,0,2,2;" +
                        "2,2,2,2,0,2,2,2,2,2;" +
                        "2,2,2,2,2,0,0,2,2,2;" +
                        "2,0,2,2,2,2,2,2,2,2;" +
                        "2,2,2,2,0,2,0,2,2,2;" +
                        "2,2,2,2,2,2,2,2,0,2;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        when:
        Field f1 = field.getResultingField(new Shape(ShapeType.J, new Point(-1, 2)).turnRight());
        Field f2 = field.getResultingField(new Shape(ShapeType.J, new Point(5, 10)))

        then:
        f2.getScore() > f1.getScore()
    }

    void "test column score"() {
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,1,0,0,0;" +
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
                        "2,0,0,0,2,0,0,0,2,2;" +
                        "2,0,0,0,2,0,2,2,2,2;" +
                        "2,0,0,0,2,2,2,2,2,2;" +
                        "2,2,0,2,2,2,2,2,2,2")

        when:
        Field f1 = field.getResultingField(new Shape(ShapeType.I, new Point(1, 15)).turnRight());
        Field f2 = field.getResultingField(new Shape(ShapeType.I, new Point(0, 16)).turnRight())

        then:
        f1.getScore() > f2.getScore()
    }

    void "test reducing columns results in better score"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "2,0,0,0,0,0,0,0,0,0;" +
                        "2,2,0,0,0,0,0,0,0,0;" +
                        "2,2,0,0,0,0,0,0,0,0;" +
                        "2,2,2,0,0,0,2,0,2,2;" +
                        "2,2,2,0,2,2,2,2,2,2;" +
                        "2,2,2,0,2,2,2,2,2,2;" +
                        "2,2,2,0,2,2,2,2,2,2;" +
                        "2,2,0,0,2,2,2,2,2,2;" +
                        "2,2,0,2,2,2,2,2,2,2;" +
                        "0,2,0,2,2,2,2,2,2,2;" +
                        "2,2,0,2,2,2,2,2,2,2;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        when:
        Field f1 = field.getResultingField(new Shape(ShapeType.T, new Point(3, 9)))
                .getResultingField(new Shape(ShapeType.I, new Point(5, 7)).turnRight());
        Field f2 = field.getResultingField(new Shape(ShapeType.T, new Point(6, 8)).turnRight().turnRight())
                .getResultingField(new Shape(ShapeType.I, new Point(1, 11)).turnRight());

        then:
        f1.getScore() < f2.getScore()
    }

    void "test board with spawn blocked is considered very bad"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,0,1,1,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,2,2,0,0,0,0,0;" +
                        "0,0,0,2,2,0,0,0,0,0;" +
                        "0,0,2,2,2,2,0,0,2,0;" +
                        "0,2,2,2,2,2,0,2,2,2;" +
                        "0,2,2,2,2,2,2,2,2,2;" +
                        "2,2,2,2,0,2,2,2,2,2;" +
                        "2,2,0,2,2,2,2,2,2,2;" +
                        "0,2,0,2,2,2,2,2,2,2;" +
                        "0,2,2,2,2,2,2,2,2,2;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        when:
        Field f1 = field.getResultingField(new Shape(ShapeType.O, new Point(3, 0)));
        Field f2 = field.getResultingField(new Shape(ShapeType.O, new Point(0, 3)));

        then:
        f1.getScore() < f2.getScore()
    }
}
