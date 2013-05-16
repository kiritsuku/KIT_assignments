package othello;

import java.util.Arrays;
import java.util.List;


/**
 * Represents one of the eight possible directions a cell can move to.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 18, 2012
 */
public interface Direction {

	/** The right side */
	Direction RIGHT = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x + 1, y);
		}
	};

	/** The right down side */
	Direction RIGHT_DOWN = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x + 1, y + 1);
		}
	};

	/** The down side */
	Direction DOWN = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x, y + 1);
		}
	};

	/** The left down side */
	Direction LEFT_DOWN = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x - 1, y + 1);
		}
	};

	/** The left side */
	Direction LEFT = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x - 1, y);
		}
	};

	/** The left up side */
	Direction LEFT_UP = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x - 1, y - 1);
		}
	};

	/** The up side */
	Direction UP = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x, y - 1);
		}
	};

	/** The right up side */
	Direction RIGHT_UP = new Direction() {
		public Position apply(final int x, final int y) {
			return Position.valueOf(x + 1, y - 1);
		}
	};

	/** The eight possible directions */
	List<Direction> DIRECTIONS = Arrays.asList(
			RIGHT, RIGHT_DOWN, DOWN, LEFT_DOWN, LEFT, LEFT_UP, UP, RIGHT_UP);

	/**
	 * If this method is called a position object is returned which belongs to
	 * the x- and y-value. The x- and y-value are changed in the way the
	 * direction defines it and never more than one point.
	 * 
	 * @param x
	 *        the x-value
	 * @param y
	 *        the y-value
	 * @return the new position
	 */
	Position apply(int x, int y);
}
