package com.kitsrc.jutils.api;

@FunctionalInterface
public interface IAction<T> {
    void run(T param);
}
