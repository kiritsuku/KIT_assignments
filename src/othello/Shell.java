package othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Represents a command line based interface to access the Othello engine.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 10, 2012
 */
public class Shell {

	private boolean isRunning = true;
	private Game game = Game.empty();

	/**
	 * The entry point of the application.
	 * 
	 * @param args
	 *        the arguments
	 */
	public static void main(final String... args) {
		new Shell().runShell();
	}

	/**
	 * Starts the shell.
	 */
	public void runShell() {
		while (isRunning) {
			final String input = Terminal.askString("othello> ").trim();
			if (!input.isEmpty()) {
				handleInput(input);
			}
		}
	}

	private void handleInput(final String input) {
		final List<String> args = Arrays.asList(input.split("\\s+"));
		final String command = args.get(0);

		try {
			final long start = System.nanoTime();
			handleCommand(command, args.subList(1, args.size()));
			System.out.println("time: " + (System.nanoTime()-start)/1e6 + "ms");
		} catch (final IllegalArgumentException e) {
			System.out.println("Error! " + e.getMessage());
		}
	}

	private void handleCommand(final String command, final List<String> args) {
		if ("newGame".equals(command)) {
			createNewGame(args);
		} else if ("hole".equals(command)) {
			createHole(args);
		} else if ("move".equals(command)) {
			move(args);
		} else if ("print".equals(command)) {
			print();
		} else if ("abort".equals(command)) {
			abort();
		} else if ("possibleMoves".equals(command)) {
			showPossibleMoves();
		} else if ("quit".equals(command)) {
			isRunning = false;
		} else {
			System.out.println("command not found");
		}
	}

	private void createNewGame(final List<String> args) {
		InputValidation.require(game.getMode().equals(GameMode.GAME_OVER_MODE),
				"there is already an active game");
		InputValidation.require(args.size() == 2 || args.size() == 3,
				"invalid number of arguments");

		final String rawWidth = args.get(0);
		final String rawHeight = args.get(1);

		final Option<Integer> width = InputValidation.parseInt(rawWidth);
		final Option<Integer> height = InputValidation.parseInt(rawHeight);
		InputValidation.require(width.isDefined() && height.isDefined(), "invalid number");

		game = args.size() == 2
				? Game.valueOf(width.get(), height.get())
				: Game.valueOf(width.get(), height.get(), args.get(2));
		if (!game.canMove()) {
			calculatePass();
		}
	}

	private void createHole(final List<String> args) {
		InputValidation.require(game.getMode().equals(GameMode.NEW_MODE),
				"can't add hole area. there is no game yet or the game has already started");
		InputValidation.require(args.size() == 1, "invalid number of arguments");

		final String[] hole = args.get(0).split(":");
		InputValidation.require(hole.length == 2, "invalid hole");

		final Position[] parsedHole = parseHole(
				hole[0].charAt(0), hole[0].substring(1),
				hole[1].charAt(0), hole[1].substring(1));
		final Position from = parsedHole[0];
		final Position to = parsedHole[1];

		InputValidation.require(!game.getBoard().containsCell(from, to),
				"can't add hole. it is not empty");
		game = game.addHole(from, to);
		if (!game.canMove()) {
			calculatePass();
		}
	}

	private void move(final List<String> args) {
		requireGameStarted();
		InputValidation.require(args.size() == 1, "invalid number of arguments");

		final String rawPos = args.get(0);
		final Position pos = parsePosition(rawPos.charAt(0), rawPos.substring(1));

		if (!game.getPossibleMoves().contains(pos)) {
			System.out.println("Move not possible.");
		} else {
			game = game.moveTo(pos);
			if (!game.canMove()) {
				calculatePass();
			}
		}
	}

	private void print() {
		requireGameStarted();
		final int h = game.getBoard().getHeight();
		final int w = game.getBoard().getWidth();
		final char[][] board = new char[h][w];

		for (final Map.Entry<Position, Cell> entry : game.getBoard().getCells().entrySet()) {
			final Position pos = entry.getKey();
			final Cell cell = entry.getValue();
			board[pos.getY() - 1][pos.getX() - 1] = cell.getSign();
		}

		final StringBuilder sb = new StringBuilder((h + 1) * w);
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				sb.append(board[i][j] == 0 ? '-' : board[i][j]);
			}
			sb.append('\n');
		}
		System.out.print(sb);
		System.out.println("turn: " + game.getCurPlayer());
	}

	private void abort() {
		requireGameStarted();
		game = game.endGame();
		calculateWinner();
	}

	private void showPossibleMoves() {
		requireGameStarted();
		final List<Position> possibleMoves = game.getPossibleMoves();
		Collections.sort(possibleMoves);

		final StringBuilder sb = new StringBuilder(possibleMoves.size() * 3);
		for (int i = 0; i < possibleMoves.size() - 1; ++i) {
			sb.append(possibleMoves.get(i)).append(",");
		}
		sb.append(possibleMoves.get(possibleMoves.size() - 1));

		System.out.println("Possible moves: " + sb);
	}

	private void requireGameStarted() {
		InputValidation.require(game.getMode().equals(GameMode.NEW_MODE)
				|| game.getMode().equals(GameMode.ACTIVE_MODE),
				"game not started");
	}

	private Position[] parseHole(final char x1, final String y1, final char x2, final String y2) {
		final Position pos1 = parsePosition(x1, y1);
		final Position pos2 = parsePosition(x2, y2);
		InputValidation.require(pos1.compareTo(pos2) <= 0,
				"second position must be greater than the first one");
		return new Position[] { pos1, pos2 };
	}

	private Position parsePosition(final char x, final String y) {
		final Option<Integer> parsedY = InputValidation.parseInt(y);
		InputValidation.require(parsedY.isDefined(), "invalid position");
		final Position pos = Position.valueOf(x - 'A' + 1, parsedY.get());
		InputValidation.require(game.getBoard().isInRange(pos), "invalid position");
		return pos;
	}

	private void calculatePass() {
		final Game passed = game.passMove();
		if (!passed.canMove()) {
			calculateWinner();
		} else {
			System.out.println(game.getCurPlayer() + " passes.");
			game = passed;
		}
	}

	private void calculateWinner() {
		game = game.endGame();

		final List<Cell> white = new ArrayList<Cell>();
		final List<Cell> black = new ArrayList<Cell>();
		for (final Cell cell : game.getBoard().getCells().values()) {
			if (cell.equals(Cell.WHITE)) {
				white.add(cell);
			} else if (cell.equals(Cell.BLACK)) {
				black.add(cell);
			}
		}

		final int numOfWhite = white.size();
		final int numOfBlack = black.size();

		if (numOfWhite == numOfBlack) {
			System.out.println("Game has ended in a draw.");
		} else {
			final Cell winner = numOfWhite > numOfBlack ? Cell.WHITE : Cell.BLACK;
			final int max = Math.max(numOfWhite, numOfBlack);
			final int min = Math.min(numOfWhite, numOfBlack);
			System.out.println(String.format("Game Over! %s has won (%d:%d)!", winner, max, min));
		}
	}

}
