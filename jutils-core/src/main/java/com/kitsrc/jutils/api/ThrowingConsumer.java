package com.kitsrc.jutils.api;

@FunctionalInterface
public interface ThrowingConsumer<T> {
    /**
     *
     * @param t
     * @throws Exception
     */
    void accept(T t) throws Exception;
}
