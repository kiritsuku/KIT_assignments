package minipraktomat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minipraktomat.commands.ListSolutionsCommand;
import minipraktomat.commands.ListStudentsCommand;
import minipraktomat.commands.PartialShellCommand;
import minipraktomat.commands.ReviewCommand;
import minipraktomat.commands.ReviewSummaryCommand;
import minipraktomat.commands.ShellCommand;
import minipraktomat.commands.StudentCommand;
import minipraktomat.commands.StudentSummaryCommand;
import minipraktomat.commands.SubmitCommand;
import minipraktomat.commands.TaskCommand;
import minipraktomat.commands.TaskSummaryCommand;
import minipraktomat.commands.TutorCommand;
import minipraktomat.commands.TutorSummaryCommand;



/**
 * Represents a command line based interface to access the Praktomat.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 10, 2012
 */
public class Shell {

	private boolean isRunning = true;
	private Map<String, ShellCommand> commands;
	private Praktomat praktomat;

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
	 * Creates a new instance of the shell and initializes it.
	 */
	public Shell() {
		init();
	}

	/**
	 * Initializes the shell.
	 */
	public void init() {
		praktomat = new Praktomat();
		commands = new HashMap<String, ShellCommand>();
		
		commands.put("tut", new TutorCommand(praktomat));
		commands.put("stud", new StudentCommand(praktomat));
		commands.put("task", new TaskCommand(praktomat));
		commands.put("list-students", new ListStudentsCommand(praktomat));
		commands.put("submit", new SubmitCommand(praktomat));
		commands.put("review", new ReviewCommand(praktomat));
		commands.put("list-solutions", new ListSolutionsCommand(praktomat));
		commands.put("results", new ReviewSummaryCommand(praktomat));
		commands.put("summary-task", new TaskSummaryCommand(praktomat));
		commands.put("summary-tutor", new TutorSummaryCommand(praktomat));
		commands.put("summary-student", new StudentSummaryCommand(praktomat));

		commands.put("quit", new PartialShellCommand(praktomat) {

			@Override
			public void handle() {
				isRunning = false;
			}
		});
		commands.put("reset", new PartialShellCommand(praktomat) {

			@Override
			public void handle() {
				init();
			}
		});
	}

	/**
	 * Starts the shell.
	 */
	public void runShell() {
		while (isRunning) {
			final String input = Terminal.askString("praktomat> ").trim();
			if (!input.isEmpty()) {
				handleInput(input);
			}
		}
	}

	private void handleInput(final String input) {
		final List<String> args = Arrays.asList(input.split("\\s+"));
		final String command = args.get(0);

		if (commands.containsKey(command)) {
			handleCommand(commands.get(command), args.subList(1, args.size()));
		} else {
			error("command not found");
		}
	}

	private void handleCommand(final ShellCommand command, final List<String> arguments) {
		final Validation<String, String> result = command.execute(arguments);

		if (result.isFailure()) {
			error(result.getFailure());
		} else if (!result.getSuccess().isEmpty()) {
			System.out.println(result.getSuccess());
		}
	}

	private void error(final String error) {
		System.out.println("Error! " + error);
	}
}
