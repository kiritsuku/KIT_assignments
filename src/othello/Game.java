package othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Represents a game state of a player, his move possibilities and the next
 * player. It is not possible to change anything of a game state. If data have
 * to change, a new game state must be created and old data copied.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 17, 2012
 */
public class Game {

	private static final Map<Position, Cell> START_POSITIONS =
			Collections.unmodifiableMap(new HashMap<Position, Cell>() {
				{
					put(Position.valueOf(0, 0), Cell.WHITE);
					put(Position.valueOf(1, 0), Cell.BLACK);
					put(Position.valueOf(0, 1), Cell.BLACK);
					put(Position.valueOf(1, 1), Cell.WHITE);
				}
			});

	/**
	 * Creates an empty game object. Its board size is zero and it contains no
	 * cells. The start player is the black one.
	 * 
	 * @return an empty game object.
	 */
	public static Game empty() {
		return new Game(
				GameMode.GAME_OVER_MODE,
				new Board(0, 0, Collections.<Position, Cell> emptyMap()),
				Cell.BLACK);
	}

	/**
	 * Creates a new instance.
	 * <p>
	 * The width and height have to be an even positive number in range of the
	 * constants of {@link Board}.
	 * 
	 * @param width
	 *        the width of the game board
	 * @param height
	 *        the height of the game board
	 * @return a game object with the given data if they are correct. Otherwise
	 *         an exception is thrown.
	 */
	public static Game valueOf(final int width, final int height) {
		return valueOf(width, height, "");
	}

	/**
	 * Creates a new instance. It is possible to set start cells for both
	 * players. Each cell have to be represented by a single char, which belongs
	 * to the one set in {@link Cell}.
	 * <p>
	 * The rows of the board have to be separated by commas and their count must
	 * equal the height of the board and the length of each row must equal the
	 * width of the board. The width and height have to be an even positive
	 * number in range of the constants of {@link Board}.
	 * <p>
	 * Example start cell data for a board size of 4x4: #---,-WB-,-BW-,---#
	 * 
	 * @param width
	 *        the width of the game board
	 * @param height
	 *        the height of the game board
	 * @param data
	 *        the start cells for both players
	 * @return a game object with the given data if they are correct. Otherwise
	 *         an exception is thrown.
	 */
	public static Game valueOf(final int width, final int height, final String data) {
		InputValidation.require(
				isValidSize(width, Board.MIN_WIDTH, Board.MAX_WIDTH), "invalid width");
		InputValidation.require(
				isValidSize(height, Board.MIN_HEIGHT, Board.MAX_HEIGHT), "invalid height");

		final Map<Position, Cell> cells = data.isEmpty()
				? middleCells(width, height)
				: calculateCells(width, height, data);
		return new Game(GameMode.NEW_MODE, new Board(width, height, cells), Cell.BLACK);
	}

	private final GameMode mode;
	private final Board board;
	private final Cell curPlayer;

	private Game(final GameMode mode, final Board board, final Cell curPlayer) {
		this.mode = mode;
		this.board = board;
		this.curPlayer = curPlayer;
	}

	/**
	 * Returns the mode.
	 * 
	 * @return the mode
	 */
	public GameMode getMode() {
		return mode;
	}

	/**
	 * Returns the board.
	 * 
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the current player.
	 * 
	 * @return the current player
	 */
	public Cell getCurPlayer() {
		return curPlayer;
	}

	/**
	 * Returns the next player.
	 * 
	 * @return the next player
	 */
	public Cell getNextPlayer() {
		return curPlayer.equals(Cell.WHITE) ? Cell.BLACK : Cell.WHITE;
	}

	/**
	 * Calculates all possible moves the current player can do.
	 * 
	 * @return a list of positions.
	 */
	public List<Position> getPossibleMoves() {
		final Set<Position> positions = new HashSet<Position>();
		for (final Position pos : getCellsToProof()) {
			positions.addAll(getPossiblePositions(pos));
		}
		return new ArrayList<Position>(positions);
	}

	/**
	 * Checks whether the current player can move.
	 * 
	 * @return true if the player can move to a position
	 */
	public boolean canMove() {
		return !getPossibleMoves().isEmpty();
	}

	/**
	 * Lets the current player move to a position. A new game object with the
	 * transformed cells is returned.
	 * 
	 * @param pos
	 *        the position to move to
	 * @return a new game object with the transformed cells.
	 */
	public Game moveTo(final Position pos) {
		InputValidation.require(getPossibleMoves().contains(pos),
				"it is impossible to move to position " + pos);
		final Board newBoard = transformBy(pos);
		return new Game(GameMode.ACTIVE_MODE, newBoard, getNextPlayer());
	}

