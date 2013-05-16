package minipraktomat.commands;

import java.util.Collections;
import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Student;



/**
 * Command to handle lists of students.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class ListStudentsCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public ListStudentsCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 0;
	}

	/**
	 * Returns information of all students. The students are sorted by their id.
	 * 
	 * @param arguments
	 *        no arguments expected
	 * @return a validation
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		final List<Student> students = praktomat.getStudents();
		Collections.sort(students);

		final StringBuilder sb = new StringBuilder();
		for (final Student student : students) {
			if (sb.length() != 0) {
				sb.append("\n");
			}
			sb.append("(").append(student.getId()).append(",")
					.append(student.getName()).append("): ")
					.append(student.getTutor().getName());
		}
		return Validation.success(sb.toString());
	}

}
