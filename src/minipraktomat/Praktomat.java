package minipraktomat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import minipraktomat.data.Review;
import minipraktomat.data.Selection;
import minipraktomat.data.Solution;
import minipraktomat.data.Student;
import minipraktomat.data.Task;
import minipraktomat.data.Tutor;



/**
 * Saves all data and controls access to them.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 4, 2012
 */
public class Praktomat {

	private Selection selection = Selection.emptySelection();
	private final Set<Tutor> tutors = new HashSet<Tutor>();
	private final Set<Student> students = new HashSet<Student>();
	private final Set<Task> tasks = new HashSet<Task>();

	/**
	 * Returns the selection.
	 * 
	 * @return the selection
	 */
	public Selection getSelection() {
		return selection;
	}

	/**
	 * Saves a selection.
	 * 
	 * @param selection
	 *        the selection
	 */
	public void saveSelection(final Selection selection) {
		if (selection == null) {
			return;
		}
		this.selection = selection;
	}

	/**
	 * Adds a tutor.
	 * 
	 * @param tutor
	 *        the tutor
	 */
	public void addTutor(final Tutor tutor) {
		if (tutor == null) {
			return;
		}
		tutors.add(tutor);
	}

	/**
	 * Adds a student.
	 * 
	 * @param student
	 *        the student
	 */
	public void addStudent(final Student student) {
		if (student == null) {
			return;
		}
		students.add(student);
	}

	/**
	 * Adds a task.
	 * 
	 * @param task
	 *        the task
	 */
	public void addTask(final Task task) {
		if (task == null) {
			return;
		}
		tasks.add(task);
	}

	/**
	 * Returns all available tutors.
	 * 
	 * @return the tutors
	 */
	public List<Tutor> getTutors() {
		return new ArrayList<Tutor>(tutors);
	}

	/**
	 * Returns all available students.
	 * 
	 * @return the students
	 */
	public List<Student> getStudents() {
		return new ArrayList<Student>(students);
	}

	/**
	 * Returns all students coached by a specific tutor.
	 * 
	 * @param tutor
	 *        the tutor
	 * @return the students
	 */
	public List<Student> getStudentsOfTutor(final Tutor tutor) {
		final List<Student> result = new ArrayList<Student>();
		for (final Student student : students) {
			if (student.getTutor().equals(tutor)) {
				result.add(student);
			}
		}
		return result;
	}

	/**
	 * Returns all tasks.
	 * 
	 * @return the tasks
	 */
	public List<Task> getTasks() {
		return new ArrayList<Task>(tasks);
	}

	/**
	 * Returns all solutions.
	 * 
	 * @return the solutions
	 */
	public List<Solution> getSolutions() {
		final List<Solution> solutions = new ArrayList<Solution>();
		for (final Task task : tasks) {
			solutions.addAll(task.getSolutions());
		}
		return solutions;
	}
	
	/**
	 * Returns all solutions of a specific tutor with are not yet corrected.
	 * 
	 * @param tutor
	 *        the tutor
	 * @return the solutions
	 */
	public List<Solution> getUncorrectedSolutionsOfTutor(final Tutor tutor) {
		final List<Solution> result = new ArrayList<Solution>();
		for (final Task task : tasks) {
			final List<Solution> solutions = task.getUncorrectedSolutions();
			for (final Solution solution : solutions) {
				if (solution.getStudent().getTutor().equals(tutor)) {
					result.add(solution);
				}
			}
		}
		return result;
	}

	/**
	 * Returns all reviews.
	 * 
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		final List<Review> reviews = new ArrayList<Review>();
		for (final Task task : tasks) {
			reviews.addAll(task.getReviews());
		}
		return reviews;
	}

	/**
	 * Checks whether a tutor already exists.
	 * 
	 * @param name
	 *        the name of the tutor
	 * @return an Option with the tutor.
	 */
	public Option<Tutor> findTutor(final String name) {
		for (final Tutor tutor : tutors) {
			if (tutor.getName().equals(name)) {
				return Option.some(tutor);
			}
		}
		return Option.none();
	}

	/**
	 * Checks whether a student already exists.
	 * 
	 * @param id
	 *        the student id
	 * @return an Option with the student.
	 */
	public Option<Student> findStudent(final int id) {
		for (final Student student : students) {
			if (student.getId() == id) {
				return Option.some(student);
			}
		}
		return Option.none();
	}

	/**
	 * Checks whether a task already exists.
	 * 
	 * @param id
	 *        the task id
	 * @return an Option with the task.
	 */
	public Option<Task> findTask(final int id) {
		for (final Task task : tasks) {
			if (task.getId() == id) {
				return Option.some(task);
			}
		}
		return Option.none();
	}

	/**
	 * Returns all solutions submitted by a student.
	 * 
	 * @param student
	 *        the student
	 * @return the solutions.
	 */
	public List<Solution> getSolutionsByStudent(final Student student) {
		final List<Solution> solutions = new ArrayList<Solution>();
		for (final Task task : tasks) {
			final Option<Solution> solution = task.findSolution(student);
			if (solution.isDefined()) {
				solutions.add(solution.get());
			}
		}
		return solutions;
	}
}
