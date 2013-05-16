package minipraktomat.commands;

import java.util.Collections;
import java.util.List;

import minipraktomat.InputValidation;
import minipraktomat.Option;
import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Solution;
import minipraktomat.data.Task;



/**
 * Command to handle lists of solutions.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class ListSolutionsCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public ListSolutionsCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 1;
	}

	/**
	 * Returns information to the solutions of a task. The solutions are sorted
	 * by the id of the students who have submitted the solution.
	 * 
	 * @param arguments
	 *        the task id
	 * @return a validation
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		final Option<Integer> taskId = InputValidation.parseInt(arguments.get(0));
		if (!taskId.isDefined()) {
			return Validation.fail("invalid number");
		}
		final Option<Task> task = praktomat.findTask(taskId.get());
		if (!task.isDefined()) {
			return Validation.fail("task does not exist");
		}
		final List<Solution> solutions = task.get().getSolutions();
		Collections.sort(solutions);

		final StringBuilder sb = new StringBuilder();
		for (final Solution solution : solutions) {
			if (sb.length() != 0) {
				sb.append("\n");
			}
			sb.append("(").append(solution.getStudent().getId()).append(",")
					.append(solution.getStudent().getName()).append("): ")
					.append(solution.getText());
		}
		return Validation.success(sb.toString());
	}

}
