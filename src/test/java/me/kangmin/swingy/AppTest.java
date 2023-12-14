package me.kangmin.swingy;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    void test() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        LocalDateTime now2 = LocalDateTime.now();
        System.out.println(now2);
        DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String format = now2.format(yyyyMMddHHmmss);
        System.out.println(format);
    }
}
