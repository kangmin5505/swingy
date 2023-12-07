package me.kangmin.swingy.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WelcomeRequestTest {

    @Test
    @DisplayName("1 ~ 4 사이의 숫자를 넣으면 ResponseCode.ONE ~ ResponseCode.FOUR 반환되어야 함")
    void getResponseCode() {
        RequestCommon.testSuccessResponseCode(WelcomeRequest.class, 4);
    }

    @Test
    @DisplayName("1 ~ 4 사이의 숫자가 아니면 IllegalArgumentException이 발생해야 함")
    void getResponseCode2() {
        RequestCommon.testFailResponseCode(WelcomeRequest.class, 0, 5, null);
    }

}