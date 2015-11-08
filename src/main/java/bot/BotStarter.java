// Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;

import field.Cell;
import field.Field;
import field.Shape;
import field.ShapeType;
import log.Logger;
import moves.MoveType;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * BotStarter class
 * <p>
 * This class is where the main logic should be. Implement getMoves() to
 * return something better than random moves.
 *
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class BotStarter {
    private static Logger log = new Logger(BotStarter.class.getSimpleName());

    public BotStarter() {
    }

    /**
     * Returns a random amount of random moves
     *
     * @param state   : current state of the bot
     * @param timeout : time to respond
     * @return : a list of moves to execute
     */
    public ArrayList<MoveType> getMoves(BotState state, long timeout) {
        ArrayList<MoveType> moves = new ArrayList<>();

        List<Shape> possiblePlacements = getPossiblePlacements(state);
        log.debug("Possible placements %s", possiblePlacements);

        moves.add(MoveType.DROP);
        return moves;
    }

    public ArrayList<Shape> getPossiblePlacements(BotState state) {
        Field field = state.getMyField();
        ArrayList<Shape> placements = new ArrayList<>();


        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = field.getHeight() - 1; y >= 0; y--) {
                for (int i = 0; i < 4; i++) {
                    Shape shape = new Shape(state.getCurrentShape(), new Point(x, y));

                    for(int j = i; j > 0; j--) {
                        shape.turnRight();
                    }

                    if(isValidPosition(shape, field)) {
                        placements.add(shape);
                    }
                }
            }
        }

        return placements;
    }

    public boolean isValidPosition(Shape shape, Field field) {
        boolean isValid = true;
        log.trace("Evaluating position %s", shape.getLocation());

        if(!isShapeSupported(shape, field)) {
            log.trace("Invalid position %s. Shape is not supported.", shape.getLocation());
            isValid = false;
        } else if(areShapeCellsEmpty(shape, field)) {
            log.trace("Invalid position %s. Shape cells are not empty.", shape.getLocation());
            isValid = false;
        } else {
            log.trace("Position %s is valid.", shape.getLocation());
        }

        return isValid;
    }

    public static boolean isShapeSupported(Shape shape, Field field) {
        Cell[] cells = shape.getBlocks();

        for (Cell cell : cells) {
            Point p = cell.getLocation();
            p.setLocation(p.x, p.y + 1);
            if ( !isLocationEmpty(p, field) ) {
                return true;
            }
        }

        return false;
    }

    public static boolean areShapeCellsEmpty(Shape shape, Field field) {
        Cell[] cells = shape.getBlocks();

        for (Cell cell : cells) {
            Point p = cell.getLocation();
            if ( !isLocationEmpty(p, field) ) {
                return false;
            }
        }

        return true;
    }

    public static boolean isLocationEmpty(Point p, Field field) {
        Cell fieldCell = field.getCell(p.x, p.y);
        return fieldCell != null && (fieldCell.isEmpty() || fieldCell.isShape());
    }

    public Shape getCurrentShape(BotState state) {
        ShapeType type = state.getCurrentShape();
        Point location = state.getShapeLocation();
        return new Shape(type, location);
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
