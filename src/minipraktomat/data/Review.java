package minipraktomat.data;

/**
 * Represents a review.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 1, 2012
 */
public final class Review implements Comparable<Review> {

	private final String text;
	private int grade;
	private final Tutor tutor;
	private final Solution solution;

	/**
	 * Creates a new review.
	 * 
	 * @param text
	 *        the text
	 * @param grade
	 *        the grade
	 * @param tutor
	 *        the tutor
	 * @param solution
	 *        the student
	 */
	public Review(final String text, final int grade, final Tutor tutor,
			final Solution solution) {
		this.text = text;
		this.grade = grade;
		this.tutor = tutor;
		this.solution = solution;
	}

	/**
	 * Returns the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns the grade.
	 * 
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Sets the grade.
	 * 
	 * @param grade
	 *        the grade
	 */
	public void setGrade(final int grade) {
		this.grade = grade;
	}

	/**
	 * Returns the tutor.
	 * 
	 * @return the tutor
	 */
	public Tutor getTutor() {
		return tutor;
	}

	/**
	 * Returns the student.
	 * 
	 * @return the student
	 */
	public Solution getSolution() {
		return solution;
	}

	public int compareTo(final Review o) {
		return getSolution().getStudent().getId() - o.getSolution().getStudent().getId();
	}
	
	@Override
	public String toString() {
		return "Review(" + solution + ")";
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		return obj instanceof Review ? compareTo((Review) obj) == 0 : false;
	}
	
	@Override
	public int hashCode() {
		return solution.hashCode() + tutor.hashCode() * 13 + grade * 37;
	}

}
