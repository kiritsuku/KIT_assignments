package minipraktomat.commands;

import java.util.List;

import minipraktomat.InputValidation;
import minipraktomat.Option;
import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Review;
import minipraktomat.data.Solution;
import minipraktomat.data.Student;
import minipraktomat.data.Task;



/**
 * Command for review handling.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class ReviewCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public ReviewCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 4;
	}

	/**
	 * Tries to create a new review. If it could be created a message is
	 * returned, otherwise an error message.
	 * 
	 * @param arguments
	 *        the text, the grade, the student id, the task id
	 * @return on success no special message or an error message
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		final Option<Integer> taskId = InputValidation.parseInt(arguments.get(0));
		final Option<Integer> studentId = InputValidation.parseInt(arguments.get(1));
		final Option<Integer> grade = InputValidation.parseInt(arguments.get(2));
		final String text = arguments.get(3);
		if (!studentId.isDefined() || !taskId.isDefined() || !grade.isDefined()) {
			return Validation.fail("invalid number");
		}
		final Option<Student> student = praktomat.findStudent(studentId.get());
		if (!student.isDefined()) {
			return Validation.fail("student does not exist");
		}
		final Validation<String, Review> review =
			createReview(text, grade.get(), student.get(), taskId.get());
		if (review.isFailure()) {
			return Validation.fail(review.getFailure());
		}
		final String message = String.format(
				"%s reviewed (%d,%s) with grade %d",
				student.get().getTutor().getName(), student.get().getId(),
				student.get().getName(), grade.get());
		return Validation.success(message);
	}

	private Validation<String, Review> createReview(final String text, final int grade,
			final Student student, final int taskId) {
		if (text.contains(" ")) {
			return Validation.fail("invalid text");
		}
		if (grade < 1 || grade > 5) {
			return Validation.fail("invalid grade");
		}

		final Option<Task> task = praktomat.findTask(taskId);
		if (!task.isDefined()) {
			return Validation.fail("task does not exist");
		}
		final Option<Solution> solution = task.get().findSolution(student);
		if (!solution.isDefined()) {
			return Validation.fail("(" + student.getId() + "," + student.getName()
					+ ") has no solution to correct");
		}

		final Review review = new Review(text, grade, student.getTutor(), solution.get());

		task.get().removeSolution(solution.get());
		task.get().addSolution(solution.get().correct(review));

		return Validation.success(review);
	}
}
