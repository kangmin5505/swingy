package me.kangmin.swingy.controller.request.handler;

@FunctionalInterface
public interface NoArgumentRequestHandler<T> {
    T handle();
}
