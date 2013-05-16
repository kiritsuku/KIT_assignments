package minipraktomat.commands;

import java.util.List;

import minipraktomat.Praktomat;
import minipraktomat.Validation;
import minipraktomat.data.Task;



/**
 * Command for task handling.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class TaskCommand extends ShellCommand {

	/**
	 * Creates a new instance.
	 * 
	 * @param praktomat
	 *        the praktomat
	 */
	public TaskCommand(final Praktomat praktomat) {
		super(praktomat);
	}

	@Override
	public int expectedArguments() {
		return 1;
	}

	/**
	 * Tries to create a new task. If it could be created a success message is
	 * returned, otherwise an error message.
	 * 
	 * @param arguments
	 *        the text
	 * @return a success or an error message.
	 */
	@Override
	protected Validation<String, String> handleParameters(
			final List<String> arguments) {
		final Validation<String, Task> task = createTask(arguments.get(0));
		if (task.isFailure()) {
			return Validation.fail(task.getFailure());
		}
		return Validation.success("task id(" + task.getSuccess().getId() + ")");
	}

	private Validation<String, Task> createTask(final String text) {
		if (text.contains(" ")) {
			return Validation.fail("invalid text");
		}
		final Task task = new Task(text, praktomat.getTasks().size() + 1);
		praktomat.addTask(task);
		return Validation.success(task);
	}

}
