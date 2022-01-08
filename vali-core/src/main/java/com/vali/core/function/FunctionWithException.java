

package com.vali.core.function;


/**
 * A functional interface for a {@link java.util.function.Function} that may
 * throw exceptions.
 *
 * @param <T> The type of the argument to the function.
 * @param <R> The type of the result of the supplier.
 * @param <E> The type of Exceptions thrown by this function.
 */
@FunctionalInterface
public interface FunctionWithException<T, R, E extends Throwable> {

    /**
     * Calls this function.
     *
     * @param value The argument to the function.
     * @return The result of thus supplier.
     * @throws E This function may throw an exception.
     */
    R apply(T value) throws E;
}
