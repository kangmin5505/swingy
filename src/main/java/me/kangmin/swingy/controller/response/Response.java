package me.kangmin.swingy.controller.response;

import me.kangmin.swingy.controller.response.enums.ResponseCode;

public class Response<T> {
    private final ResponseCode responseCode;
    private final T data;
    private final String message;

    public Response(ResponseCode responseCode, T data, String message) {
        this.responseCode = responseCode;
        this.data = data;
        this.message = message;
    }

    public static <T> Builder<T> builder() { return new Builder<>(); }

    public static class Builder<T> {
        private ResponseCode responseCode;
        private T data;
        private String message;

        public Builder<T> responseCode(ResponseCode responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> message(Message message) {
            this.message = message.getMessage();
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Response<T> build() {
            return new Response<>(responseCode, data, message);
        }
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public enum Message {
        INVALID_INPUT("잘못된 입력입니다."),
        CREATE_MAP("게임 맵 생성에 실패했습니다."),
        FAIL_TO_LOAD_GAME("게임을 불러오는데 실패했습니다."),
        ;

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
