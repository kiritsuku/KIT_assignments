package othello;

/**
 * Utility class to validate input data.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 18, 2012
 */
public class InputValidation {
	
	
	private InputValidation() {
		
	}
	
	/**
	 * Tries to convert a String to an Integer.
	 * 
	 * @param str
	 *        the String
	 * @return an Option which describes if the conversion was successful or not.
	 */
	public static Option<Integer> parseInt(final String str) {
		try {
			return Option.some(Integer.parseInt(str));
		} catch (final NumberFormatException e) {
			return Option.none();
		}
	}

	/**
	 * Tests an expression, throwing an IllegalArgumentException if false.
	 * 
	 * @param requirement
	 *        the expression to test
	 * @param message
	 *        a string to include in the failure message
	 */
	public static void require(final boolean requirement, final String message) {
		if (!requirement) {
			throw new IllegalArgumentException(message);
		}
	}
}
