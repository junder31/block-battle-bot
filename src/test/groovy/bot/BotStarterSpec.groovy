package bot

import field.CellType
import field.Field
import spock.lang.Specification

import java.awt.Point

/**
 * Created by johnunderwood on 11/8/15.
 */
class BotStarterSpec extends Specification {
    public Field field

    void setup() {
        field = new Field(2, 2, "0,0;0,0")
    }

    void "test isLocationEmpty for empty cell"() {
        given:
        Point p = new Point(1,1)

        when:
        boolean rVal = BotStarter.isLocationEmpty(p, field)

        then:
        true == rVal
    }

    void "test isLocationEmpty for invalid cell"() {
        given:
        Point p = new Point(-1,-1)

        when:
        boolean rVal = BotStarter.isLocationEmpty(p, field)

        then:
        false == rVal
    }

    void "test isLocationEmpty for non empty cell"() {
        given:
        Point p = new Point(1,1)
        field.getCell(1, 1).state = CellType.BLOCK

        when:
        boolean rVal = BotStarter.isLocationEmpty(p, field)

        then:
        false == rVal
    }
}
