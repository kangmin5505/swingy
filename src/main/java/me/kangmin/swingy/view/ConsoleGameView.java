package me.kangmin.swingy.view;

import me.kangmin.swingy.controller.GameController;
import me.kangmin.swingy.core.GameManager;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayerDtos;
import me.kangmin.swingy.dto.SubjectDto;
import me.kangmin.swingy.enums.Page;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.ResponseCode;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.enums.menu.IntroMenu;
import me.kangmin.swingy.enums.menu.MoveMenu;
import me.kangmin.swingy.enums.menu.SettingMenu;
import me.kangmin.swingy.enums.menu.StudyMethodMenu;
import me.kangmin.swingy.request.*;
import me.kangmin.swingy.view.helper.Printer;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

public class ConsoleGameView implements GameView {
    private final Map<ResponseCode, Page> RESPONSE_CODE_TO_WELCOME_PAGE_MAP = Map.of(
            ResponseCode.ONE, Page.NEW_GAME,
            ResponseCode.TWO, Page.SAVED_GAME,
            ResponseCode.THREE, Page.SETTING,
            ResponseCode.FOUR, Page.EXIT
    );
    private final Map<ResponseCode, Page> RESPONSE_CODE_TO_SETTING_PAGE_MAP = Map.of(
            ResponseCode.ONE, Page.CHANGE_VIEW_MODE,
            ResponseCode.TWO, Page.RESET_DATA,
            ResponseCode.THREE, Page.WELCOME
    );
    private final Map<Page, Supplier<Page>> PAGE_TO_PROC_MAP = Map.of(
            Page.WELCOME, this::procWelcomePage,
            Page.SETTING, this::procSettingPage,
            Page.NEW_GAME, this::procNewGamePage,
            Page.SAVED_GAME, this::procSavedGamePage,
            Page.GAME_PLAY, this::procGamePlayPage,
            Page.CHANGE_VIEW_MODE, this::procChangeViewMode,
            Page.EXIT, this::procExit
    );
    private final Scanner scanner = new Scanner(System.in);
    private final Printer printer = new Printer();
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
            this.printer.clearConsole();
            page = this.PAGE_TO_PROC_MAP.get(page).get();
        }
    }
    // ========== private methods ==========
    private Page procExit() {
        this.printer.exit();
        System.exit(0);

        return Page.NONE;
    }
    private Page procGamePlayPage() {
        GameInfoDto gameInfoDto = this.gameController.getGameInfo();
        printer.stage(gameInfoDto);

        printer.gameMap(gameInfoDto);
        printer.player(gameInfoDto.getPlayer());

        this.printer.menu(MoveMenu.values())
                    .inputMessage();

        ResponseCode moveResponseCode = this.getNumberInput(MoveRequest.class);

        boolean isAllocatedSubject = this.gameController.move(moveResponseCode);

        return isAllocatedSubject ? this.procAllocatedSubject() : Page.GAME_PLAY;
    }

    private Page procAllocatedSubject() {
        this.printer.menu(StudyMethodMenu.values())
                    .inputMessage();

        ResponseCode allocatedSubectResponseCode = this.getNumberInput(AllocatedSubjectRequest.class);
        SubjectDto subjectDto = this.gameController.doSubject(allocatedSubectResponseCode);

        if (!subjectDto.isSuccess()) {
            return Page.Game_OVER;
        }

        if (subjectDto.getSubjectType() == SubjectType.MAIN) {
//            subjectDto.getArtifact().ifPresent(artifact -> {
//                this.printer.artifact(artifact)
//                            .inputMessage();
//                ResponseCode numberInput = this.getNumberInput(ArtifactRequest.class);
//                if (numberInput.ordinal() == GET_ARTIFACT_MENU)
//            });
        }


        return Page.GAME_PLAY;
    }

    private Page procSavedGamePage() {
        return Page.WELCOME;
    }

    private Page procNewGamePage() {
        PlayerDtos newPlayers = gameController.getNewPlayers();
        this.printer.players(newPlayers)
                    .inputMessage();

        ResponseCode selectNewPlayerResponseCode = this.getNumberInput(NewPlayerRequest.class);
        int playerTotalCnt = PlayerType.values().length;
        int inputNumber = selectNewPlayerResponseCode.ordinal() + 1;

        if (inputNumber >  playerTotalCnt) {
            return Page.WELCOME;
        }

        ResponseCode setNewGameResponseCode = null;
        do {
            this.printer.inputPlayerNameMessage()
                        .inputMessage();
            String playerName = this.getStringInput();
            SetNewGamePlayerRequest setNewGamePlayerRequest = new SetNewGamePlayerRequest(playerName, selectNewPlayerResponseCode);
            setNewGameResponseCode =  this.gameController.setNewGamePlayer(setNewGamePlayerRequest);
        } while (setNewGameResponseCode != ResponseCode.SUCCESS);

        return Page.GAME_PLAY;
    }

    private Page procSettingPage() {
        this.printer.menu(SettingMenu.values())
                    .inputMessage();

        ResponseCode input = this.getNumberInput(SettingRequest.class);
        return this.RESPONSE_CODE_TO_SETTING_PAGE_MAP.getOrDefault(input, Page.SETTING);
    }

    private Page procChangeViewMode() {
        this.gameManager.procChangeViewMode();
        return Page.NONE;
    }

    private Page procWelcomePage() {
        this.printer.intro(getBanner(), getIntro())
                    .menu(IntroMenu.values())
                    .inputMessage();

        ResponseCode input = this.getNumberInput(WelcomeRequest.class);

        return this.RESPONSE_CODE_TO_WELCOME_PAGE_MAP.getOrDefault(input, Page.WELCOME);
    }

    private <T extends Request> ResponseCode getNumberInput(Class<T> clazz) {
        ResponseCode result = null;
        try {
            do {
                String input = this.scanner.next();
                result = this.gameController.getResponseCode(clazz, input);
            } while (this.procIfFailure(result));
        } catch (NoSuchElementException e) {
            this.procExit();
        }

        return result;
    }

    private String getStringInput() {
        String result = null;
        try {
            do {
                result = this.scanner.next();
            } while (this.procIfFailure(result));
        } catch (NoSuchElementException e) {
            this.procExit();
        }
        return result;
    }

    private boolean procIfFailure(String input) {
        if (input == null || input.isBlank()) {
            this.printer.mismatchMessage()
                        .inputMessage();
            return true;
        }
        return false;
    }

    private boolean procIfFailure(ResponseCode responseCode) {
        if (ResponseCode.FAILURE.equals(responseCode)) {
            this.printer.mismatchMessage()
                        .inputMessage();
            return true;
        }
        return false;
    }
}
