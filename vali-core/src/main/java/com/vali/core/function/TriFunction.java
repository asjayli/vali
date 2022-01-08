

package com.vali.core.function;


/**
 * Function which takes three arguments.
 *
 * @param <S> type of the first argument
 * @param <T> type of the second argument
 * @param <U> type of the third argument
 * @param <R> type of the return value
 */
@FunctionalInterface
public interface TriFunction<S, T, U, R> {

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param s the first function argument
	 * @param t the second function argument
	 * @param u the third function argument
	 * @return the function result
	 */
	R apply(S s, T t, U u);
}
