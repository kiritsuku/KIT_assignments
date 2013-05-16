package minipraktomat.commands;

import java.util.List;

import minipraktomat.InputValidation;
import minipraktomat.Option;
import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Student;
import minipraktomat.data.Tutor;



/**
 * Command for student handling.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class StudentCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public StudentCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 2;
	}

	/**
	 * Tries to create a new student. If it could be created no special message
	 * is returned, otherwise an error message.
	 * 
	 * @param arguments
	 *        the name, the id, the tutor
	 * @return no special message or an error message.
	 */
	@Override
	protected Validation<String, String> handleParameters(
			final List<String> arguments) {
		final Option<Tutor> tutor = praktomat.getSelection().getTutor();
		if (!tutor.isDefined()) {
			return Validation.fail("no tutor selected");
		}
		final String name = arguments.get(0);
		final Option<Integer> id = InputValidation.parseInt(arguments.get(1));
		if (!id.isDefined()) {
			return Validation.fail("invalid id");
		}
		final Validation<String, Student> student =
				createStudent(name, id.get(), tutor.get());
		if (student.isFailure()) {
			return Validation.fail(student.getFailure());
		}
		return Validation.success("");
	}

	private Validation<String, Student> createStudent(final String name, final int id,
			final Tutor tutor) {
		if (!InputValidation.isCorrectName(name)) {
			return Validation.fail("invalid name");
		}
		if (id < 10000 || id > 99999) {
			return Validation.fail("invalid id");
		}
		final Option<Student> opt = praktomat.findStudent(id);
		if (opt.isDefined()) {
			return Validation.fail("duplicate id");
		}
		final Student student = new Student(name, id, tutor, praktomat);
		praktomat.addStudent(student);
		return Validation.success(student);
	}

}
