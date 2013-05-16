package minipraktomat.commands;

import java.util.Collections;
import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Solution;
import minipraktomat.data.Task;



/**
 * Command for review summaries.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 9, 2012
 */
public class ReviewSummaryCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public ReviewSummaryCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 0;
	}

	/**
	 * Returns information of reviews. The order of output depends on the id of
	 * the task and on the id of the student who has submitted the solution.
	 * 
	 * @param arguments
	 *        no arguments expected
	 * @return a validation
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		final List<Task> tasks = praktomat.getTasks();
		Collections.sort(tasks);

		final StringBuilder result = new StringBuilder();
		for (final Task task : tasks) {
			if (result.length() != 0) {
				result.append("\n");
			}
			result.append("task id(").append(task.getId()).append("): ").append(task.getText());

			final List<Solution> solutions = task.getSolutions();
			Collections.sort(solutions);
			appendResultOfSolutions(solutions, result);
		}
		return Validation.success(result.toString());
	}

	private void appendResultOfSolutions(final List<Solution> solutions, final StringBuilder result) {
		for (int i = 0; i < solutions.size(); ++i) {
			final Solution solution = solutions.get(i);
			if (solutions.get(i).isCorrected()) {
				result.append("\n").append(solution.getStudent().getId()).append(": ")
						.append(solution.getReview().getGrade());
			}
		}
	}

}
