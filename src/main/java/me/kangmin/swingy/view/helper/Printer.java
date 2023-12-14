package me.kangmin.swingy.view.helper;

import me.kangmin.swingy.controller.request.SetNewPlayerNameRequest;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayerDto;
import me.kangmin.swingy.dto.PlayersDto;
import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.entity.base.Coordinate;
import me.kangmin.swingy.entity.base.Level;
import me.kangmin.swingy.entity.base.Stat;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.view.menu.ArtifactMenu;
import me.kangmin.swingy.view.menu.Menu;
import me.kangmin.swingy.view.menu.PlayerMenu;
import me.kangmin.swingy.view.menu.SaveGameMenu;
import me.kangmin.swingy.view.menu.element.MenuElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Printer {
    private final static char PLAYER_SYMBOL = '@';
    private final static char MAIN_SUBJECT_SYMBOL = '$';
    private final static char SUB_SUBJECT_SYMBOL = '#';
    private final static int ENDING_TIME = 5000;

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void exit() {
        System.out.println("게임을 종료합니다.");
    }

    public void mismatchMessage() {
        System.out.println("잘못된 입력입니다.");
    }

    public void inputMessage() {
        System.out.print("> ");
    }

    public Printer menu(Menu menu) {
        this.inputNumberMessage();
        MenuElement[] menuElements = menu.getElements();

        IntStream.range(1, menuElements.length + 1).forEach(i -> {
            System.out.printf("%d. %s\n", i, menuElements[i - 1].getDescription());
        });

        return this;
    }

    public Printer intro(String... messages) {
        Arrays.stream(messages).forEach(System.out::println);
        return this;
    }
    public void playerList(PlayersDto playersDto) {
        List<PlayerDto> players = playersDto.getPlayers();

        PlayerMenu playerMenu = new PlayerMenu(players);
        this.menu(playerMenu);
    }
    public void gameMap(GameInfoDto gameInfoDto) {
        System.out.println("[맵]");
        List<StringBuilder> blankGameMap = this.getBlankGameMap(gameInfoDto);
        List<String> gameMap = this.setEntityOnMap(blankGameMap, gameInfoDto);

        gameMap.forEach(System.out::println);
    }

    private List<String> setEntityOnMap(List<StringBuilder> blankGameMap, GameInfoDto gameInfoDto) {
        Coordinate playerCoordinate = gameInfoDto.getPlayerCoordinate();
        Coordinate enemyBossCoordinate = gameInfoDto.getEnemyBossCoordinate();
        List<Coordinate> enemyCoordinates = gameInfoDto.getEnemyCoordinates();

        blankGameMap.get(playerCoordinate.getY() + 1)
                      .setCharAt(playerCoordinate.getX() + 1, PLAYER_SYMBOL);
        blankGameMap.get(enemyBossCoordinate.getY() + 1)
                      .setCharAt(enemyBossCoordinate.getX() + 1, MAIN_SUBJECT_SYMBOL);
        enemyCoordinates.forEach(coordinate -> {
            blankGameMap.get(coordinate.getY() + 1)
                          .setCharAt(coordinate.getX() + 1, SUB_SUBJECT_SYMBOL);
        });
        return blankGameMap.stream()
                .map(StringBuilder::toString)
                .collect(Collectors.toList());
    }

    private List<StringBuilder> getBlankGameMap(GameInfoDto gameInfoDto) {
        List<StringBuilder> stringBuilders = new ArrayList<>();
        int mapSize = gameInfoDto.getMapSize();
        int totalMapSize = mapSize + 2;

        for (int y = 0; y < totalMapSize; ++y) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < totalMapSize; ++x) {
                if (y == 0 || y == mapSize + 1) {
                    stringBuilder.append('-');
                } else if (x == 0 || (x == mapSize + 1)) {
                    stringBuilder.append('|');
                } else {
                    stringBuilder.append('.');
                }
            }
            stringBuilders.add(stringBuilder);
        }
        stringBuilders.get(1).delete(totalMapSize - 1, totalMapSize);
        return stringBuilders;
    }

    public void stage(GameInfoDto gameInfoDto) {
        int stage = gameInfoDto.getStage();
        int totalStage = gameInfoDto.getTotalStage();
        String bossName = gameInfoDto.getBossName();
        System.out.println("[스테이지]");
        System.out.printf("(%d / %d) %s\n", stage, totalStage - 1, bossName);
    }

    public void player(Player player) {
        Stat stat = player.getStat();
        String name = player.getName();
        String type = player.getType();
        int level = player.getLevel();
        int codingSkill = stat.getCodingSkill();
        int mentalStrength = stat.getMentalStrength();
        int health = stat.getHealth();
        int currentHealth = stat.getCurrentHealth();
        int experience = player.getExperience();
        int neededExperience = player.getTotalNeededExperience();

        System.out.println("[플레이어]");
        System.out.printf("이름: %s\n", name);
        System.out.printf("타입(%s)\t레벨(%s/%s)\t코딩스킬(%s/%s)\t정신력(%s/%s)\t체력(%s/%s)\t경험치(%s/%s)\n",
                type, level, Level.MAX_LEVEL, codingSkill, Stat.MAX_STAT_LIMIT, mentalStrength, Stat.MAX_STAT_LIMIT,
                currentHealth, health, experience, neededExperience);

        System.out.println("[아티팩트]");
        player.getArtifacts().values()
                .forEach(artifact -> {
                    String rank = artifact.getRank();
                    int artifactCodingSkill = artifact.getCodingSkill();
                    int artifactMentalStrength = artifact.getMentalStrength();
                    int artifactHealth = artifact.getHealth();

                    System.out.printf("등급(%s)\t코딩스킬(%d)\t정신력(%d)\t체력(%d)\n",
                            rank, artifactCodingSkill, artifactMentalStrength, artifactHealth);
                });
    }

    public void inputPlayerNameMessage() {
        System.out.printf("플레이어 이름을 입력하세요. (%d ~ %d자)\n",
                SetNewPlayerNameRequest.MIN_PLAYER_NAME_LENGTH,
                SetNewPlayerNameRequest.MAX_PLAYER_NAME_LENGTH);
    }

    private void inputNumberMessage() {
        System.out.println("번호를 입력하세요.");
    }

    public void artifact(Artifact artifact) {

        String rank = artifact.getRank();
        int codingSkill = artifact.getCodingSkill();
        int mentalStrength = artifact.getMentalStrength();
        int health = artifact.getHealth();

        System.out.println("아티팩트를 획득하였습니다.");
        System.out.println("[아티팩트]");
        System.out.printf("등급: %s\n", rank);
        System.out.printf("코딩스킬: %s\n", codingSkill);
        System.out.printf("정신력: %s\n", mentalStrength);
        System.out.printf("체력: %s\n", health);

        this.menu(new ArtifactMenu());
    }

    public void saveGame() {
        System.out.println("게임을 저장하시겠습니까?");
        this.menu(new SaveGameMenu());
    }

    public void ending(String message) {
        System.out.println(message);
        System.out.printf("\n%d초 후에 게임을 종료합니다.\n", ENDING_TIME / 1000);

        try {
            Thread.sleep(ENDING_TIME);
        } catch (InterruptedException e) {
            throw new GameException("게임을 종료하는데 실패하였습니다.");
        }
    }

    public void resetDataMessage() {
        System.out.println("정말 데이터를 초기화 하시겠습니까?");
    }
}
