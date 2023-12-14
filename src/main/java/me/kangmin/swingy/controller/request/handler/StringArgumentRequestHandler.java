package me.kangmin.swingy.controller.request.handler;

@FunctionalInterface
public interface StringArgumentRequestHandler<T> {
    T handle(String input);
}
