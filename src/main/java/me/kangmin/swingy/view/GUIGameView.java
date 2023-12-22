package me.kangmin.swingy.view;

import me.kangmin.swingy.controller.GameController;
import me.kangmin.swingy.controller.request.*;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;
import me.kangmin.swingy.controller.response.Response;
import me.kangmin.swingy.controller.response.enums.ResponseCode;
import me.kangmin.swingy.core.GameManager;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayerDto;
import me.kangmin.swingy.dto.PlayersDto;
import me.kangmin.swingy.dto.SubjectDto;
import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.enums.Step;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.view.helper.TextProvider;
import me.kangmin.swingy.view.menu.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GUIGameView extends JFrame implements GameView {
    private static final int FONT_SIZE = 16;
    private final int WIDTH = 1400;
    private final double RATIO = (double) 9 / 16;
    private final int HEIGHT =  (int) (WIDTH * RATIO);
    private final int MAIN_PANEL_WIDTH = WIDTH * 4 / 5;
    private final int SUB_PANEL_WIDTH = WIDTH - MAIN_PANEL_WIDTH;
    private final int TEXT_AREA_MARGIN = 20;
    private final int BUTTON_WIDTH = SUB_PANEL_WIDTH * 3 / 4;
    private final int BUTTON_HEIGHT = 50;

    private final static String BANNER = "SWINGY";
    private final GameManager gameManager;
    private final GameController gameController;
    private final JPanel mainPanel = new JPanel();
    private final JTextArea display = new JTextArea(35, 80);
    private final JPanel subPanel = new JPanel();

    public GUIGameView(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;

        this.init();
    }

    @Override
    public void run() {
        this.procWelcomeStep();
    }

    private void clearDisplay() {
        this.display.setText("");
    }

    private void procWelcomeStep() {
        this.clearAllDisplay();

        String introText = TextProvider.intro();
        this.display.append(introText);

        this.setMenuButtons(TextProvider.menu(new WelcomeMenu()),
                this::procCreateNewPlayer,
                this::procLoadGameStep,
                this::procSettingStep,
                this::procExit
        );

        this.repaintAll();
    }

    private void procSettingStep() {
        this.clearAllDisplay();

        this.setMenuButtons(TextProvider.menu(new SettingMenu()),
                this::procChangeViewMode,
                this::procResetDataStep,
                this::procWelcomeStep
        );

        this.repaintAll();
    }

    private void procCreateNewPlayer() {
        this.clearAllDisplay();

        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.NEW_PLAYER_LIST);

        TextProvider.playerList(playersDto)
                    .forEach(this.display::append);

        List<PlayerDto> players = playersDto.getPlayers();

        List<String> playerTypesMenu = this.createPlayerTypes(players);
        Runnable[] runnablesForPlayerTypeMenu = this.createRunnablesForPlayerTypeMenu(players, ChooseNewPlayerRequest.class);
        this.setMenuButtons(playerTypesMenu, runnablesForPlayerTypeMenu);

        this.repaintAll();
    }

    private List<String> createPlayerTypes(List<PlayerDto> players) {
        List<String> playerTypes = IntStream.range(0, players.size())
                                            .mapToObj(i -> {
                                                PlayerDto playerDto = players.get(i);
                                                String playerType = playerDto.getType();

                                                return String.format("%d. %s\n", i + 1, playerType);
                                            })
                                            .collect(Collectors.toList());
        playerTypes.add(String.format("%d. %s\n", players.size() + 1, "뒤로가기"));

        return playerTypes;
    }

    private <T extends Request> Runnable[] createRunnablesForPlayerTypeMenu(List<PlayerDto> players, Class<T> clazz) {
        List<Runnable> runnables = new ArrayList<>();

        for (int i = 0; i < players.size() + 1; i++) {
            final int number = i + 1;
            runnables.add(() -> {
                Step step = this.requestFromInput(clazz, String.valueOf(number));

                if (step == Step.WELCOME) {
                    this.procWelcomeStep();
                } else if (clazz == LoadGameRequest.class) {
                    this.procGamePlayStep();
                } else {
                    this.procSetNewPlayerName();
                }
            });
        }
        return runnables.toArray(Runnable[]::new);
    }

    private void procSetNewPlayerName() {
        this.clearAllDisplay();

        String inputPlayerNameText = TextProvider.inputPlayerName();
        this.display.append(inputPlayerNameText);

        JTextField nameField = new JTextField(20);

        JButton button = new JButton("완료");
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT / 2);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT / 2));
        button.setBackground(Color.GREEN);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addActionListener(e -> {
            String name = nameField.getText();
            try {
                this.requestFromInput(SetNewPlayerNameRequest.class, name);
                this.procGamePlayStep();
            } catch (GameException ex) {
                this.display.append(ex.getMessage() + "\n");
            }
        });

        this.subPanel.add(nameField);
        this.subPanel.add(button);

        this.repaintAll();
    }

    private void procChangeViewMode() {
        dispose();
        this.gameManager.changeViewMode();
    }

    private void procEndStep() {
        this.clearAllDisplay();

        String endingText = TextProvider.ending();
        this.display.append(endingText);

        setMenuButtons(TextProvider.menu(new EndMenu()), this::procWelcomeStep);

        this.repaintAll();
    }

    private void procExit() {
        System.exit(0);
    }

    private void procGamePlayStep() {
        this.clearAllDisplay();

        GameInfoDto gameInfoDto = this.requestFromCode(RequestHandlerCode.GAME_INFO);

        String stageText = TextProvider.stage(gameInfoDto);
        this.display.append(stageText);

        String gameMapText = TextProvider.gameMap(gameInfoDto);
        this.display.append(gameMapText);

        String playerText = TextProvider.player(gameInfoDto.getPlayer());
        this.display.append(playerText);

        MoveMenu moveMenu = new MoveMenu();
        Runnable[] runnablesForMoveMenu = this.createRunnablesForMoveMenu(moveMenu);
        this.setMenuButtons(TextProvider.menu(moveMenu), runnablesForMoveMenu);

        this.repaintAll();
    }

    private Runnable[] createRunnablesForMoveMenu(MoveMenu moveMenu) {
        List<Runnable> runnables = new ArrayList<>();

        for (int i = 0; i < moveMenu.getElements().length; ++i) {
            final int number = i + 1;

            runnables.add(() -> {
                try {
                    Boolean isAllocatedSubject = this.requestFromInput(MovePlayerRequest.class, String.valueOf(number));
                    if (Boolean.TRUE.equals(isAllocatedSubject)) {
                        this.procAllocatedSubject();
                    } else {
                        this.procGamePlayStep();
                    }
                } catch (GameException e) {
                    this.display.append(e.getMessage() + "\n");
                }
            });
        }
        return runnables.toArray(Runnable[]::new);
    }

    private void procAllocatedSubject() {
        this.clearSubDisplay();

        this.setMenuButtons(TextProvider.menu(new StudyMethodMenu()),
                () -> this.procStudySubjectRequest(1),
                () -> this.procStudySubjectRequest(2)
        );

        this.repaintAll();
    }

    private void procStudySubjectRequest(int number) {
        try {
            SubjectDto subjectDto = this.requestFromInput(StudySubjectRequest.class, String.valueOf(number));
            if (!subjectDto.isSuccess()) {
                this.procGameOver();
                return;
            }

            int stage = subjectDto.getStage();
            SubjectType subjectType = subjectDto.getSubjectType();
            if (subjectType == SubjectType.MAIN && stage == Stage.FINAL_STAGE) {
                this.procEndStep();
                return;
            }

            if (subjectDto.getSubjectType() == SubjectType.MAIN) {
                Optional<Artifact> artifactOptional = subjectDto.getArtifact();
                if (artifactOptional.isPresent()) {
                    this.clearAllDisplay();

                    Artifact artifact = artifactOptional.get();

                    String artifactText = TextProvider.artifact(artifact);
                    this.display.append(artifactText);

                    this.setMenuButtons(TextProvider.menu(new ArtifactMenu()),
                            () -> this.procArtifactRequest("1"),
                            () -> this.procArtifactRequest("2")
                    );

                    this.repaintAll();
                } else {
                    this.procSaveGameStep();
                }
            } else {
                this.procGamePlayStep();
            }
        } catch (GameException e) {
            this.display.append(e.getMessage() + "\n");
        }
    }

    private void procGameOver() {
        this.clearAllDisplay();

        String dieText = TextProvider.gameOver();
        this.display.append(dieText);

        setMenuButtons(TextProvider.menu(new EndMenu()), this::procWelcomeStep);

        this.repaintAll();
    }

    private void procArtifactRequest(String input) {
        try {
            this.requestFromInput(ArtifactRequest.class, input);

            this.procSaveGameStep();
        } catch (GameException e) {
            this.display.append(e.getMessage() + "\n");
        }
    }

    private void procSaveGameStep() {
        this.clearAllDisplay();

        this.requestFromCode(RequestHandlerCode.NEXT_STAGE);

        String saveGameText = TextProvider.saveGame();
        this.display.append(saveGameText);

        this.setMenuButtons(TextProvider.menu(new SaveGameMenu()),
                () -> this.procSaveGameRequest("1"),
                () -> this.procSaveGameRequest("2")
        );

        this.repaintAll();
    }

    private void procSaveGameRequest(String input) {
        try {
            Step step = this.requestFromInput(SaveGameRequest.class, input);
            if (step == Step.WELCOME) {
                this.procWelcomeStep();
                return;
            }

            this.procGamePlayStep();
        } catch (GameException e) {
            this.display.append(e.getMessage() + "\n");
        }
    }

    private void procResetDataStep() {
        this.clearAllDisplay();

        String resetDataText = TextProvider.resetData();
        this.display.append(resetDataText);

        this.setMenuButtons(TextProvider.menu(new ResetDataMenu()),
                () -> this.procResetDataRequest("1"),
                () -> this.procResetDataRequest("2")
        );

        this.repaintAll();
    }

    private void procResetDataRequest(String input) {
        try {
            this.requestFromInput(ResetDataRequest.class, input);
            this.procSettingStep();
        } catch (GameException e) {
            this.display.append(e.getMessage() + "\n");
        }
    }

    private void procLoadGameStep() {
        this.clearAllDisplay();

        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.SAVED_GAME_LIST);
        TextProvider.playerList(playersDto)
                    .forEach(this.display::append);

        List<PlayerDto> players = playersDto.getPlayers();

        List<String> playerTypesMenu = this.createPlayerTypes(players);
        Runnable[] runnablesForPlayerTypeMenu = this.createRunnablesForPlayerTypeMenu(players, LoadGameRequest.class);
        this.setMenuButtons(playerTypesMenu, runnablesForPlayerTypeMenu);

        this.repaintAll();
    }

    private <T extends Request, S> S requestFromInput(Class<T> clazz, String input) throws GameException {
        Response<S> response = this.request(input, clazz);
        if (response == null || response.getResponseCode() == ResponseCode.FAILURE) {
            String message = (response == null) ? "알 수 없는 문제가 발생했습니다." : response.getMessage();
            throw new GameException(message);
        }
        return response.getData();
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

    private JButton getDefaultJButton(String text) {
        JButton button = new JButton(text);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setBackground(Color.GREEN);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFont(this.getDefaultFont());

        return button;
    }

    private void setMenuButtons(List<String> menu, Runnable... runnables) {
        for (int i = 0; i < menu.size(); i++) {
            JButton button = this.getDefaultJButton(menu.get(i));

            final int idx = i;
            button.addActionListener(e -> {
                runnables[idx].run();
            });

            this.subPanel.add(button);
        }
    }

    private Font getDefaultFont() {
        return new Font("고딕", Font.PLAIN, FONT_SIZE);
    }

    private void init() {
        setTitle(BANNER);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        this.mainPanel.setSize(MAIN_PANEL_WIDTH, HEIGHT);
        this.mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, HEIGHT));
        this.mainPanel.setLocation(0, 0);
        this.mainPanel.setBackground(Color.BLACK);

        this.display.setBackground(Color.BLACK);
        this.display.setForeground(Color.GREEN);
        this.display.setFont(this.getDefaultFont());
        this.display.setLocation(TEXT_AREA_MARGIN, TEXT_AREA_MARGIN);
        this.display.setEditable(false);
        this.display.setLineWrap(true);

        JScrollPane scrollPanel = new JScrollPane(this.display);

        this.mainPanel.add(scrollPanel);
        add(this.mainPanel);

        this.subPanel.setSize(SUB_PANEL_WIDTH, HEIGHT);
        this.subPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, HEIGHT));
        this.subPanel.setLocation(MAIN_PANEL_WIDTH, 0);
        this.subPanel.setBackground(Color.BLACK);

        add(this.subPanel);

        setVisible(true);
    }

    private void clearAllDisplay() {
        this.clearDisplay();
        this.clearSubDisplay();
    }

    private void clearSubDisplay() {
        this.subPanel.removeAll();
    }

    // paint : Direct Painting
    // repaint : Requesting Redraw
    // validate : Layout Management
    // update : Painting Process
    // updateUI : Look and Feel
    private void repaintAll() {
        this.display.repaint();
        this.subPanel.revalidate();
        this.subPanel.repaint();
    }
}
