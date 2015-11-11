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
 * <p>
 * Represents the playing field for one player.
 * Has some basic methods already implemented.
 *
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class Field {
    private static final long SCORE_SEGMENT_SIZE = 10000;
    private static Logger log = new Logger(Field.class.getSimpleName());

    private final int width;
    private final int height;
    private Cell[][] grid;
    private long score = -1;

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
        return fieldCell != null && fieldCell.isEmpty();
    }

    public Set<Set<Cell>> getEmptyRegions() {
        Set<Set<Cell>> emptyRegions = new HashSet<>();
        Set<Cell> usedCells = new HashSet<>();
        Set<CellType> empty = new HashSet<>();
        empty.add(CellType.EMPTY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = getCell(x, y);
                if (!usedCells.contains(cell) && cell.isEmpty()) {
                    Set<Cell> region = getConnectedCells(cell, empty);
                    emptyRegions.add(region);
                    usedCells.addAll(region);
                }
            }
        }

        return emptyRegions;
    }

    public Set<Cell> getConnectedCells(Point p, Set<CellType> types) {
        return getConnectedCells(getCell(p), types);
    }

    public Set<Cell> getConnectedCells(Cell cell, Set<CellType> types) {
        Set<Cell> connected = new HashSet<>();
        getConnectedCellsHelper(cell, types, connected);
        return connected;
    }

    public void getConnectedCellsHelper(Cell cell, Set<CellType> types, Set<Cell> connected) {
        if (cell != null && !connected.contains(cell) && types.contains(cell.getState())) {
            connected.add(cell);

            getConnectedCellsHelper(getLeftNeighbor(cell), types, connected);
            getConnectedCellsHelper(getRightNeighbor(cell), types, connected);
            getConnectedCellsHelper(getUpNeighbor(cell), types, connected);
            getConnectedCellsHelper(getDownNeighbor(cell), types, connected);
        }
    }

    public long getScore() {
        if (score < 0) {
            score = calculateScore();
        }
        return score;
    }

    private long calculateScore() {
        int maxHeight = getMaxHeight();
        long score = height - maxHeight < 4 ? 0 : 1;

        score = (SCORE_SEGMENT_SIZE * score) + calculateOpennessScorePart() - 1;
        score = (SCORE_SEGMENT_SIZE * score) + ((SCORE_SEGMENT_SIZE * (height - getMaxHeight())) / height) - 1;
        score = (SCORE_SEGMENT_SIZE * score) + calculateEmptyRegionScorePart() - 1;

        return score;
    }

    private long calculateEmptyRegionScorePart() {
        Set<Set<Cell>> emptyRegions = getEmptyRegions();
        int maxPerimeter = height * width * 4;
        int maxRegions = height * width;

        int perimeter = 0;
        for (Set<Cell> region : emptyRegions) {
            perimeter += getPerimeter(region);
        }

        long emptyScore = (900 * (maxPerimeter - perimeter)) / maxPerimeter +
                (100 * (maxRegions - emptyRegions.size())) / maxRegions;
        return (SCORE_SEGMENT_SIZE * emptyScore) / 1000;
    }

    public int getMaxHeight() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(!getCell(x, y).isEmpty()) {
                    return height - y;
                }
            }
        }

        return 0;
    }

    private int getPerimeter(Set<Cell> cells) {
        int perimeter = 0;

        for (Cell cell : cells) {
            perimeter += getNeighborCount(cell);
        }

        return perimeter;
    }

    private int getNeighborCount(Cell cell) {
        int neighbors = 0;

        Cell l = getLeftNeighbor(cell);
        if (l == null || !l.isEmpty()) {
            neighbors++;
        }
        Cell r = getRightNeighbor(cell);
        if (r == null || !r.isEmpty()) {
            neighbors++;
        }
        Cell u = getUpNeighbor(cell);
        if (u == null || !u.isEmpty()) {
            neighbors++;
        }
        Cell d = getDownNeighbor(cell);
        if (d == null || !d.isEmpty()) {
            neighbors++;
        }

        return neighbors;
    }

    private long calculateOpennessScorePart() {
        int overhangMultiplier = 10;
        int sideMultiplier = 6;
        long opennessMax = (height - 1) * width * 10;
        long openness = opennessMax;

        List<Cell> sideCells = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = getCell(x, y);
                if (cell.isEmpty()) {
                    if (y - 1 >= 0 && !getCell(x, y - 1).isEmpty()) {
                        openness -= overhangMultiplier * countEmptyBelow(cell);
                    }

                    if ( (x + 1 == width || !getCell(x + 1, y).isEmpty()) &&
                            (x - 1 < 0 || !getCell(x - 1, y).isEmpty())) {
                        sideCells.add(cell);
                    }
                }
            }
        }

        Collections.sort(sideCells, new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                int c = o1.getX() - o2.getX();
                if(c == 0) {
                    c = o1.getY() - o2.getY();
                }

                return c;
            }
        });

        int columnHeight = 0;
        int lastY = height;
        int columnCount = 0;
        int lastX = -1;
        for(int i = 0; i < sideCells.size(); i++) {
            if(lastX == sideCells.get(i).getX() && sideCells.get(i).getY() == lastY + 1) {
                columnHeight++;
            } else {
                if(columnHeight >= 3) {
                    columnCount++;
                    openness -= (columnHeight - 2) * Math.pow(sideMultiplier, columnCount);
                }
                columnHeight = 1;
            }
            lastY = sideCells.get(i).getY();
            lastX = sideCells.get(i).getX();
        }

        if(columnHeight >= 3) {
            columnCount++;
            openness -= (columnHeight - 2) * Math.pow(sideMultiplier, columnCount);
        }

        return (SCORE_SEGMENT_SIZE * openness) / opennessMax;
    }

    private int countEmptyBelow(Cell cell) {
        int count = 1;

        while (cell.getY() + 1 < height && getCell(cell.getX(), cell.getY() + 1).isEmpty()) {
            cell = getCell(cell.getX(), cell.getY() + 1);
            count++;
        }

        return count;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(getCell(x, y).getState().ordinal());
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public String printNeighborCounts() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(getNeighborCount(getCell(x, y)));
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public Field getResultingField(Shape shape) {
        Cell[][] grid = new Cell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = getCell(x, y);
            }
        }

        int y = 0;
        for(int x = 3; x <= 6; x++) {
            grid[x][y] = new Cell(x, y, CellType.EMPTY);
        }

        for (Cell cell : shape.getBlocks()) {
            grid[cell.getLocation().x][cell.getLocation().y] = cell;
        }

        return removeCompletedLines(new Field(grid));
    }

    private Field removeCompletedLines(Field field) {
        List<Integer> completedLines = getCompletedLines(field);

        if (completedLines.isEmpty()) {
            return field;
        } else {
            Cell[][] grid = new Cell[field.getWidth()][field.getHeight()];
            int i = 0;
            int y;
            for (y = field.getHeight() - 1; y >= 0; y--) {
                if (i < completedLines.size() && y == completedLines.get(i)) {
                    i++;
                } else {
                    for (int x = 0; x < field.getWidth(); x++) {
                        grid[x][y + i] = new Cell(x, y + i, field.getCell(x, y).getState());
                    }
                }
            }

            for (y = 0; y < i; y++) {
                for (int x = 0; x < field.getWidth(); x++) {
                    grid[x][y] = new Cell(x, y, CellType.EMPTY);
                }
            }

            return new Field(grid);
        }
    }

    private List<Integer> getCompletedLines(Field field) {
        List<Integer> completedLines = new ArrayList<>();

        for (int y = field.getHeight() - 1; y >= 0; y--) {
            boolean isComplete = true;

            for (int x = 0; x < field.getWidth(); x++) {
                Cell c = field.getCell(x, y);
                if (c.isEmpty() || c.isSolid()) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                completedLines.add(y);
            }
        }

        return completedLines;
    }
}
