package me.kangmin.swingy.exception;

public enum ExceptionMessage {
    CREATE_MAP("게임 맵을 생성할 수 없습니다."),
    VALIDATION("입력값이 유효하지 않습니다.")
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
