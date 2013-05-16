package othello;

/**
 * Represents the possible values a cell can have.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 17, 2012
 */
public enum Cell {

	/** The white cell */
	WHITE('W'),
	
	/** The black cell */
	BLACK('B'),
	
	/** The hole cell */
	HOLE('#');

	/**
	 * Tests whether a char belongs to a cell.
	 * 
	 * @param c
	 *        the char to test
	 * @return true if the char belongs to a cell
	 */
	public static boolean isCell(final char c) {
		return "WB#".contains(String.valueOf(c));
	}

	/**
	 * Tests whether a char is equal to the representation of a free cell.
	 * 
	 * @param c
	 *        the char to test
	 * @return true if the char is equal to the representation of a free cell
	 */
	public static boolean isFree(final char c) {
		return c == '-';
	}

	/**
	 * Returns the cell which belongs to a char. If there is no cell to a char an
	 * exception is thrown.
	 * 
	 * @param c
	 *        the char
	 * @return the cell which belongs to the char.
	 */
	public static Cell asCell(final char c) {
		switch (c) {
			case 'B':
				return BLACK;
			case 'W':
				return WHITE;
			case '#':
				return HOLE;
			default:
				throw new IllegalArgumentException("invalid cell sign: " + c);
		}
	}

	private final char sign;

	private Cell(final char sign) {
		this.sign = sign;
	}

	/**
	 * Returns the sign.
	 * 
	 * @return the sgin
	 */
	public char getSign() {
		return sign;
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
