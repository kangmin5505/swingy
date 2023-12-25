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
import me.kangmin.swingy.enums.Step;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.view.helper.TextProvider;
import me.kangmin.swingy.view.menu.*;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Supplier;

public class ConsoleGameView implements GameView {
    private final Map<Step, Supplier<Step>> STEP_TO_PROC_MAP = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final GameManager gameManager;
    private final GameController gameController;

    public ConsoleGameView(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;

        this.initStepToProcMap();
}

    private void initStepToProcMap() {
        STEP_TO_PROC_MAP.put(Step.WELCOME, this::procWelcomeStep);
        STEP_TO_PROC_MAP.put(Step.SETTING, this::procSettingStep);
        STEP_TO_PROC_MAP.put(Step.CREATE_NEW_PLAYER, this::procCreateNewPlayer);
        STEP_TO_PROC_MAP.put(Step.LOAD_GAME, this::procLoadGameStep);
        STEP_TO_PROC_MAP.put(Step.GAME_PLAY, this::procGamePlayStep);
        STEP_TO_PROC_MAP.put(Step.CHANGE_VIEW_MODE, this::procChangeViewMode);
        STEP_TO_PROC_MAP.put(Step.END, this::procEndStep);
        STEP_TO_PROC_MAP.put(Step.EXIT, this::procExit);
        STEP_TO_PROC_MAP.put(Step.RESET_DATA, this::procResetDataStep);
        STEP_TO_PROC_MAP.put(Step.SAVE_GAME, this::procSaveGameStep);
        STEP_TO_PROC_MAP.put(Step.GAME_OVER, this::procGameOverStep);
    }

    private Step procGameOverStep() {
        String gameOverText = TextProvider.gameOver();
        System.out.println(gameOverText);

        System.out.printf("%d초 후에 메인으로 돌아갑니다.\n", TextProvider.WAITING_TIME / 1000);

        try {
            Thread.sleep(TextProvider.WAITING_TIME);
        } catch (InterruptedException e) {
            throw new GameException("게임을 종료하는데 실패하였습니다.");
        }

        return Step.WELCOME;
    }

    @Override
    public void run() {
        Step step = Step.WELCOME;

        while (step != Step.NONE) {
            this.clearConsole();
            step = this.STEP_TO_PROC_MAP.get(step).get();
        }
    }
    // ========== private methods ==========
    private Step procSaveGameStep() {
        String saveGameText = TextProvider.saveGame();
        System.out.println(saveGameText);

        this.printMenu(new SaveGameMenu());

        return this.requestFromInput(SaveGameRequest.class);
    }

    private Step procExit() {
        String exitText = TextProvider.exit();
        System.out.println(exitText);

        System.exit(0);

        return Step.NONE;
    }

    private Step procEndStep() {
        String endingText = TextProvider.ending();
        System.out.println(endingText);
        System.out.printf("%d초 후에 메인으로 돌아갑니다.\n", TextProvider.WAITING_TIME / 1000);

        try {
            Thread.sleep(TextProvider.WAITING_TIME);
        } catch (InterruptedException e) {
            throw new GameException("게임을 종료하는데 실패하였습니다.");
        }

        return Step.WELCOME;
    }

    private Step procGamePlayStep() {
        GameInfoDto gameInfoDto = this.requestFromCode(RequestHandlerCode.GAME_INFO);

        String stageText = TextProvider.stage(gameInfoDto);
        System.out.println(stageText);

        String gameMapText = TextProvider.gameMap(gameInfoDto);
        System.out.println(gameMapText);

        String playerText = TextProvider.player(gameInfoDto.getPlayer());
        System.out.println(playerText);

        this.printMenu(new MoveMenu());

        Boolean isAllocatedSubject = this.requestFromInput(MovePlayerRequest.class);

        return Boolean.TRUE.equals(isAllocatedSubject) ? this.procAllocatedSubject() : Step.GAME_PLAY;
    }

    private void printMenu(Menu menu) {
        String inputNumberText = TextProvider.inputNumber();
        System.out.println(inputNumberText);

        TextProvider.menu(menu)
                .forEach(System.out::println);
    }

    private Step procAllocatedSubject() {
        this.printMenu(new StudyMethodMenu());

        SubjectDto subjectDto = this.requestFromInput(StudySubjectRequest.class);

        if (!subjectDto.isSuccess()) {
            return Step.GAME_OVER;
        }

        int stage = subjectDto.getStage();
        SubjectType subjectType = subjectDto.getSubjectType();
        if (subjectType == SubjectType.MAIN && stage == Stage.FINAL_STAGE) {
            return Step.END;
        }

        if (subjectType == SubjectType.MAIN) {
            subjectDto.getArtifact().ifPresent(artifact -> {
                String artifactText = TextProvider.artifact(artifact);
                System.out.println(artifactText);

                this.printMenu(new ArtifactMenu());

                this.requestFromInput(ArtifactRequest.class);
            });

            return this.requestFromCode(RequestHandlerCode.NEXT_STAGE);
        }

        return Step.GAME_PLAY;
    }

    private Step procLoadGameStep() {
        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.SAVED_GAME_LIST);

        TextProvider.playerList(playersDto)
                            .forEach(System.out::println);

        return this.requestFromInput(LoadGameRequest.class);
    }

    private Step procCreateNewPlayer() {
        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.NEW_PLAYER_LIST);

        TextProvider.playerList(playersDto)
                    .forEach(System.out::println);


        Step step = this.requestFromInput(ChooseNewPlayerRequest.class);
        if (step == Step.WELCOME) {
            return Step.WELCOME;
        }

        String inputPlayerNameText = TextProvider.inputPlayerName();
        System.out.println(inputPlayerNameText);

        return this.requestFromInput(SetNewPlayerNameRequest.class);
    }

    private Step procWelcomeStep() {
        String introText = TextProvider.intro();
        System.out.println(introText);

        this.printMenu(new WelcomeMenu());

        return this.requestFromInput(WelcomeRequest.class);
    }
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private Step procSettingStep() {
        this.printMenu(new SettingMenu());

        return this.requestFromInput(SettingRequest.class);
    }

    private Step procResetDataStep() {
        String resetDataText = TextProvider.resetData();
        System.out.println(resetDataText);

        this.printMenu(new ResetDataMenu());

        return this.requestFromInput(ResetDataRequest.class);
    }

    private Step procChangeViewMode() {
        this.gameManager.changeViewMode();
        return Step.NONE;
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
            System.out.print(inputText);

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
        if (response.getResponseCode() == ResponseCode.FAILURE) {
            throw new GameException(response.getMessage());
        }

        return response.getData();
    }
}
