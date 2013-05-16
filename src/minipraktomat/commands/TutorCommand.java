package minipraktomat.commands;

import java.util.List;

import minipraktomat.InputValidation;
import minipraktomat.Option;
import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Tutor;



/**
 * Command for tutor handling.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class TutorCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public TutorCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 1;
	}

	/**
	 * Tries to create a new tutor. If it could be created no special message is
	 * returned, otherwise an error message is returned.
	 * 
	 * @param arguments
	 *        the name of the tutor
	 * @return no special message or an error message.
	 */
	@Override
	protected Validation<String, String> handleParameters(
			final List<String> arguments) {
		final Validation<String, Tutor> tutor = createTutor(arguments.get(0));
		if (tutor.isFailure()) {
			return Validation.fail(tutor.getFailure());
		}
		praktomat.saveSelection(praktomat.getSelection().selectTutor(tutor.getSuccess()));
		return Validation.success("");
	}

	private Validation<String, Tutor> createTutor(final String name) {
		if (!InputValidation.isCorrectName(name)) {
			return Validation.fail("invalid name");
		}
		final Option<Tutor> opt = praktomat.findTutor(name);
		if (opt.isDefined()) {
			return opt.toValidation();
		}
		final Tutor tutor = new Tutor(name, praktomat);
		praktomat.addTutor(tutor);
		return Validation.success(tutor);
	}

}
