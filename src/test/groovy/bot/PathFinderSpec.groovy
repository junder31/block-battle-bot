package bot

import field.Field
import field.Shape
import field.ShapeType
import moves.MoveType
import spock.lang.Specification

import java.awt.Point

/**
 * Created by johnunderwood on 11/8/15.
 */
class PathFinderSpec extends Specification {

    void "test finding a path straight down on small empty field"() {
        given:
        Field field = getEmptyField(4, 4)
        Shape start = new Shape(ShapeType.Z, new Point(1,-1))
        Shape end = new Shape(ShapeType.Z, new Point(1, 2))

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        4 == moves.size()
        MoveType.DROP == moves.last()
        3 == moves.findAll { it == MoveType.DOWN }.size()
    }

    void "test finding a path straight down on full size empty field"() {
        given:
        Field field = getEmptyField(10, 20)
        Shape start = new Shape(ShapeType.Z, new Point(1,-1))
        Shape end = new Shape(ShapeType.Z, new Point(1, 18))

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        20 == moves.size()
        MoveType.DROP == moves.last()
        19 == moves.findAll { it == MoveType.DOWN }.size()
    }

    void "test finding a path offset on a small field"() {
        given:
        Field field = getEmptyField(4, 4)
        Shape start = new Shape(ShapeType.Z, new Point(1,-1))
        Shape end = new Shape(ShapeType.Z, new Point(0, 2))

        when:
        List<MoveType> moves = new PathFinder(start, end, field).findPath()

        then:
        5 == moves.size()
        MoveType.DROP == moves.last()
        3 == moves.findAll { it == MoveType.DOWN }.size()
        1 == moves.findAll { it == MoveType.LEFT }.size()
    }

    private Field getEmptyField(int width, int height) {
        String row = (1..width).collect { "0" }.join(",")
        String grid = (1..height).collect { row }.join(";")
        return new Field(width, height, grid)
    }
}
