package me.kangmin.swingy.controller.request.handler;

@FunctionalInterface
public interface IntegerArgumentRequestHandler<T> {
    T handle(Integer input);
}
