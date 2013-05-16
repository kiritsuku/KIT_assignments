package minipraktomat.data;

import minipraktomat.Praktomat;

/**
 * Represents a student.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 1, 2012
 */
public final class Student implements Comparable<Student>,AverageGrade {

	private final String name;
	private final int id;
	private final Tutor tutor;
	private final Praktomat praktomat;

	/**
	 * Creates a new student
	 * 
	 * @param name
	 *        the name
	 * @param id
	 *        the id
	 * @param tutor
	 *        the tutor
	 * @param praktomat
	 *        the praktomat
	 */
	public Student(final String name, final int id, final Tutor tutor, final Praktomat praktomat) {
		this.name = name;
		this.id = id;
		this.tutor = tutor;
		this.praktomat = praktomat;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Returns the tutor.
	 * 
	 * @return the tutor
	 */
	public Tutor getTutor() {
		return tutor;
	}

	public int compareTo(final Student o) {
		return getId() - o.getId();
	}

	@Override
	public String toString() {
		return "Student(" + id + ")";
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		return obj instanceof Student ? compareTo((Student) obj) == 0 : false;
	}

	public double averageGrade() {
		int gradeSum = 0;
		int reviews = 0;
		for (final Task task : praktomat.getTasks()) {
			for (final Solution solution : task.getCorrectedSolutions()) {
				if (solution.getStudent() == this) {
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
}
