package me.kangmin.swingy.view.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    @Test
    void clearConsole() {
        Printer printer = new Printer();
        printer.clearConsole();
    }
}