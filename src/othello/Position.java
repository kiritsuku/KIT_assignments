package othello;

/**
 * Represents a position object with a x- and a y-value.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 17, 2012
 */
public final class Position implements Comparable<Position> {

	/**
	 * Creates a new instance.
	 * 
	 * @param x
	 *        the x-value
	 * @param y
	 *        the y-value
	 * @return a new position object
	 */
	public static Position valueOf(final int x, final int y) {
		return new Position(x, y);
	}

	private final int x;
	private final int y;

	private Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x-value.
	 * 
	 * @return the x-value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-value.
	 * 
	 * @return the y-value
	 */
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(final Position pos) {
		final int comp = x - pos.x;
		return comp == 0 ? y - pos.y : comp;
	}

	@Override
	public String toString() {
		return (char) ('A' + x - 1) + String.valueOf(y);
	}

	@Override
	public int hashCode() {
		return x + 149 * y;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Position) {
			final Position pos = (Position) obj;
			return compareTo(pos) == 0;
		}
		return true;
	}
}
