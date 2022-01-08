

package com.vali.core.function;

import com.vali.core.ExceptionUtil;
import java.util.function.BiConsumer;

/**
 * A checked extension of the {@link BiConsumer} interface.
 *
 * @param <T> type of the first argument
 * @param <U> type of the second argument
 * @param <E> type of the thrown exception
 */
@FunctionalInterface
public interface BiConsumerWithException<T, U, E extends Throwable> {

	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param t the first input argument
	 * @param u the second input argument
	 * @throws E in case of an error
	 */
	void accept(T t, U u) throws E;

	/**
	 * Convert a {@link BiConsumerWithException} into a {@link BiConsumer}.
	 *
	 * @param biConsumerWithException BiConsumer with exception to convert into a {@link BiConsumer}.
	 * @param <A> first input type
	 * @param <B> second input type
	 * @return {@link BiConsumer} which rethrows all checked exceptions as unchecked.
	 */
	static <A, B> BiConsumer<A, B> unchecked(BiConsumerWithException<A, B, ?> biConsumerWithException) {
		return (A a, B b) -> {
			try {
				biConsumerWithException.accept(a, b);
			} catch (Throwable t) {
				ExceptionUtil.rethrow(t);
			}
		};
	}
}
