package com.kitsrc.jutils.core.bean;

@FunctionalInterface
public interface IAction<T> {
    void run(T param);
}
