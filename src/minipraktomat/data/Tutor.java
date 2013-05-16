package minipraktomat.data;

import minipraktomat.Praktomat;

/**
 * Represents a tutor
 * 
 * @version 0.1
 * @since JDK1.6, Feb 1, 2012
 */
public final class Tutor implements Comparable<Tutor>,AverageGrade {

	private final String name;
	private final Praktomat praktomat;

	/**
	 * Creates a new tutor
	 * 
	 * @param name
	 *        the name of the tutor.
	 */
	public Tutor(final String name, final Praktomat praktomat) {
		this.name = name;
		this.praktomat = praktomat;
	}

	/**
	 * Returns the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public int compareTo(final Tutor o) {
		return getName().compareTo(o.getName());
	}

	public double averageGrade() {
		int gradeSum = 0;
		int reviews = 0;
		for (final Task task : praktomat.getTasks()) {
			for (final Solution solution : task.getCorrectedSolutions()) {
				if (solution.getStudent().getTutor() == this) {
					gradeSum += solution.getReview().getGrade();
					reviews += 1;
				}
			}
		}
		if (reviews == 0) {
			return -1;
		}
		return (double) gradeSum / reviews;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		return obj instanceof Tutor ? compareTo((Tutor) obj) == 0 : false;
	}

	@Override
	public String toString() {
		return "Tutor(" + getName() + ")";
	}
}
