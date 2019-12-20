

package com.kitsrc.watt.utils.function;


/**
 * A functional interface for a {@link java.util.function.Supplier} that may
 * throw exceptions.
 *
 * @param <R> The type of the result of the supplier.
 * @param <E> The type of Exceptions thrown by this function.
 */
@FunctionalInterface
public interface SupplierWithException<R, E extends Throwable> {

    /**
     * Gets the result of this supplier.
     *
     * @return The result of thus supplier.
     * @throws E This function may throw an exception.
     */
    R get() throws E;
}
