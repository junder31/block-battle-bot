package bot

import field.Field
import field.Point
import field.Shape
import field.ShapeType
import field.FieldUtil;
import spock.lang.Specification

/**
 * Created by johnunderwood on 11/9/15.
 */
class PlacementTreeSpec extends Specification {

//    void "test finding possible locations"() {
//        given:
//        BotState state = new BotState();
//        Field field = FieldUtil.getEmptyField(3, 4);
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.Z
//        state.shapeLocation = new Point(0, -1)
//
//        when:
//        def placements = botStarter.getPossiblePlacements(state)
//
//        then:
//        6 == placements.size()
//    }
//
//    void "test finding possible locations for I"() {
//        given:
//        BotState state = new BotState();
//        Field field = FieldUtil.getEmptyField(4, 4);
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.I
//        state.shapeLocation = new Point(0, -1)
//
//        Shape expectedShape = new Shape(ShapeType.I, new Point(-1, 0)).turnLeft()
//
//        when:
//        def placements = botStarter.getPossiblePlacements(state)
//
//        then:
//        placements.contains(expectedShape)
//    }
//
//    void "test unintended mutation from game"() {
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
//        BotState state = new BotState();
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.S
//        state.shapeLocation = new Point(0, -1)
//
//        when:
//        def placements = botStarter.getPossiblePlacements(state)
//        Collections.sort(placements, new FieldCompatator(state.getMyField()));
//
//        then:
//        new Shape(ShapeType.S, new Point(4, 17)).turnLeft() == placements.first()
//    }
//
//    void "test bade placement from round 4 564000dd1c687b457caf473a"() {
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
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,2,2,0,0,0,0,0,0;" +
//                        "2,2,2,2,2,0,0,0,0,0;" +
//                        "2,2,2,2,2,0,0,0,0,0")
//        BotState state = new BotState();
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.L
//        state.shapeLocation = new Point(3, -1)
//
//        when:
//        def moves = botStarter.getMoves(state, 100000)
//
//        then:
//        moves.any { it == MoveType.TURNLEFT || it == MoveType.TURNRIGHT }
//    }
//
//    void "test bad placement from round 8 5640005c35ec1d12df1dab51"() {
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
//        BotState state = new BotState();
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.T
//        state.shapeLocation = new Point(3, -1)
//
//        when:
//        def placements = botStarter.getPossiblePlacements(state)
//        Collections.sort(placements, new FieldCompatator(state.getMyField()));
//
//        then:
//        new Shape(ShapeType.T, new Point(6, 18)) == placements.first()
//    }
//
//    void "test bad placement from round 4 56401f2a1c687b457caf481f"() {
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
//        BotState state = new BotState();
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.S
//        state.shapeLocation = new Point(3, -1)
//
//        when:
//        def placements = botStarter.getPossiblePlacements(state)
//        Collections.sort(placements, new FieldCompatator(state.getMyField()));
//
//        then:
//        new Shape(ShapeType.S, new Point(2, 17)).turnLeft() == placements.first()
//    }
//
//    void "test bad placement from round 46 564026ea35ec1d12df1daced"() {
//        given:
//        Field field = new Field(10, 20,
//                "0,0,0,1,1,1,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "0,0,0,0,0,0,0,0,0,0;" +
//                        "2,0,0,0,0,0,0,0,0,0;" +
//                        "2,2,0,0,0,0,0,0,0,2;" +
//                        "2,2,2,0,0,0,0,0,0,2;" +
//                        "2,2,2,0,0,0,0,0,2,2;" +
//                        "2,2,2,0,0,0,0,0,2,2;" +
//                        "2,2,2,2,0,0,0,0,2,2;" +
//                        "2,2,2,2,2,0,0,0,2,2;" +
//                        "2,2,2,2,0,2,2,2,2,2;" +
//                        "2,2,2,2,2,0,0,2,2,2;" +
//                        "2,0,2,2,2,2,2,2,2,2;" +
//                        "2,2,2,2,0,2,0,2,2,2;" +
//                        "2,2,2,2,2,2,2,2,0,2;" +
//                        "3,3,3,3,3,3,3,3,3,3;" +
//                        "3,3,3,3,3,3,3,3,3,3;" +
//                        "3,3,3,3,3,3,3,3,3,3")
//        BotState state = new BotState();
//        Player player = new Player("p")
//        player.field = field
//        state.myBot = player
//        state.currentShape = ShapeType.J
//        state.shapeLocation = new Point(3, -1)
//
//        when:
//        def placements = botStarter.getPossiblePlacements(state)
//        Collections.sort(placements, new FieldCompatator(state.getMyField()));
//
//        then:
//        new Shape(ShapeType.J, new Point(5, 10)) == placements.first()
//    }

