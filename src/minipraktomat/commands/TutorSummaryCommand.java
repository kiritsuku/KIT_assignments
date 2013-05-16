package minipraktomat.commands;

import java.util.Collections;
import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Solution;
import minipraktomat.data.Student;
import minipraktomat.data.Tutor;



/**
 * Command for tutor summaries.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 9, 2012
 */
public class TutorSummaryCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public TutorSummaryCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 0;
	}

	/**
	 * Returns information of tutors. The order of output depends on the name of
	 * the tutor.
	 * 
	 * @param arguments
	 *        no arguments expected
	 * @return a validation
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		final List<Tutor> tutors = praktomat.getTutors();
		Collections.sort(tutors);

		final StringBuilder result = new StringBuilder();
		for (final Tutor tutor : tutors) {
			if (result.length() != 0) {
				result.append("\n");
			}
			final List<Student> studentsOfTutor = praktomat.getStudentsOfTutor(tutor);
			final List<Solution> todo = praktomat.getUncorrectedSolutionsOfTutor(tutor);
			final String average = formatAverageGrade(tutor);

			result.append(tutor.getName()).append(": ")
					.append(studentsOfTutor.size()).append(" students, ")
					.append(todo.size()).append(" missing review(s), ")
					.append("average grade ").append(average);
		}
		return Validation.success(result.toString());
	}

	private String formatAverageGrade(final Tutor tutor) {
		double averageGrade = tutor.averageGrade();
		if (averageGrade == -1) {
			return "-";
		}
		return String.format("%.02f", averageGrade);
	}

}
