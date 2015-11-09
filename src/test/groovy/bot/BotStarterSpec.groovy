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

        Shape expectedShape = new Shape(ShapeType.I, new Point(-1,0)).turnLeft()

        when:
        def placements = botStarter.getPossiblePlacements(state)

        then:
        placements.contains(expectedShape)
    }

    void "test unintended mutation from game"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,0,0,0,0,0;" +
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
                        "0,0,2,2,0,0,0,0,2,2;" +
                        "0,0,0,2,2,0,0,0,2,2")
        BotState state = new BotState();
        Player player = new Player("p")
        player.field = field
        state.myBot = player
        state.currentShape = ShapeType.S
        state.shapeLocation = new Point(0, -1)

        when:
        def placements = botStarter.getPossiblePlacements(state)
        Collections.sort(placements, new PlacementComparator(state.getMyField()));

        then:
        new Shape(ShapeType.S, new Point(4, 17)).turnLeft() == placements.first() ||
                new Shape(ShapeType.S, new Point(3, 17)).turnRight() == placements.first()
    }
}
