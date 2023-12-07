package me.kangmin.swingy.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NewPlayerRequestTest {

    @Test
    @DisplayName("1 ~ 8 사이의 숫자를 넣으면 ResponseCode.ONE ~ ResponseCode.SEVEN가 반환되어야 함")
    void getResponseCode() {
        RequestCommon.testSuccessResponseCode(NewPlayerRequest.class, 7);
    }

    @Test
    @DisplayName("1 ~ 8 사이의 숫자가 아니면 IllegalArgumentException이 발생해야 함")
    void getResponseCode2() {
        RequestCommon.testFailResponseCode(NewPlayerRequest.class, 0, 9, null);
    }

}