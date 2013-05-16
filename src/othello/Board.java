package othello;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the board of the game which has a specific size and which saves all
 * cells. It is not possible to change anything of a board. If the board has to
 * change a new bard must created and the old values copied.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 17, 2012
 */
public class Board {
	
	/** Maximum width of a board. */
	public static final int MAX_WIDTH = 26;
	
	/** Maximum height of a board. */
	public static final int MAX_HEIGHT = 98;
	
	/** Minimum width of a board. */
	public static final int MIN_WIDTH = 2;
	
	/** Minimum height of a board. */
	public static final int MIN_HEIGHT = 2;

	private final int width;
	private final int height;
	private final Map<Position, Cell> cells;

	/**
	 * Creates a new instance.
	 * 
	 * @param width
	 *        the width
	 * @param height
	 *        the height
	 * @param cells
	 *        the cells
	 */
	public Board(final int width, final int height, final Map<Position, Cell> cells) {
		this.width = width;
		this.height = height;
		this.cells = cells;
	}

	/**
	 * Returns the width.
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the cells.
	 * 
	 * @return the cells
	 */
	public Map<Position, Cell> getCells() {
		return new HashMap<Position, Cell>(cells);
	}

	/**
	 * Checks whether a cell at a position is free.
	 * 
	 * @param pos
	 *        the position
	 * @return true if the cell is free.
	 */
	public boolean isFree(final Position pos) {
		return isInRange(pos) && !cells.containsKey(pos);
	}

	/**
	 * Checks whether a position is in range of the board size.
	 * 
	 * @param pos
	 *        the position
	 * @return true if the position is in range.
	 */
	public boolean isInRange(final Position pos) {
		return pos.getX() > 0 && pos.getX() <= width && pos.getY() > 0 && pos.getY() <= height;
	}

	/**
	 * Checks whether a cell at a given position belongs to a player.
	 * 
	 * @param pos
	 *        the position
	 * @param player
	 *        the player
	 * @return true if the cell belongs to the player.
	 */
	public boolean isOfPlayer(final Position pos, final Cell player) {
		return cells.containsKey(pos) ? cells.get(pos).equals(player) : false;
	}

	/**
	 * Checks whether a cell at a given position is a hole.
	 * 
	 * @param pos
	 *        the position
	 * @return true if the cell is a hole.
	 */
	public boolean isHole(final Position pos) {
		return cells.containsKey(pos) ? cells.get(pos).equals(Cell.HOLE) : false;
	}

	/**
	 * Checks whether the area between between two positions contains a cell
	 * which belongs to a player.
	 * 
	 * @param from
	 *        the start position
	 * @param to
	 *        the end position
	 * @return true if a cell of a player is found.
	 */
	public boolean containsCell(final Position from, final Position to) {
		if (from.equals(to)) {
			return cells.containsKey(from) ? !cells.get(from).equals(Cell.HOLE) : false;
		}

		for (int y = from.getY(); y <= to.getY(); ++y) {
			for (int x = from.getX(); x <= to.getX(); ++x) {
				final Position pos = Position.valueOf(x, y);
				if (cells.containsKey(pos) && !cells.get(pos).equals(Cell.HOLE)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Creates entries between the positions and the cell and saves them to the
	 * cells already existing. Old values are overwritten if any found. A new
	 * board with the transformed cells is returned - the old one will not
	 * changed.
	 * 
	 * @param positions
	 *        the positions
	 * @param cell
	 *        the cell
	 * @return a new board with the transformed cells
	 */
	public Board transformBy(final List<Position> positions, final Cell cell) {
		final Map<Position, Cell> newCells = new HashMap<Position, Cell>(cells);
		for (final Position position : positions) {
			newCells.put(position, cell);
		}
		return new Board(width, height, newCells);
	}
}
