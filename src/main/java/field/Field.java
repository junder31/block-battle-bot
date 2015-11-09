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

package field;

import log.Logger;

import java.util.*;

/**
 * Field class
 * <p/>
 * Represents the playing field for one player.
 * Has some basic methods already implemented.
 *
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class Field {
    private static Logger log = new Logger(Field.class.getSimpleName());

    private final int width;
    private final int height;
    private Cell[][] grid;

    public Field(int width, int height, String fieldString) {
        this.width = width;
        this.height = height;

        parse(fieldString);
    }

    public Field(Cell[][] grid) {
        width = grid.length;
        height = grid.length > 0 ? grid[0].length : 0;
        this.grid = grid;
    }

    /**
     * Parses the input string to get a grid with Cell objects
     *
     * @param fieldString : input string
     */
    private void parse(String fieldString) {

        this.grid = new Cell[this.width][this.height];

        // get the separate rows
        String[] rows = fieldString.split(";");
        for (int y = 0; y < this.height; y++) {
            String[] rowCells = rows[y].split(",");

            // parse each cell of the row
            for (int x = 0; x < this.width; x++) {
                int cellCode = Integer.parseInt(rowCells[x]);
                this.grid[x][y] = new Cell(x, y, CellType.values()[cellCode]);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height)
            return null;
        return this.grid[x][y];
    }

    public Cell getCell(Point p) {
        return getCell(p.x, p.y);
    }

    public boolean isValidPosition(Shape shape) {
        boolean isValid = true;
        log.trace("Evaluating position %s", shape.getLocation());

        if (!isShapeSupported(shape)) {
            log.trace("Invalid position %s. Shape is not supported.", shape.getLocation());
            isValid = false;
        } else if (!areShapeCellsEmpty(shape)) {
            log.trace("Invalid position %s. Shape cells are not empty.", shape.getLocation());
            isValid = false;
        } else {
            log.trace("Position %s is valid.", shape.getLocation());
        }

        return isValid;
    }

    public boolean isShapeSupported(Shape shape) {
        Cell[] cells = shape.getBlocks();

        for (Cell cell : cells) {
            Point p = new Point(cell.getLocation().x, cell.getLocation().y + 1);
            if (!isLocationEmpty(p)) {
                return true;
            }
        }

        return false;
    }

    public boolean areShapeCellsEmpty(Shape shape) {
        Cell[] cells = shape.getBlocks();

        for (Cell cell : cells) {
            Point p = cell.getLocation();
            if (!isLocationEmpty(p)) {
                return false;
            }
        }

        return true;
    }

    public boolean isLocationEmpty(Point p) {
        Cell fieldCell = getCell(p.x, p.y);
        return fieldCell != null && (fieldCell.isEmpty() || fieldCell.isShape());
    }

    public Set<Cell> getConnectedCells(Point p, Set<CellType> types) {
        return getConnectedCells(getCell(p), types);
    }

    public Set<Cell> getConnectedCells(Cell cell, Set<CellType> types) {
        Set<Cell> connected = new HashSet<>();
        getConnectedCellsHelper(cell, types, connected);
        return connected;
    }

    public void getConnectedCellsHelper(Cell cell , Set<CellType> types, Set<Cell> connected) {
        if(cell != null && !connected.contains(cell) && types.contains(cell.getState())) {
            connected.add(cell);

            getConnectedCellsHelper(getLeftNeighbor(cell), types, connected);
            getConnectedCellsHelper(getRightNeighbor(cell), types, connected);
            getConnectedCellsHelper(getUpNeighbor(cell), types, connected);
            getConnectedCellsHelper(getDownNeighbor(cell), types, connected);
        }
    }

    public Cell getLeftNeighbor(Cell cell) {
        return getCell(cell.getX() - 1, cell.getY());
    }

    public Cell getRightNeighbor(Cell cell) {
        return getCell(cell.getX() + 1, cell.getY());
    }

    public Cell getUpNeighbor(Cell cell) {
        return getCell(cell.getX(), cell.getY() - 1);
    }

    public Cell getDownNeighbor(Cell cell) {
        return getCell(cell.getX(), cell.getY() + 1);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
