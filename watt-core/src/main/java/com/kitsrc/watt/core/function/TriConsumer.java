

package com.kitsrc.watt.core.function;


/**
 * Operation which is performed on three given arguments.
 *
 * @param <S> type of the first argument
 * @param <T> type of the second argument
 * @param <U> type of the third argument
 */
@FunctionalInterface
public interface TriConsumer<S, T, U> {

	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param s first argument
	 * @param t second argument
	 * @param u third argument
	 */
	void accept(S s, T t, U u);
}
