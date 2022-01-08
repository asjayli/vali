package com.vali.api;

@FunctionalInterface
public interface IAction<T> {
    void run(T param);
}