	/**
	 * Adds a hole to the board and creates a new game object with the new cells.
	 * 
	 * @param from
	 *        the start position of the hole
	 * @param to
	 *        the end position of the hole
	 * @return a new game object with the hole.
	 */
	public Game addHole(final Position from, final Position to) {
		if (board.containsCell(from, to)) {
			throw new IllegalArgumentException(String.format(
					"can't add hole (%s:%s). It is not empty", from, to));
		}
		final List<Position> positions = new ArrayList<Position>();
		for (int y = from.getY(); y <= to.getY(); ++y) {
			for (int x = from.getX(); x <= to.getX(); ++x) {
				positions.add(Position.valueOf(x, y));
			}
		}

		final Board newBoard = board.transformBy(positions, Cell.HOLE);
		return new Game(mode, newBoard, curPlayer);
	}

	/**
	 * Lets the current player pass his move and returns a new game object with
	 * the next player.
	 * 
	 * @return a new game object with the next player.
	 */
	public Game passMove() {
		return new Game(mode, board, getNextPlayer());
	}

	/**
	 * Aborts a game and returns a new game object with analogous game mode.
	 * 
	 * @return a new game object
	 */
	public Game endGame() {
		return new Game(GameMode.GAME_OVER_MODE, board, curPlayer);
	}

	private Board transformBy(final Position pos) {
		final List<Position> positions = new ArrayList<Position>();
		positions.add(pos);

		for (final Direction direction : Direction.DIRECTIONS) {
			final List<Position> p = getCellsToTransform(
					direction.apply(pos.getX(), pos.getY()),
					direction, new ArrayList<Position>());
			positions.addAll(p);
		}
		return board.transformBy(positions, curPlayer);
	}

	private List<Position> getCellsToTransform(final Position pos, final Direction direction,
			final List<Position> positions) {
		if (board.isOfPlayer(pos, getNextPlayer())) {
			positions.add(pos);
			return getCellsToTransform(direction.apply(pos.getX(), pos.getY()), direction, positions);
		}
		if (board.isOfPlayer(pos, curPlayer)) {
			return positions;
		}
		return Collections.emptyList();
	}

	private List<Position> getCellsToProof() {
		final List<Position> cellsToProof = new ArrayList<Position>();
		for (final Map.Entry<Position, Cell> entry : board.getCells().entrySet()) {
			if (entry.getValue().equals(curPlayer)) {
				cellsToProof.add(entry.getKey());
			}
		}
		return cellsToProof;
	}

	private List<Position> getPossiblePositions(final Position pos) {
		final List<Position> positions = new ArrayList<Position>();
		for (final Direction direction : Direction.DIRECTIONS) {
			final Option<Position> endPosition = checkDirection(pos, direction);
			if (endPosition.isDefined()) {
				positions.add(endPosition.get());
			}
		}
		return positions;
	}

	private Option<Position> checkDirection(final Position pos, final Direction direction) {
		final Position first = direction.apply(pos.getX(), pos.getY());

		if (board.isOfPlayer(first, getNextPlayer())) {
			return findEndPosition(direction.apply(first.getX(), first.getY()), direction);
		}
		return Option.none();
	}

	private Option<Position> findEndPosition(final Position pos, final Direction direction) {
		if (isInvalid(pos)) {
			return Option.none();
		}
		if (board.isFree(pos)) {
			return Option.some(pos);
		}
		return findEndPosition(direction.apply(pos.getX(), pos.getY()), direction);
	}

	private boolean isInvalid(final Position pos) {
		return !board.isInRange(pos) || board.isOfPlayer(pos, curPlayer) || board.isHole(pos);
	}

	private static Map<Position, Cell> middleCells(final int width, final int height) {
		final int xMiddle = width / 2;
		final int yMiddle = height / 2;

		final Map<Position, Cell> cells = new HashMap<Position, Cell>();
		for (final Map.Entry<Position, Cell> entry : START_POSITIONS.entrySet()) {
			final Position pos = entry.getKey();
			cells.put(Position.valueOf(pos.getX() + xMiddle, pos.getY() + yMiddle), entry.getValue());
		}
		return cells;
	}

	private static Map<Position, Cell> calculateCells(final int width, final int height,
			final String data) {
		InputValidation.require(isValidData(width, height, data), "invalid data");

		final Map<Position, Cell> cells = new HashMap<Position, Cell>();
		final String[] rows = data.split(",");
		for (int y = 1; y <= height; ++y) {
			for (int x = 1; x <= width; ++x) {
				final char c = rows[y - 1].charAt(x - 1);
				if (Cell.isCell(c)) {
					cells.put(Position.valueOf(x, y), Cell.asCell(c));
				}
			}
		}
		return cells;
	}

	private static boolean isValidData(final int width, final int height, final String data) {
		final String[] rows = data.split(",");
		if (rows.length != height) {
			return false;
		}
		for (final String row : rows) {
			if (!isRowValid(width, row)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isRowValid(final int width, final String row) {
		if (row.length() != width) {
			return false;
		}
		for (final char c : row.toCharArray()) {
			if (!Cell.isCell(c) && !Cell.isFree(c)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidSize(final int i, final int from, final int to) {
		return i >= from && i <= to && i % 2 == 0;
	}
}
