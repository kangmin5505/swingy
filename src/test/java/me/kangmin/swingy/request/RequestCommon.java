package me.kangmin.swingy.request;

import me.kangmin.swingy.enums.ResponseCode;
import me.kangmin.swingy.exception.GameException;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestCommon {
    public static <T extends Request> void testSuccessResponseCode(Class<T> clazz, int end) {
        ResponseCode[] responseCodes = ResponseCode.values();

        try {
            assertAll(
                    IntStream.range(1, end + 1)
                             .mapToObj(i -> () -> {
                                 Request request = (Request) clazz.getConstructor(Integer.class).newInstance(i);
                                 ResponseCode responseCode = request.getResponseCode();

                                 assertThat(responseCode).isEqualTo(responseCodes[i - 1]);
                             })
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Request> void testFailResponseCode(Class<T> clazz, Integer... values) {
        assertAll(
                Arrays.stream(values).map(value -> () -> {
                            assertThrows(GameException.class, () -> {
                                Request request = (Request) clazz.getConstructor(Integer.class).newInstance(value);
                                request.getResponseCode();
                            });
                        }
                )
        );
    }
}
