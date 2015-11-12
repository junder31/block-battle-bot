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

import field.Point;
import field.Shape;
import field.ShapeType;
import log.Logger;
import moves.MoveType;

import java.util.Collections;
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
    private int roundNum = 1;

    public BotStarter() {
    }

    /**
     * Returns a random amount of random moves
     *
     * @param state   : current state of the bot
     * @param timeout : time to respond
     * @return : a list of moves to execute
     */
    public List<MoveType> getMoves(BotState state, long timeout) {
        log.info("Starting Round %d", roundNum++);

        List<PlacementTree> possiblePlacements = new PlacementPermutator(state.getMyField(),
                state.getCurrentShape(),
                state.getNextShape(),
                1).getPossibleResultingFields();

        if (possiblePlacements.size() == 0) {
            throw new RuntimeException("Failed to find any possible placements for " + getCurrentShape(state) + ".");
        }

        Collections.sort(possiblePlacements);

        List<MoveType> moves = null;

        for (int i = 0; i < possiblePlacements.size(); i++) {
            Shape placement = possiblePlacements.get(i).shape;
            try {
                log.debug("Attempting to find path from %s to %s.", getCurrentShape(state), placement);
                moves = new PathFinder(getCurrentShape(state), placement, state.getMyField()).findPath();
                log.info("Moving shape to %s", placement);
                break;
            } catch (NoPathAvailableException ex) {
                log.warn("Could not find a path from %s to %s.", getCurrentShape(state), placement);
            }
        }

        if (moves == null) {
            log.error("Failed to find any series of moves.");
            moves.add(MoveType.DROP);
        }

        return moves;
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
