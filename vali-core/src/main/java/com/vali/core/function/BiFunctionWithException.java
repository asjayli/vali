

package com.vali.core.function;

import com.vali.core.ExceptionUtil;
import java.util.function.BiFunction;

/**
 * {@link BiFunction} interface which can throw exceptions.
 *
 * @param <T> type of the first parameter
 * @param <U> type of the second parameter
 * @param <R> type of the return type
 * @param <E> type of the exception which can be thrown
 */
@FunctionalInterface
public interface BiFunctionWithException<T, U, R, E extends Throwable> {

    /**
     * Apply the given values t and u to obtain the resulting value. The operation can
     * throw an exception.
     *
     * @param t first parameter
     * @param u second parameter
     * @return result value
     * @throws E if the operation fails
     */
    R apply(T t, U u) throws E;

    /**
     * Convert at {@link BiFunctionWithException} into a {@link BiFunction}.
     *
     * @param biFunctionWithException function with exception to convert into a function
     * @param <A>                     input type
     * @param <B>                     output type
     * @return {@link BiFunction} which throws all checked exception as an unchecked exception.
     */
    static <A, B, C> BiFunction<A, B, C> unchecked(BiFunctionWithException<A, B, C, ?> biFunctionWithException) {
        return (A a, B b) -> {
            try {
                return biFunctionWithException.apply(a, b);
            } catch (Throwable t) {
                ExceptionUtil.rethrow(t);
                // we need this to appease the compiler :-(
                return null;
            }
        };
    }
}
