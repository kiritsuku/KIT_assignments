package minipraktomat.commands;

import java.util.Collections;
import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Solution;
import minipraktomat.data.Task;



/**
 * Command for task summaries.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 9, 2012
 */
public class TaskSummaryCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public TaskSummaryCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 0;
	}

	/**
	 * Returns information of tasks. The order of output depends on the id of the
	 * task.
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
			final List<Solution> corrected = task.getCorrectedSolutions();
			final double average = task.averageGrade();
			final String averageGrade = average == -1 ? "-" : String.format("%.02f", average);
			final int[] distributions = distributions(corrected);

			result.append("\nsubmitted: ").append(solutions.size());
			result.append("\nreviewed: ").append(corrected.size());
			result.append("\naverage grade: ")
					.append(averageGrade);
			result.append("\ndistribution: ")
					.append(distributions[0]).append("x1, ")
					.append(distributions[1]).append("x2, ")
					.append(distributions[2]).append("x3, ")
					.append(distributions[3]).append("x4, ")
					.append(distributions[4]).append("x5");
		}
		return Validation.success(result.toString());
	}

	private int[] distributions(final List<Solution> solutions) {
		final int[] distributions = new int[5];
		for (final Solution solution : solutions) {
			distributions[solution.getReview().getGrade() - 1] += 1;
		}
		return distributions;
	}

}
