package minipraktomat.commands;

import java.util.List;

import minipraktomat.InputValidation;
import minipraktomat.Option;
import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Solution;
import minipraktomat.data.Student;
import minipraktomat.data.Task;



/**
 * Command for solution handling.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class SubmitCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public SubmitCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 3;
	}

	/**
	 * Tries to create a new solution. If it could be created no special message
	 * is returned, otherwise an error message.
	 * 
	 * @param arguments
	 *        the text, the student id, the task id
	 * @return on success no special message or an error message
	 */
	@Override
	protected Validation<String, String> handleParameters(
			final List<String> arguments) {
		final Option<Integer> taskId = InputValidation.parseInt(arguments.get(0));
		final Option<Integer> studentId = InputValidation.parseInt(arguments.get(1));
		final String text = arguments.get(2);
		if (!studentId.isDefined() || !taskId.isDefined()) {
			return Validation.fail("invalid number");
		}
		final Validation<String, Solution> solution = createSolution(
				text, studentId.get(), taskId.get());
		if (solution.isFailure()) {
			return Validation.fail(solution.getFailure());
		}
		return Validation.success("");
	}

	private Validation<String, Solution> createSolution(final String text,
			final int studentId, final int taskId) {
		if (text.contains(" ")) {
			return Validation.fail("invalid text");
		}
		final Option<Task> task = praktomat.findTask(taskId);
		if (!task.isDefined()) {
			return Validation.fail("task does not exist");
		}
		final Option<Student> student = praktomat.findStudent(studentId);
		if (!student.isDefined()) {
			return Validation.fail("student does not exist");
		}
		final Option<Solution> existingSolution = task.get().findSolution(
				student.get());
		if (existingSolution.isDefined()) {
			if (existingSolution.get().isCorrected()) {
				return Validation.fail("solution already exists");
			}
			task.get().removeSolution(existingSolution.get());
		}

		final Solution solution = new Solution(text, task.get(), student.get());
		task.get().addSolution(solution);
		return Validation.success(solution);
	}

}
