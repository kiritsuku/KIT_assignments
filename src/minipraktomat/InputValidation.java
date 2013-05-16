package minipraktomat;

/**
 * Utility class to validate input data.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 7, 2012
 */
public class InputValidation {

	/*
	 * Make it impossible to create an instance of this class.
	 */
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
	 * Checks if a given String is conform with the rules for names.
	 * 
	 * @param name
	 *        the name
	 * @return true if the name is correct
	 */
	public static boolean isCorrectName(final String name) {
		if (name == null || name.contains(" ")) {
			return false;
		}
		return name.matches("[a-z]+");
	}
}
