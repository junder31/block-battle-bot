package bot

import field.Field
import field.FieldUtil
import field.Point
import field.Shape
import field.ShapeType
import player.Player
import spock.lang.Specification

/**
 * Created by johnunderwood on 11/8/15.
 */
class BotStarterSpec extends Specification {
    public static BotStarter botStarter = new BotStarter();

    void "test finding possible locations"() {
        given:
        BotState state = new BotState();
        Field field = FieldUtil.getEmptyField(3, 4);
        Player player = new Player("p")
        player.field = field
        state.myBot = player
        state.currentShape = ShapeType.Z
        state.shapeLocation = new Point(0, -1)

        when:
        def placements = botStarter.getPossiblePlacements(state)

        then:
        6 == placements.size()
    }

    void "test finding possible locations for I"() {
        given:
        BotState state = new BotState();
        Field field = FieldUtil.getEmptyField(4, 4);
        Player player = new Player("p")
        player.field = field
        state.myBot = player
        state.currentShape = ShapeType.I
        state.shapeLocation = new Point(0, -1)

        Shape expectedShape = new Shape(ShapeType.I, new Point(-1,0))
        expectedShape.turnLeft()

        when:
        def placements = botStarter.getPossiblePlacements(state)

        then:
        placements.contains(expectedShape)
    }
}
