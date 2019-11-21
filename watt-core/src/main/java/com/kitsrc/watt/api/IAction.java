package com.kitsrc.watt.api;

@FunctionalInterface
public interface IAction<T> {
    void run(T param);
}
