package me.kangmin.swingy.view.helper;

import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayerDto;
import me.kangmin.swingy.dto.PlayerDtos;
import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.entity.base.Coordinate;
import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.entity.base.Level;
import me.kangmin.swingy.entity.base.Stat;
import me.kangmin.swingy.enums.menu.IntroMenu;
import me.kangmin.swingy.enums.menu.Menu;
import me.kangmin.swingy.request.SetNewGamePlayerRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Printer {
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void exit() {
        System.out.println("게임을 종료합니다.");
    }

    public Printer mismatchMessage() {
        System.out.println("잘못된 입력입니다.");
        return this;
    }

    public void inputMessage() {
        System.out.print("> ");
    }

    public <T extends Menu<T>> Printer menu(Menu<T>[] menu) {
        this.inputNumberMessage();

        IntStream.range(1, menu.length + 1).forEach(i -> {
            System.out.printf("%d. %s\n", i, menu[i - 1].getDescription());
        });

        return this;
    }

    public Printer intro(String... messages) {
        Arrays.stream(messages).forEach(System.out::println);
        return this;
    }
    public Printer players(PlayerDtos newPlayers) {
        List<PlayerDto> players = newPlayers.getPlayers();

        this.inputNumberMessage();
        IntStream.range(0, players.size()).forEach(i -> {
            this.newPlayer(i + 1, players.get(i));
        });

        System.out.printf("%d. 뒤로가기\n", players.size() + 1);
        return this;
    }

    private void newPlayer(int idx, PlayerDto player) {
        System.out.printf("%d. %s%n", idx, player.getType());
        System.out.printf("\t - 코딩 실력(%d)\n", player.getCodingSkill());
        System.out.printf("\t - 정신력(%d)\n", player.getMentalStrength());
        System.out.printf("\t - 체력(%d)\n", player.getHealth());
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
                      .setCharAt(playerCoordinate.getX() + 1, GameMap.PLAYER_SYMBOL);
        blankGameMap.get(enemyBossCoordinate.getY() + 1)
                      .setCharAt(enemyBossCoordinate.getX() + 1, GameMap.MAIN_SUBJECT_SYMBOL);
        enemyCoordinates.forEach(coordinate -> {
            blankGameMap.get(coordinate.getY() + 1)
                          .setCharAt(coordinate.getX() + 1, GameMap.SUB_SUBJECT_SYMBOL);
        });
        return blankGameMap.stream()
                .map(StringBuilder::toString)
                .collect(Collectors.toList());
    }

    private List<StringBuilder> getBlankGameMap(GameInfoDto gameInfoDto) {
        List<StringBuilder> stringBuilders = new ArrayList<>();
        int mapSize = gameInfoDto.getMapSize();

        for (int y = 0; y < mapSize + 2; ++y) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < mapSize + 2; ++x) {
                if (y == 0 || y == mapSize + 1) {
                    stringBuilder.append('-');
                } else if (x == 0 || x == mapSize + 1) {
                    stringBuilder.append('|');
                } else {
                    stringBuilder.append('.');
                }
            }
            stringBuilders.add(stringBuilder);
        }
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
                health, currentHealth, experience, neededExperience);
    }

    public Printer inputPlayerNameMessage() {
        System.out.printf("플레이어 이름을 입력하세요. (%d ~ %d자)\n",
                SetNewGamePlayerRequest.MIN_PLAYER_NAME_LENGTH,
                SetNewGamePlayerRequest.MAX_PLAYER_NAME_LENGTH);
        return this;
    }

    private void inputNumberMessage() {
        System.out.println("번호를 입력하세요.");
    }
}
