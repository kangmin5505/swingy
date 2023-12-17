package me.kangmin.swingy.view;

import me.kangmin.swingy.controller.GameController;
import me.kangmin.swingy.controller.request.ChooseNewPlayerRequest;
import me.kangmin.swingy.controller.request.MovePlayerRequest;
import me.kangmin.swingy.controller.request.Request;
import me.kangmin.swingy.controller.request.SetNewPlayerNameRequest;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;
import me.kangmin.swingy.controller.response.Response;
import me.kangmin.swingy.controller.response.enums.ResponseCode;
import me.kangmin.swingy.core.GameManager;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayersDto;
import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.enums.Page;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.view.helper.TextProvider;
import me.kangmin.swingy.view.menu.MoveMenu;
import me.kangmin.swingy.view.menu.SettingMenu;
import me.kangmin.swingy.view.menu.WelcomeMenu;
import me.kangmin.swingy.view.menu.element.MoveElement;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GUIGameView extends JFrame implements GameView {
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
    private final JTextArea display = new JTextArea();
    private final JPanel subPanel = new JPanel();

    public GUIGameView(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;
        this.init();
    }

    @Override
    public void run() {
        this.procWelcomePage();
    }

    private void clearDisplay() {
        this.display.setText("");
    }

    private void procWelcomePage() {
        this.clearPanelContents();

        String introText = TextProvider.intro();
        this.display.append(introText);
        this.setMenuButtons(TextProvider.menu(new WelcomeMenu()),
                this::procCreateNewPlayer,
                this::procLoadGamePage,
                this::procSettingPage,
                this::procExit
        );
    }

    private void procSettingPage() {
        this.clearPanelContents();

        this.setMenuButtons(TextProvider.menu(new SettingMenu()),
                this::procChangeViewMode,
                this::procResetDataPage,
                this::procWelcomePage
        );
    }

    private void procCreateNewPlayer() {
        this.clearPanelContents();

        PlayersDto playersDto = this.requestFromCode(RequestHandlerCode.NEW_PLAYER_LIST);

        List<Runnable> runnables = new ArrayList<>();

        for (int i = 0; i < playersDto.getPlayers().size() + 1; i++) {
            final int number = i + 1;
            runnables.add(() -> {
                Page page = this.requestFromInput(ChooseNewPlayerRequest.class, String.valueOf(number));

                if (page == Page.WELCOME) {
                    this.procWelcomePage();
                } else {
                    this.procSetNewPlayerName();
                }
            });
        }

        TextProvider.playerList(playersDto)
                .forEach(this.display::append);

        List<String> stringList = IntStream.rangeClosed(1, playersDto.getSize() + 1)
                                        .mapToObj(Integer::toString)
                                        .collect(Collectors.toList());
        this.setMenuButtons(stringList, runnables.toArray(Runnable[]::new));
    }

    private void procSetNewPlayerName() {
        this.clearPanelContents();

        String inputPlayerNameText = TextProvider.inputPlayerName();
        this.display.append(inputPlayerNameText);

        JTextField nameField = new JTextField(20);
        JLabel nameLabel = new JLabel("이름 : ");

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
                this.procGamePlayPage();
            } catch (GameException ex) {
                this.display.append(ex.getMessage() + "\n");
            }
        });

        this.subPanel.add(nameField);
        this.subPanel.add(nameLabel);
        this.subPanel.add(button);
    }

    private void procNextStage() {

    }

    private void procChangeViewMode() {
        dispose();
        this.gameManager.procChangeViewMode();
    }

    private void procEndPage() {

    }

    private void procExit() {
        System.exit(0);
    }

    private void procGamePlayPage() {
        this.clearPanelContents();

        GameInfoDto gameInfoDto = this.requestFromCode(RequestHandlerCode.GAME_INFO);

        String stageText = TextProvider.stage(gameInfoDto);
        this.display.append(stageText);

        String gameMapText = TextProvider.gameMap(gameInfoDto);
        this.display.append(gameMapText);

        String playerText = TextProvider.player(gameInfoDto.getPlayer());
        this.display.append(playerText);

        MoveMenu moveMenu = new MoveMenu();
        List<Runnable> runnables = new ArrayList<>();
        for (int i = 0; i < moveMenu.getElements().length; ++i) {
            final int number = i + 1;

            runnables.add(() -> {
                try {
                    Boolean isAllocatedSubject = this.requestFromInput(MovePlayerRequest.class, String.valueOf(number));
                    if (Boolean.TRUE.equals(isAllocatedSubject)) {
                        this.procAllocatedSubject();
                    } else {
                        this.procGamePlayPage();
                    }
                } catch (GameException e) {
                    this.display.append(e.getMessage() + "\n");
                }
            });
        }

        this.setMenuButtons(TextProvider.menu(moveMenu), runnables.toArray(Runnable[]::new));
    }

    private void procAllocatedSubject() {

    }

    private void procResetDataPage() {

    }

    private void procLoadGamePage() {

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

        return button;
    }

    private void setMenuButtons(List<String> menu, Runnable... runnables) {
        this.subPanel.removeAll();

        for (int i = 0; i < menu.size(); i++) {
            JButton button = this.getDefaultJButton(menu.get(i));

            final int idx = i;
            button.addActionListener(e -> {
                this.clearDisplay();
                runnables[idx].run();
            });

            this.subPanel.add(button);
        }
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

        this.display.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH - TEXT_AREA_MARGIN, HEIGHT));
        this.display.setSize(new Dimension(MAIN_PANEL_WIDTH - TEXT_AREA_MARGIN, HEIGHT));
        this.display.setBackground(Color.BLACK);
        this.display.setForeground(Color.GREEN);
        this.display.setFont(new Font("Courier", Font.PLAIN, 14));
        this.display.setLocation(TEXT_AREA_MARGIN, TEXT_AREA_MARGIN);
        this.display.setEditable(false);
        this.display.setLineWrap(true);

        this.mainPanel.add(this.display);
        add(this.mainPanel);

        this.subPanel.setSize(SUB_PANEL_WIDTH, HEIGHT);
        this.subPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, HEIGHT));
        this.subPanel.setLocation(MAIN_PANEL_WIDTH, 0);
        this.subPanel.setBackground(Color.BLACK);
        add(this.subPanel);

        setVisible(true);
    }


    private void clearPanelContents() {
        this.clearDisplay();
        this.subPanel.removeAll();
        repaint();
    }
}
