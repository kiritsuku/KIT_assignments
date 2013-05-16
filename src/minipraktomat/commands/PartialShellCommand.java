package minipraktomat.commands;

import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;



/**
 * Partial implemented version of a ShellCommand. This class expects no
 * parameters and has no special return value. Therefore it is only necessary to
 * implement the {@link PartialShellCommand#handle()} method.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public abstract class PartialShellCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat the command should work with.
	 */
	public PartialShellCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 0;
	}

	/**
	 * Returns no special message but always a Success.
	 * 
	 * @param arguments
	 *        doesn't expect parameters
	 * @return a validation
	 */
	@Override
	protected Validation<String, String> handleParameters(final List<String> arguments) {
		handle();
		return Validation.success("");
	}

	/**
	 * This method is the only point which must be implemented.
	 */
	protected abstract void handle();

}
