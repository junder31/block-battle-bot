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

/**
 * Cell class
 * 
 * Represents one Cell in the playing field.
 * Has some basic methods already implemented.
 * 
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class Cell {
	private final Point location;
	private final CellType state;

	public Cell(int x, int y, CellType type) {
		this.location = new Point(x, y);
		this.state = type;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (location != null ? !location.equals(cell.location) : cell.location != null) return false;
        return state == cell.state;

    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + state.hashCode();
        return result;
    }

    public boolean isShape() {
		return this.state == CellType.SHAPE;
	}
	
	public boolean isSolid() {
		return this.state == CellType.SOLID;
	}
	
	public boolean isBlock() {
		return this.state == CellType.BLOCK;
	}
	
	public boolean isEmpty() {
		return this.state == CellType.EMPTY;
	}

	public CellType getState() {
		return this.state;
	}
	
	public Point getLocation() {
		return this.location;
	}
}
