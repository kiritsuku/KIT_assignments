package minipraktomat.data;

import minipraktomat.Option;

/**
 * Represents a solution.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 1, 2012
 */
public final class Solution implements Comparable<Solution> {

	private final String text;
	private final Task task;
	private final Student student;
	private final Option<Review> review;

	/**
	 * Creates a new solution.
	 * 
	 * @param text
	 *        the text
	 * @param task
	 *        the task
	 * @param student
	 *        the student
	 */
	public Solution(final String text, final Task task, final Student student) {
		this(text, task, student, Option.<Review> none());
	}

	/**
	 * Creates a new solution.
	 * 
	 * @param text
	 *        the text
	 * @param task
	 *        the task
	 * @param student
	 *        the student
	 * @param review
	 *        the review
	 */
	public Solution(final String text, final Task task, final Student student,
			final Review review) {
		this(text, task, student, Option.some(review));
	}

	private Solution(final String text, final Task task, final Student student,
			final Option<Review> review) {
		this.text = text;
		this.task = task;
		this.student = student;
		this.review = review;
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
	 * Returns the task.
	 * 
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Returns the student.
	 * 
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Checks whether this solution is corrected.
	 * 
	 * @return true if this solution is corrected
	 */
	public boolean isCorrected() {
		return review.isDefined();
	}

	/**
	 * Returns the review of this solution or throws an exception if there is no
	 * review.
	 * 
	 * @return the review
	 */
	public Review getReview() {
		return review.get();
	}

	/**
	 * Corrects this solution with a review.
	 * 
	 * @param review
	 *        the review
	 * @return the corrected solution
	 */
	public Solution correct(final Review review) {
		return new Solution(text, task, student, review);
	}
	
	public int compareTo(final Solution arg0) {
		return getStudent().getId() - arg0.getStudent().getId();
	}
	
	@Override
	public String toString() {
		return "Solution(" + task + ", " + student + ")";
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		return obj instanceof Solution ? compareTo((Solution) obj) == 0 : false;
	}
	
	@Override
	public int hashCode() {
		return task.hashCode() * 37 + student.hashCode() * 53;
	}
}
