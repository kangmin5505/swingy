package me.kangmin.swingy.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SettingRequestTest {

    @Test
    @DisplayName("1 ~ 3 사이의 숫자를 넣으면 ResponseCode.ONE ~ ResponseCode.THREE가 반환되어야 함")
    void getResponseCode() {
        RequestCommon.testSuccessResponseCode(SettingRequest.class, 3);
    }

    @Test
    @DisplayName("1 ~ 3 사이의 숫자가 아니면 IllegalArgumentException이 발생해야 함")
    void getResponseCode2() {
        RequestCommon.testFailResponseCode(SettingRequest.class, 0, 4, null);
    }
}