    void "test sorting PlacementTrees"() {
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
        Shape s1 = new Shape(ShapeType.J, new Point(-1, 2)).turnRight();
        Field f1 = field.getResultingField(s1);
        Shape s2 = new Shape(ShapeType.J, new Point(5, 10))
        Field f2 = field.getResultingField(s2)

        when:
        def placementTrees = [new PlacementTree(s1, f1), new PlacementTree(s2, f2)]

        then:
        s2 == placementTrees.sort().first().shape
    }

    void "test I piece takes tetris when avaliable"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,1,1,1,1,0,0,0;" +
                        "0,0,0,0,0,0,0,0,0,0;" +
                        "0,0,0,2,2,2,0,0,0,0;" +
                        "0,0,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,0,0,0;" +
                        "0,2,2,2,2,2,2,2,0,0;" +
                        "0,2,2,2,2,2,2,2,0,0;" +
                        "0,2,2,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "2,2,2,2,2,2,2,2,2,0;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        when:
        def placementTrees = new PlacementPermutator(field, ShapeType.I, ShapeType.T).getPossibleResultingFields()
        Collections.sort(placementTrees);

        then:
        println(placementTrees.first().field)
        new Shape(ShapeType.I, new Point(7, 14)).turnRight().getBlocks() == placementTrees.first().shape.getBlocks()
    }

    void "test placing I when columns exist and row count high"() {
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

        when:
        def placementTrees = new PlacementPermutator(field, ShapeType.I, ShapeType.S).getPossibleResultingFields()
        Collections.sort(placementTrees);

        then:
        println(placementTrees.first().field)
        new Shape(ShapeType.I, new Point(3, 10)).turnRight().getBlocks() == placementTrees.first().shape.getBlocks()
    }

    void "test not placing on top of empty cell"() {
        given:
        Field field = new Field(10, 20,
                "0,0,0,0,1,1,0,0,0,0;" +
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
                        "0,0,2,0,0,2,2,2,2,0;" +
                        "0,2,2,2,2,2,0,2,2,2;" +
                        "0,2,2,2,2,2,2,2,2,2;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3;" +
                        "3,3,3,3,3,3,3,3,3,3")

        when:
        def placementTrees = new PlacementPermutator(field, ShapeType.O, ShapeType.O).getPossibleResultingFields()
        Collections.sort(placementTrees);

        then:
        println(placementTrees.first().field)
        new Shape(ShapeType.O, new Point(3, 13)).getBlocks() == placementTrees.first().shape.getBlocks()
    }

    void "test not creating multiple columns"() {
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
                        "2,0,0,0,2,0,0,0,2,2;" +
                        "2,0,0,0,2,0,2,2,2,2;" +
                        "2,0,0,0,2,2,2,2,2,2;" +
                        "2,2,0,2,2,2,2,2,2,2")

        when:
        def placementTrees = new PlacementPermutator(field, ShapeType.I, ShapeType.L).getPossibleResultingFields()
        Collections.sort(placementTrees);

        then:
        println(placementTrees.first().field)
        new Shape(ShapeType.I, new Point(0, 16)).turnRight().getBlocks() != placementTrees.first().shape.getBlocks()
    }

    void "test blocking column when next move results in tetris"() {
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
        def placementTrees = new PlacementPermutator(field, ShapeType.T, ShapeType.I).getPossibleResultingFields()
        Collections.sort(placementTrees);

        then:
        println(placementTrees.first().field)
        new Shape(ShapeType.I, new Point(6, 8)).turnRight().turnRight().getBlocks() != placementTrees.first().shape.getBlocks()
    }

    void "test blocking spawn point is considered worst move ever"() {
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
        def placementTrees = new PlacementPermutator(field, ShapeType.O, ShapeType.S).getPossibleResultingFields()
        Collections.sort(placementTrees);

        then:
        println(placementTrees.first().field)
        new Shape(ShapeType.O, new Point(3, 0)).getBlocks() != placementTrees.first().shape.getBlocks()
    }
}
