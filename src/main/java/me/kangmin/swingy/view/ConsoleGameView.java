package me.kangmin.swingy.view;

import me.kangmin.swingy.controller.GameController;
import me.kangmin.swingy.controller.request.*;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;
import me.kangmin.swingy.controller.response.Response;
import me.kangmin.swingy.controller.response.enums.ResponseCode;
import me.kangmin.swingy.core.GameManager;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayersDto;
import me.kangmin.swingy.dto.SubjectDto;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.enums.Page;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.view.helper.TextProvider;
import me.kangmin.swingy.view.menu.*;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

public class ConsoleGameView implements GameView {
    private final Map<Page, Supplier<Page>> PAGE_TO_PROC_MAP = Map.of(
            Page.WELCOME, this::procWelcomePage,
            Page.SETTING, this::procSettingPage,
            Page.CREATE_NEW_PLAYER, this::procCreateNewPlayer,
            Page.LOAD_GAME, this::procLoadGamePage,
            Page.GAME_PLAY, this::procGamePlayPage,
            Page.CHANGE_VIEW_MODE, this::procChangeViewMode,
            Page.NEXT_STAGE, this::procNextStage,
            Page.END, this::procEndPage,
            Page.EXIT, this::procExit,
            Page.RESET_DATA, this::procResetDataPage
    );

    private final Scanner scanner = new Scanner(System.in);
    private final GameManager gameManager;
    private final GameController gameController;

    public ConsoleGameView(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        Page page = Page.WELCOME;

        while (page != Page.NONE) {
            this.clearConsole();
            page = this.PAGE_TO_PROC_MAP.get(page).get();
        }
    }
    // ========== private methods ==========
    private Page procExit() {
        String exitText = TextProvider.exit();
        System.out.println(exitText);

        System.exit(0);

        return Page.NONE;
    }

    private Page procEndPage() {
        String endingText = TextProvider.ending();
        System.out.println(endingText);

        try {
            Thread.sleep(TextProvider.ENDING_TIME);
        } catch (InterruptedException e) {
            throw new GameException("게임을 종료하는데 실패하였습니다.");
        }

        return Page.WELCOME;
    }

    private Page procGamePlayPage() {
        GameInfoDto gameInfoDto = this.requestFromCode(RequestHandlerCode.GAME_INFO);

        String stageText = TextProvider.stage(gameInfoDto);
        System.out.println(stageText);

        String gameMapText = TextProvider.gameMap(gameInfoDto);
        System.out.println(gameMapText);

        String playerText = TextProvider.player(gameInfoDto.getPlayer());
        System.out.println(playerText);

        this.printMenu(new MoveMenu());

        Boolean isAllocatedSubject = this.requestFromInput(MovePlayerRequest.class);

        return Boolean.TRUE.equals(isAllocatedSubject) ? this.procAllocatedSubject() : Page.GAME_PLAY;
    }

    private void printMenu(Menu menu) {
        String inputNumberText = TextProvider.inputNumber();
        System.out.println(inputNumberText);

        TextProvider.menu(menu)
                .forEach(System.out::println);
    }

    private Page procAllocatedSubject() {
        this.printMenu(new StudyMethodMenu());

        SubjectDto subjectDto = this.requestFromInput(StudySubjectRequest.class);

        if (!subjectDto.isSuccess()) {
            return Page.Game_OVER;
        }

        if (subjectDto.getSubjectType() == SubjectType.MAIN) {
            subjectDto.getArtifact().ifPresent(artifact -> {
                String artifactText = TextProvider.artifact(artifact);
                System.out.println(artifactText);

                this.requestFromInput(ArtifactRequest.class);
            });
            int stage = subjectDto.getStage();

            return stage == Stage.FINAL_STAGE ? Page.END : Page.NEXT_STAGE;
        }

        return Page.GAME_PLAY;
    }

    private Page procNextStage() {
        String saveGameText = TextProvider.saveGame();
        System.out.println(saveGameText);

        return this.requestFromInput(SaveGameRequest.class);
    }

    private Page procLoadGamePage() {
        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.SAVED_GAME_LIST);

        TextProvider.playerList(playersDto)
                            .forEach(System.out::println);

        return this.requestFromInput(LoadGameRequest.class);
    }

    private Page procCreateNewPlayer() {
        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.NEW_PLAYER_LIST);

        TextProvider.playerList(playersDto)
                    .forEach(System.out::println);


        Page page = this.requestFromInput(ChooseNewPlayerRequest.class);
        if (page == Page.WELCOME) {
            return Page.WELCOME;
        }

        String inputPlayerNameText = TextProvider.inputPlayerName();
        System.out.println(inputPlayerNameText);

        return this.requestFromInput(SetNewPlayerNameRequest.class);
    }

    private Page procWelcomePage() {
        String introText = TextProvider.intro();
        System.out.println(introText);

        this.printMenu(new WelcomeMenu());

        return this.requestFromInput(WelcomeRequest.class);
    }
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private Page procSettingPage() {
        this.printMenu(new SettingMenu());

        return this.requestFromInput(SettingRequest.class);
    }

    private Page procResetDataPage() {
        String resetDataText = TextProvider.resetData();
        System.out.println(resetDataText);

        return this.requestFromInput(ResetDataRequest.class);
    }

    private Page procChangeViewMode() {
        this.gameManager.procChangeViewMode();
        return Page.NONE;
    }

    private <T extends Request, S> S requestFromInput(Class<T> clazz) {
        Response<S> response = null;
        boolean isFailure = false;
        String mismatchText = TextProvider.mismatch();
        String inputText = TextProvider.input();

        do {
            if (isFailure) {
                System.out.println(mismatchText);
            }
            System.out.println(inputText);

            String input = this.getInput();
            response = this.request(input, clazz);
            isFailure = true;
        } while (response == null || response.getResponseCode() != ResponseCode.SUCCESS);

        return response.getData();
    }

    private String getInput() {
        try {
            return this.scanner.next();
        } catch (NoSuchElementException e) {
            this.procExit();
        }

        throw new IllegalStateException();
    }

    private <T extends Request, S> Response<S> request(String input, Class<T> clazz) {
        try {
            Class<?> inputClass = clazz.getDeclaredField("input").getType();

            if (inputClass.equals(String.class)) {
                Constructor<T> constructor = clazz.getConstructor(String.class);
                return this.gameController.request(constructor.newInstance(input));
            } else if (inputClass.equals(Integer.class)) {
                Constructor<T> constructor = clazz.getConstructor(Integer.class);
                return this.gameController.request(constructor.newInstance(Integer.parseInt(input)));
            }

            throw new IllegalArgumentException();
        } catch (Exception e) {
            return null;
        }
    }

    private <S> S requestFromCode(RequestHandlerCode code) {
        Response<S> response = this.gameController.request(code);
        return response.getData();
    }
}
