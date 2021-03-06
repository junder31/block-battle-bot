package bot

import field.*
import moves.MoveType
import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class PathFinderSpec extends Specification {

    void "test finding a path straight down on small empty field"() {
        given:
        Field field = FieldUtil.getEmptyField(4, 4)
        Shape start = new Shape(ShapeType.Z, new Point(1, -1))
        Shape end = new Shape(ShapeType.Z, new Point(1, 2))

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        1 == moves.size()
        MoveType.DROP == moves.last()
    }

    void "test finding a path straight down on full size empty field"() {
        given:
        Field field = FieldUtil.getEmptyField(10, 20)
        Shape start = new Shape(ShapeType.Z, new Point(1, -1))
        Shape end = new Shape(ShapeType.Z, new Point(1, 18))

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        1 == moves.size()
        MoveType.DROP == moves.last()
    }

    void "test finding a path offset on a small field"() {
        given:
        Field field = FieldUtil.getEmptyField(4, 4)
        Shape start = new Shape(ShapeType.Z, new Point(1, -1))
        Shape end = new Shape(ShapeType.Z, new Point(0, 2))

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        MoveType.DROP == moves.last()
        1 == moves.findAll { MoveType.LEFT == it }.size()
    }

    void "test impossible path from round 3 563ffd5f35ec1d12df1dab35"() {
        given:
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
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,2,2,2,2,0,0,0;" +
                        "0,0,2,2,2,2,0,0,0,0")
        Shape start = new Shape(ShapeType.I, new Point(3, -1))
        Shape end = new Shape(ShapeType.I, new Point(6, 17)).turnRight().turnRight()

        when:
        new PathFinder(start, end, field).findPath()

        then:
        thrown(NoPathAvailableException)
    }

    void "test path missing rotation from round 4 564000dd1c687b457caf473a"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,0,0,0,0;" +
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
                        "0,0,2,2,0,0,0,0,0,0;" +
                        "2,2,2,2,2,0,0,0,0,0;" +
                        "2,2,2,2,2,0,0,0,0,0")
        Shape start = new Shape(ShapeType.L, new Point(3, -1))
        Shape end = new Shape(ShapeType.L, new Point(4, 17)).turnRight()

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        moves.any { it == MoveType.TURNLEFT || it == MoveType.TURNRIGHT }
    }

    void "test L spin"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,0,0,0,0;" +
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
                        "0,0,0,2,2,2,2,0,2,0;" +
                        "0,0,0,2,2,0,0,0,2,2;" +
                        "2,0,2,2,2,2,2,2,2,2;" +
                        "2,2,2,0,2,2,2,2,2,2;" +
                        "0,2,2,0,2,2,2,2,2,2;" +
                        "2,2,2,2,2,2,0,2,2,2;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")
        Shape start = new Shape(ShapeType.L, new Point(3, -1))
        Shape end = new Shape(ShapeType.L, new Point(5, 12))

        when:
        new PathFinder(start, end, field).findPath()

        then:
        thrown(NoPathAvailableException)
    }

    void "test finding path for I with high columns right side"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,1,0,0,0;" +
                        "0,0,0,0,0,0,0,2,0,0;" +
                        "0,0,0,0,0,0,2,2,0,0;" +
                        "0,0,0,0,2,0,2,2,0,0;" +
                        "0,0,2,2,2,0,2,2,2,0;" +
                        "0,0,2,2,2,0,2,2,2,0;" +
                        "0,0,2,2,0,0,2,2,2,0;" +
                        "0,0,2,0,0,0,2,2,2,0;" +
                        "2,0,2,2,2,0,2,2,2,0;" +
                        "2,2,2,2,2,0,2,2,2,0;" +
                        "2,2,2,2,2,0,2,2,2,0;" +
                        "2,2,2,2,2,0,2,2,2,0;" +
                        "0,2,2,2,2,0,2,2,2,0;" +
                        "0,2,2,2,2,0,2,2,2,0;" +
                        "0,2,2,2,2,2,2,2,2,0;" +
                        "0,2,2,2,2,2,2,2,2,0;" +
                        "0,2,2,2,2,2,2,2,2,2;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;")
        Shape start = new Shape(ShapeType.I, new Point(3, -1));
        Shape end = new Shape(ShapeType.I, new Point(7, 12)).turnRight();

        when:
        def moves = new PathFinder(start, end, field).findPath();

        then:
        moves.contains(MoveType.DROP)
    }

    void "test finding path for J with blocks near top"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,0,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,0,2,0,0,0,0,0;" +
                        "2,2,2,0,2,2,2,0,0,0;" +
                        "2,2,2,0,2,2,2,2,0,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,0,0,2,2,2;" +
                        "2,2,2,2,2,0,2,2,2,2;" +
                        "0,2,2,2,2,0,2,2,2,2;" +
                        "2,0,2,2,2,2,2,2,2,2;" +
                        "2,2,0,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "2,2,0,2,2,2,2,2,2,0;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        Shape start = new Shape(ShapeType.J, new Point(3, -1));
        Shape end = new Shape(ShapeType.J, new Point(5, 1)).turnRight().turnRight();

        when:
        def moves = new PathFinder(start, end, field).findPath();

        then:
        moves.contains(MoveType.TURNLEFT) || moves.contains(MoveType.TURNRIGHT)
    }
}
