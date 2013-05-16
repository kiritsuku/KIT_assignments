package minipraktomat.commands;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Student;



/**
 * Command for student summaries.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 9, 2012
 */
public class StudentSummaryCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public StudentSummaryCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 0;
	}

	/**
	 * Returns information of students. The order of output depends on the
	 * average grade of the students and if they are equal on there id's.
	 * 
	 * @param arguments
	 *        no arguments expected
	 * @return a validation
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		final List<Student> students = praktomat.getStudents();
		sortStudents(students);

		final StringBuilder result = new StringBuilder();
		for (final Student student : students) {
			if (result.length() != 0) {
				result.append("\n");
			}
			result.append("(").append(student.getId()).append(",").append(student.getName())
					.append("): ").append(averageGrade(student));
		}
		return Validation.success(result.toString());
	}

	private void sortStudents(final List<Student> students) {
		final Comparator<Student> comparator = new Comparator<Student>() {

			@Override
			public int compare(final Student o1, final Student o2) {
				String average1 = averageGrade(o1);
				if (average1.equals("-")) {
					average1 = "~";
				}
				String average2 = averageGrade(o2);
				if (average2.equals("-")) {
					average2 = "~";
				}
				final int compare = average1.compareTo(average2);
				return compare == 0 ? o1.compareTo(o2) : compare;
			}
		};

		Collections.sort(students, comparator);
	}

	private String averageGrade(final Student student) {
		double averageGrade = student.averageGrade();
		if (averageGrade == -1) {
			return "-";
		}
		return String.format("%.02f", averageGrade);
	}
}
