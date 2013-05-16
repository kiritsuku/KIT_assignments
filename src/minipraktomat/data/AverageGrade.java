package minipraktomat.data;

/**
 * Interface for classes with an average grade.
 */
public interface AverageGrade {

	/**
	 * Returns the average grade of this object. The average grade is a
	 * non-negative double value. A negative value (e.g. -1) is returned, if no
	 * average grade for the current object exists.
	 * 
	 * @return average grade of this object, or a negative number (e.g. -1) if no
	 *         average grade exists.
	 */
	double averageGrade();

}
