package me.kangmin.swingy.controller.request;

import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;

public interface Request {
    RequestHandlerCode getRequestHandlerCode();
    <T> T getInput();
}
