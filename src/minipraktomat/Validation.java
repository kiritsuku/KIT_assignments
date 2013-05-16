package minipraktomat;

import java.util.NoSuchElementException;


/**
 * Represents a value of one of two possible types (a disjoint union). The data
 * constructors Failure and Success represent the two possible values. The
 * Validation type is often used as an alternative to Option where the left side
 * represents failures and the right is akin to Some.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 4, 2012
 * @param <A>
 *        the left (failure) side
 * @param <B>
 *        the right (success) side
 */
public abstract class Validation<A, B> {

	/**
	 * Wraps the value into a Failure.
	 * 
	 * @param <A>
	 *        the left (failure) side
	 * @param <B>
	 *        the right (success) side
	 * @param a
	 *        the value to wrap
	 * @return a Failure
	 */
	public static <A, B> Validation<A, B> fail(final A a) {
		return new Failure<A, B>(a);
	}

	/**
	 * Wraps the value into a Success.
	 * 
	 * @param <A>
	 *        the left (failure) side
	 * @param <B>
	 *        the right (success) side
	 * @param b
	 *        the value to wrap
	 * @return a Success
	 */
	public static <A, B> Validation<A, B> success(final B b) {
		return new Success<A, B>(b);
	}

	/**
	 * Checks whether this is a Failure.
	 * 
	 * @return true if this is a Failure, false otherwise.
	 */
	public abstract boolean isFailure();

	/**
	 * Projects this Either to the left value.
	 * 
	 * @return the left value or an exception if it is not defined.
	 */
	public abstract A getFailure();

	/**
	 * Projects this Either to the right value.
	 * 
	 * @return the right value or an exception if it is not defined.
	 */
	public abstract B getSuccess();

	/**
	 * Checks whether this is a Success.
	 * 
	 * @return true if this is a Success, false otherwise.
	 */
	public boolean isSuccess() {
		return !isFailure();
	}

	/**
	 * Converts this Validation to an Option. If the Validation is a Success,
	 * Some is returned otherwise None.
	 * 
	 * @return the value of Success as Some
	 */
	public Option<B> toOption() {
		return isSuccess() ? Option.some(getSuccess()) : Option.<B> none();
	}
}

/**
 * Represents a failure.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 4, 2012
 * @param <A>
 *        the left (failure) side
 * @param <B>
 *        the right (success) side
 */
final class Failure<A, B> extends Validation<A, B> {

	private final A a;

	/**
	 * Creates a new Failure.
	 * 
	 * @param a
	 *        the value to wrap
	 */
	public Failure(final A a) {
		this.a = a;
	}

	@Override
	public boolean isFailure() {
		return true;
	}

	@Override
	public A getFailure() {
		return a;
	}

	@Override
	public B getSuccess() {
		throw new NoSuchElementException("Failure.getSuccess");
	}

	@Override
	public String toString() {
		return "Failure(" + a + ")";
	}

}

/**
 * Represents a success.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 4, 2012
 * @param <A>
 *        the left (failure) side
 * @param <B>
 *        the right (success) side
 */
final class Success<A, B> extends Validation<A, B> {

	private final B b;

	/**
	 * Creates a new Success.
	 * 
	 * @param b
	 *        the value to wrap
	 */
	public Success(final B b) {
		this.b = b;
	}

	@Override
	public boolean isFailure() {
		return false;
	}

	@Override
	public A getFailure() {
		throw new NoSuchElementException("Success.getFailure");
	}

	@Override
	public B getSuccess() {
		return b;
	}

	@Override
	public String toString() {
		return "Success(" + b + ")";
	}

}
