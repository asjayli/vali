package com.kitsrc.watt.utils.function;

@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R apply(T t) throws Exception;
}
