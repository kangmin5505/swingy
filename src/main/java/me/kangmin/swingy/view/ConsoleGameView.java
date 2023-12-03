package me.kangmin.swingy.view;

import me.kangmin.swingy.controller.DefaultGameController;
import me.kangmin.swingy.controller.GameController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleGameView implements GameView {
    private final Scanner scanner = new Scanner(System.in);
    private final GameController gameController;

    public ConsoleGameView(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
        try {
            procWelcomePage();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            procExit();
        }
        while (true) {

        }
    }
    private void procWelcomePage() {
        printIntro();
        requestNumber();
        handleNumberInput(
                this::procWelcomePage,
                this::procNewGame,
                this::procPreviousGame,
                this::procSetting,
                this::procExit
        );
    }

    private void procNewGame() {}
    private void procPreviousGame() {}
    private void procSetting() {}
    private void procExit() {
        System.out.println("게임을 종료합니다.");
        System.exit(0);
    }


    private void handleNumberInput(Runnable mismatch, Runnable ...runnables) {
        int number = scanner.nextInt();
        try {
            ((DefaultGameController) gameController).test(number);
            System.out.println(runnables[-1]);
            runnables[number - 1].run();
        } catch (ArrayIndexOutOfBoundsException e) {
            mismatch.run();
        }
    }

    private void procInputMismatch(Runnable mismatch) {
        System.out.println("잘못된 입력입니다.");
        mismatch.run();
    }

    private void requestNumber() {
        showMenu(getIntroMenu());
        printPrompt();
    }

    private void printPrompt() {
        System.out.println("번호를 입력해주세요.");
        System.out.print("> ");
    }

    private void showMenu(List<String> menu) {
        menu.forEach(System.out::println);
        System.out.println();
    }

    private void printIntro() {
        System.out.println(getBanner());
        System.out.println(getIntro());
    }

}
