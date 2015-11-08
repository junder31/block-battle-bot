package field

import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class ShapeOrientationSpec extends Specification {

    void "test rotateLeft"() {
        given:
        def orientation = ShapeOrientation.UP

        when:
        (1..4).each { orientation.rotateLeft() }

        then:
        ShapeOrientation.UP == orientation
    }

    void "test rotateRight"() {
        given:
        def orientation = ShapeOrientation.UP

        when:
        (1..4).each { orientation.rotateRight() }

        then:
        ShapeOrientation.UP == orientation
    }
}
