package com.kitsrc.jutils.core.lambda;

@FunctionalInterface
public interface ThrowingConsumer<T> {
    /**
     *
     * @param t
     * @throws Exception
     */
    void accept(T t) throws Exception;
}
