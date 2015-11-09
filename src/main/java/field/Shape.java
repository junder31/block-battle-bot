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

import java.util.Arrays;

/**
 * Shape class
 * <p>
 * Represents the shapes that appear in the field.
 * Some basic methods have already been implemented, but
 * actual move actions, etc. should still be created.
 *
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class Shape {

    public final ShapeType type;
    private final boolean[][] cells; // 2-dimensional bounding box: a matrix that contains the block-cells of the cells
    private final Point location;
    private final ShapeOrientation orientation;

    public Shape(ShapeType type, Point location) {
        this.type = type;
        this.location = location;
        this.orientation = ShapeOrientation.UP;

        cells = getCells(type);
    }

    public Shape(Shape shape) {
        this(shape.type, new Point(shape.location));
    }

    private Shape(ShapeType type, Point location, boolean[][] cells, ShapeOrientation orientation) {
        this.type = type;
        this.location = location;
        this.cells = cells;
        this.orientation = orientation;
    }

    /**
     * Rotates the shape counter-clockwise
     */
    public Shape turnLeft() {
        boolean[][] temp = this.transposeShape();
        boolean[][] newCells = new boolean[type.size][type.size];
        for (int y = 0; y < type.size; y++) {
            for (int x = 0; x < type.size; x++) {
                newCells[x][y] = temp[x][type.size - y - 1];
            }
        }

        return new Shape(type, location, newCells, orientation.rotateLeft());
    }

    /**
     * Rotates the shape clockwise
     */
    public Shape turnRight() {
        boolean[][] temp = this.transposeShape();
        boolean[][] newCells = new boolean[type.size][type.size];
        for (int x = 0; x < type.size; x++) {
            newCells[x] = temp[type.size - x - 1];
        }

        return new Shape(type, location, newCells, orientation.rotateRight());
    }

    public Shape oneUp() {
        return new Shape(type, new Point(location.x, location.y - 1), cells, orientation);
    }

    public Shape oneDown() {
        return new Shape(type, new Point(location.x, location.y + 1), cells, orientation);
    }

    public Shape oneRight() {
        return new Shape(type, new Point(location.x + 1, location.y), cells, orientation);
    }

    public Shape oneLeft() {
        return new Shape(type, new Point(location.x - 1, location.y), cells, orientation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shape shape = (Shape) o;

        if (type != shape.type) return false;
        if (!Arrays.deepEquals(cells, shape.cells)) return false;
        if (!location.equals(shape.location)) return false;
        return orientation == shape.orientation;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + Arrays.deepHashCode(cells);
        result = 31 * result + location.hashCode();
        result = 31 * result + orientation.hashCode();
        return result;
    }

    /**
     * Used for rotations
     *
     * @return transposed matrix of current shape box
     */
    private boolean[][] transposeShape() {

        boolean[][] temp = new boolean[type.size][type.size];
        for (int y = 0; y < type.size; y++) {
            for (int x = 0; x < type.size; x++) {
                temp[y][x] = cells[x][y];
            }
        }

        return temp;
    }

    /**
     * Set cells in square box.
     * Creates new Cells that can be checked against the actual
     * playing field.
     */
    private static boolean[][] getCells(ShapeType type) {
        boolean[][] cells = new boolean[type.size][type.size];

        switch (type) {
            case I:
                cells[0][1] = true;
                cells[1][1] = true;
                cells[2][1] = true;
                cells[3][1] = true;
                break;
            case J:
                cells[0][0] = true;
                cells[0][1] = true;
                cells[1][1] = true;
                cells[2][1] = true;
                break;
            case L:
                cells[2][0] = true;
                cells[0][1] = true;
                cells[1][1] = true;
                cells[2][1] = true;
                break;
            case O:
                cells[0][0] = true;
                cells[1][0] = true;
                cells[0][1] = true;
                cells[1][1] = true;
                break;
            case S:
                cells[1][0] = true;
                cells[2][0] = true;
                cells[0][1] = true;
                cells[1][1] = true;
                break;
            case T:
                cells[1][0] = true;
                cells[0][1] = true;
                cells[1][1] = true;
                cells[2][1] = true;
                break;
            case Z:
                cells[0][0] = true;
                cells[1][0] = true;
                cells[1][1] = true;
                cells[2][1] = true;
                break;
        }

        return cells;
    }

    public Cell[] getBlocks() {
        Cell[] blocks = new Cell[4];
        int i = 0;

        for(int x = 0; x < type.size; x++) {
            for(int y = 0; y < type.size; y++) {
                if(cells[x][y]) {
                    blocks[i] = new Cell(location.x + x, location.y + y, CellType.SHAPE);
                    i++;
                }
            }
        }

        return blocks;
    }

    public Point getLocation() {
        return this.location;
    }

    public ShapeType getType() {
        return this.type;
    }

    public ShapeOrientation getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "Shape [x: " + location.x +
                ", y: " + location.y +
                ", type: " + type +
                ", orientation: " + orientation + "]";
    }
}
