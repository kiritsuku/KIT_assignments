package minipraktomat.data;

import minipraktomat.Option;

/**
 * Represents the selected values of the Praktomat. If some data change a new
 * selection must be created.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 11, 2012
 */
public class Selection {

	private final Option<Tutor> tutor;

	private Selection(final Option<Tutor> tutor) {
		this.tutor = tutor;
	}

	/**
	 * Returns the tutor.
	 * 
	 * @return the tutor
	 */
	public Option<Tutor> getTutor() {
		return tutor;
	}

	/**
	 * Selects a tutor.
	 * 
	 * @param tutor
	 *        the tutor
	 * @return a selection which holds the tutor.
	 */
	public Selection selectTutor(final Tutor tutor) {
		return new Selection(Option.some(tutor));
	}

	/**
	 * The only way to create an instance of Selection which hasn't yet anything
	 * selected.
	 * 
	 * @return an empty selection
	 */
	public static Selection emptySelection() {
		return new Selection(Option.<Tutor> none());
	}
}
