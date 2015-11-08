package bot

import field.Field
import field.FieldUtil
import field.Point
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
        5 == placements.size()
    }
}
