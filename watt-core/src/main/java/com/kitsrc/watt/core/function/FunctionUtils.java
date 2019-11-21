

package com.kitsrc.watt.core.function;

import com.kitsrc.watt.core.ExceptionUtil;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility class for Flink's functions.
 */
public class FunctionUtils {

	private FunctionUtils() {
		throw new UnsupportedOperationException("This class should never be instantiated.");
	}

	private static final Function<Object, Void> NULL_FN = ignored -> null;

	private static final Consumer<Object> IGNORE_FN = ignored -> {};

	/**
	 * Function which returns {@code null} (type: Void).
	 *
	 * @param <T> input type
	 * @return Function which returns {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Void> nullFn() {
		return (Function<T, Void>) NULL_FN;
	}

	/**
	 * Consumer which ignores the input.
	 *
	 * @param <T> type of the input
	 * @return Ignoring {@link Consumer}
	 */
	@SuppressWarnings("unchecked")
	public static <T> Consumer<T> ignoreFn() {
		return (Consumer<T>) IGNORE_FN;
	}

	/**
	 * Convert at {@link FunctionWithException} into a {@link Function}.
	 *
	 * @param functionWithException function with exception to convert into a function
	 * @param <A> input type
	 * @param <B> output type
	 * @return {@link Function} which throws all checked exception as an unchecked exception.
	 */
	public static <A, B> Function<A, B> uncheckedFunction(FunctionWithException<A, B, ?> functionWithException) {
		return (A value) -> {
			try {
				return functionWithException.apply(value);
			} catch (Throwable t) {
				ExceptionUtil.rethrow(t);
				// we need this to appease the compiler :-(
				return null;
			}
		};
	}

	/**
	 * Converts a {@link ThrowingConsumer} into a {@link Consumer} which throws checked exceptions
	 * as unchecked.
	 *
	 * @param throwingConsumer to convert into a {@link Consumer}
	 * @param <A> input type
	 * @return {@link Consumer} which throws all checked exceptions as unchecked
	 */
	public static <A> Consumer<A> uncheckedConsumer(ThrowingConsumer<A, ?> throwingConsumer) {
		return (A value) -> {
			try {
				throwingConsumer.accept(value);
			} catch (Throwable t) {
				ExceptionUtil.rethrow(t);
			}
		};
	}

	/**
	 * Converts a {@link SupplierWithException} into a {@link Supplier} which throws all checked exceptions
	 * as unchecked.
	 *
	 * @param supplierWithException to convert into a {@link Supplier}
	 * @return {@link Supplier} which throws all checked exceptions as unchecked.
	 */
	public static <T> Supplier<T> uncheckedSupplier(SupplierWithException<T, ?> supplierWithException) {
		return () -> {
			T result = null;
			try {
				result = supplierWithException.get();
			} catch (Throwable t) {
				ExceptionUtil.rethrow(t);
			}
			return result;
		};
	}
}
