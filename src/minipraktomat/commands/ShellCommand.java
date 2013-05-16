package minipraktomat.commands;

import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;



/**
 * Represents a command a shell can handle.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 6, 2012
 */
public abstract class ShellCommand {

	/**
	 * The Praktomat which holds the data.
	 */
	protected final Praktomat praktomat;

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat the command should work with.
	 */
	public ShellCommand(final Praktomat praktomat) {
		this.praktomat = praktomat;
	}

	/**
	 * Executes the command and handles the arguments. Each execution checks
	 * whether there are enough arguments and if they are valid. If this is not
	 * the case an error message is returned, otherwise the command returns the
	 * expected output. To execute a command the only way is to call this method.
	 * 
	 * @param arguments
	 *        the arguments
	 * @return the expected value or an error message.
	 */
	public final Validation<String, String> execute(final List<String> arguments) {
		if (arguments.size() != expectedArguments()) {
			return Validation.fail("invalid number of arguments. Expected: " + expectedArguments());
		}
		return handleParameters(arguments);
	}

	/**
	 * Returns the number of arguments the command expects.
	 * 
	 * @return the number of arguments.
	 */
	public abstract int expectedArguments();

	/**
	 * Takes the logic which should be executed by a command. This method is
	 * called boy the execute method.
	 * 
	 * @param arguments
	 *        the arguments
	 * @return the expected value or an error message.
	 */
	protected abstract Validation<String, String> handleParameters(List<String> arguments);
}
