package minipraktomat.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import minipraktomat.Option;



/**
 * Represents a task.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 1, 2012
 */
public final class Task implements Comparable<Task>, AverageGrade {

	private final String text;
	private final int id;
	private final Set<Solution> solutions;

	/**
	 * Creates an new task.
	 * 
	 * @param text
	 *        the text
	 * @param id
	 *        the id
	 */
	public Task(final String text, final int id) {
		this.text = text;
		this.id = id;
		solutions = new HashSet<Solution>();
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
	 * Returns the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns all solutions associated with this task.
	 * 
	 * @return the solution
	 */
	public List<Solution> getSolutions() {
		return new ArrayList<Solution>(solutions);
	}
	
	/**
	 * Returns all corrected solutions associated with this task.
	 * 
	 * @return the solution
	 */
	public List<Solution> getCorrectedSolutions() {
		final List<Solution> corrected = new ArrayList<Solution>();
		for (final Solution solution : solutions) {
			if (solution.isCorrected()) {
				corrected.add(solution);
			}
		}
		return corrected;
	}
	
	/**
	 * Returns all uncorrected solutions associated with this task.
	 * 
	 * @return the solution
	 */
	public List<Solution> getUncorrectedSolutions() {
		final List<Solution> corrected = new ArrayList<Solution>();
		for (final Solution solution : solutions) {
			if (!solution.isCorrected()) {
				corrected.add(solution);
			}
		}
		return corrected;
	}

	/**
	 * Adds a solution.
	 * 
	 * @param solution
	 *        the solution
	 */
	public void addSolution(final Solution solution) {
		if (solution == null) {
			return;
		}
		solutions.add(solution);
	}

	/**
	 * Removes a solution.
	 * 
	 * @param solution
	 *        the solution
	 */
	public void removeSolution(final Solution solution) {
		solutions.remove(solution);
	}

	/**
	 * Checks whether a student has already created a solution.
	 * 
	 * @param student
	 *        the student
	 * @return an Option with the solution.
	 */
	public Option<Solution> findSolution(final Student student) {
		for (final Solution solution : solutions) {
			if (solution.getStudent().equals(student)) {
				return Option.some(solution);
			}
		}
		return Option.none();
	}

	/**
	 * Returns all reviews associated with this task.
	 * 
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		final List<Review> reviews = new ArrayList<Review>();
		for (final Solution solution : solutions) {
			if (solution.isCorrected()) {
				reviews.add(solution.getReview());
			}
		}
		return reviews;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		return obj instanceof Task ? compareTo((Task) obj) == 0 : false;
	}
	
	@Override
	public String toString() {
		return "Task(" + id + ")";
	}
	
	@Override
	public int hashCode() {
		return id;
	}

	public int compareTo(final Task o) {
		return getId() - o.getId();
	}

	public double averageGrade() {
		final List<Solution> correctedSolutions = getCorrectedSolutions();
		int gradeSum = 0;
		for (final Solution solution : correctedSolutions) {
			gradeSum += solution.getReview().getGrade();
		}
		if (correctedSolutions.size() == 0) {
			return -1;
		}
		return (double) gradeSum / correctedSolutions.size();
	}
}
