package othello;

import java.util.NoSuchElementException;


/**
 * Represents optional values. Instances of Option are either an instance of
 * Some or the singleton object NONE.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 4, 2012
 * @param <A>
 *        an arbitrary type which specifies the the type the Option holds.
 */
public abstract class Option<A> {

	@SuppressWarnings("rawtypes")
	private static final Option NONE = new Option() {

		@Override
		public Object get() {
			throw new NoSuchElementException("None.get");
		}

		@Override
		public boolean isDefined() {
			return false;
		}

		@Override
		public String toString() {
			return "None";
		}
	};

	/**
	 * Returns the singleton object NONE which does never hold a value.
	 * 
	 * @param <B>
	 *        the type which is adopted by NONE.
	 * @return the singleton object NONE.
	 */
	@SuppressWarnings("unchecked")
	public static <B> Option<B> none() {
		return NONE;
	}

	/**
	 * Creates a new Some. This method guarantees that the value saved by Some is
	 * never null.
	 * 
	 * @param <B>
	 *        the type which is adopted by Some.
	 * @param value
	 *        the parameter which is wrapped by Some.
	 * @return the parameter some wrapped by Some.
	 */
	public static <B> Option<B> some(final B value) {
		if (value == null) {
			return none();
		}
		return new Some<B>(value);
	}

	/**
	 * Returns the value saved by the Option type. If Option is Some the value is
	 * returned. If it is NONE, an exception is thrown.
	 * 
	 * @return the saved value
	 */
	public abstract A get();

	/**
	 * Checks whether the Option type is of type Some or not. It returns always
	 * true for some and always false for NONE.
	 * 
	 * @return true when the Option is defined
	 */
	public abstract boolean isDefined();
}

/**
 * Type Some is a wrapper for arbitrary values. The value saved by Some can
 * never be null.
 * 
 * @version 0.1
 * @since JDK1.6, Feb 4, 2012
 * @param <A>
 *        an arbitrary type which specifies the the type the Option holds.
 */
final class Some<A> extends Option<A> {

	private final A value;

	/**
	 * Creates a new instance of Some.
	 * 
	 * @param value
	 *        the value
	 */
	public Some(final A value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"value is null. Use Option.none() instead");
		}
		this.value = value;
	}

	@Override
	public A get() {
		return value;
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public String toString() {
		return "Some(" + value + ")";
	}
}
