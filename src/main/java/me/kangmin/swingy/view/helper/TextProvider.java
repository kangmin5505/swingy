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
import me.kangmin.swingy.view.menu.*;
import me.kangmin.swingy.view.menu.element.MenuElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextProvider {
    private final static char PLAYER_SYMBOL = '@';
    private final static char MAIN_SUBJECT_SYMBOL = '$';
    private final static char SUB_SUBJECT_SYMBOL = '*';
    public final static int WAITING_TIME = 5000;
    private final static StringBuilder sb = new StringBuilder();

    public static String exit() {
        return "게임을 종료합니다.";
    }

    public static String mismatch() {
        return "잘못된 입력입니다.";
    }

    public static String input() {
        return "> ";
    }

    public static List<String> menu(Menu menu) {
        List<String> menuList = new ArrayList<>();

        MenuElement[] menuElements = menu.getElements();

        IntStream.range(1, menuElements.length + 1).forEach(i -> {
            menuList.add(String.format("%d. %s", i, menuElements[i - 1].getDescription()));
        });

        return menuList;
    }

    public static String intro() {
        sb.append(getBanner());
        sb.append(getIntro());

        return getResult();
    }
    public static List<String> playerList(PlayersDto playersDto) {
        List<PlayerDto> players = playersDto.getPlayers();

        PlayerMenu playerMenu = new PlayerMenu(players);
        return menu(playerMenu);
    }

    public static String gameMap(GameInfoDto gameInfoDto) {
        sb.append("[맵]\n");
        List<StringBuilder> blankGameMap = getBlankGameMap(gameInfoDto);
        List<String> gameMap = setEntityOnMap(blankGameMap, gameInfoDto);

        gameMap.forEach(s -> sb.append(s).append("\n"));

        return getResult();
    }

    private static List<String> setEntityOnMap(List<StringBuilder> blankGameMap, GameInfoDto gameInfoDto) {
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

    private static List<StringBuilder> getBlankGameMap(GameInfoDto gameInfoDto) {
        List<StringBuilder> stringBuilders = new ArrayList<>();
        int mapSize = gameInfoDto.getMapSize();
        int totalMapSize = mapSize + 2;

        for (int y = 0; y < totalMapSize; ++y) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < totalMapSize; ++x) {
                if (y == 0 || y == mapSize + 1) {
                    stringBuilder.append('#');
                } else if (x == 0 || (x == mapSize + 1)) {
                    stringBuilder.append('#');
                } else {
                    stringBuilder.append('_');
                }
            }
            stringBuilders.add(stringBuilder);
        }
        stringBuilders.get(1).delete(totalMapSize - 1, totalMapSize);

        return stringBuilders;
    }

    public static String stage(GameInfoDto gameInfoDto) {
        int stage = gameInfoDto.getStage();
        int totalStage = gameInfoDto.getTotalStage();
        String bossName = gameInfoDto.getBossName();

        sb.append("[스테이지]\n");
        sb.append(String.format("(%d / %d) %s\n", stage, totalStage - 1, bossName));

        return getResult();
    }

    public static String player(Player player) {
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

        sb.append("[플레이어]\n");
        sb.append(String.format("이름: %s\n", name));
        sb.append(String.format("타입(%s)\t레벨(%s/%s)\t코딩스킬(%s/%s)\t정신력(%s/%s)\t체력(%s/%s)\t경험치(%s/%s)\n",
                type, level, Level.MAX_LEVEL, codingSkill, Stat.MAX_STAT_LIMIT, mentalStrength, Stat.MAX_STAT_LIMIT,
                currentHealth, health, experience, neededExperience));
        sb.append("[아티팩트]\n");
        player.getArtifacts().values()
                .forEach(artifact -> {
                    String artifactName = artifact.getArtifactType().getName();
                    String rank = artifact.getRank();
                    int artifactCodingSkill = artifact.getCodingSkill();
                    int artifactMentalStrength = artifact.getMentalStrength();
                    int artifactHealth = artifact.getHealth();

                    sb.append(String.format("이름(%s)\t등급(%s)\t코딩스킬(%d)\t정신력(%d)\t체력(%d)\n",
                            artifactName, rank, artifactCodingSkill, artifactMentalStrength, artifactHealth));
                });

        return getResult();
    }

    private static String getResult() {
        String result = sb.toString();
        clearStringBuilder();

        return result;
    }

    public static String inputPlayerName() {
        sb.append(String.format("플레이어 이름을 입력하세요. (%d ~ %d자)\n",
                SetNewPlayerNameRequest.MIN_PLAYER_NAME_LENGTH,
                SetNewPlayerNameRequest.MAX_PLAYER_NAME_LENGTH));
        return getResult();
    }

    public static String inputNumber() {
        return "번호를 입력하세요.";
    }

    public static String artifact(Artifact artifact) {
        String artifactName = artifact.getArtifactType().getName();
        String rank = artifact.getRank();
        int codingSkill = artifact.getCodingSkill();
        int mentalStrength = artifact.getMentalStrength();
        int health = artifact.getHealth();

        sb.append("아티팩트를 획득하였습니다.\n");
        sb.append("[아티팩트]\n");
        sb.append(String.format("이름: %s\n", artifactName));
        sb.append(String.format("등급: %s\n", rank));
        sb.append(String.format("코딩스킬: %s\n", codingSkill));
        sb.append(String.format("정신력: %s\n", mentalStrength));
        sb.append(String.format("체력: %s\n", health));

        return getResult();
    }

    public static String ending() {
        sb.append(getEnding());

        return getResult();
    }

    public static String resetData() {
        sb.append("데이터를 초기화하시겠습니까?");

        return getResult();
    }
    private static String getIntro() {
        sb.append("'Knights of 42: The Code Crusade'는 다양한 분야에서 온 플레이어가 프로그래밍의 세계를 모험하며 진정한 개발자로 성장하는 여정을 그립니다.\n");
        sb.append("각자의 능력치를 발전시키고 도전적인 프로그래밍 과제를 해결함으로써, 진정한 개발자로 거듭나게 됩니다.\n");
        sb.append("지금 바로 이 모험에 참여하여 42의 진정한 개발자로 거듭나보세요!\n\n");

        return getResult();
    }

    private static String getEnding() {
        sb.append("축하합니다! 'Knights of 42: The Code Crusade'의 모든 프로그래밍 과제를 완료하셨습니다.\n");
        sb.append("여러분은 진정한 개발자로 성장하였고, 이제는 프로그래밍의 세계에서 탁월한 역할을 수행할 수 있습니다.\n");
        sb.append("여러분의 모험을 응원하며, 이제 여러분이 만든 코드를 더 큰 세상에서 빛낼 수 있기를 기대합니다!\n");
        sb.append("게임을 즐겨주셔서 감사합니다. 새로운 모험에서 만나요!\n");

        return getResult();
    }

    private static String getBanner() {
        sb.append("\n\n");
        sb.append("██╗  ██╗███╗   ██╗██╗ ██████╗ ██╗  ██╗████████╗     ██████╗ ███████╗    ██╗  ██╗██████╗\n");
        sb.append("██║ ██╔╝████╗  ██║██║██╔════╝ ██║  ██║╚══██╔══╝    ██╔═══██╗██╔════╝    ██║  ██║╚════██╗\n");
        sb.append("█████╔╝ ██╔██╗ ██║██║██║  ███╗███████║   ██║       ██║   ██║█████╗      ███████║ █████╔╝\n");
        sb.append("██╔═██╗ ██║╚██╗██║██║██║   ██║██╔══██║   ██║       ██║   ██║██╔══╝      ╚════██║██╔═══╝ \n");
        sb.append("██║  ██╗██║ ╚████║██║╚██████╔╝██║  ██║   ██║       ╚██████╔╝██║              ██║███████╗\n");
        sb.append("╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝        ╚═════╝ ╚═╝              ╚═╝╚══════╝\n");
        sb.append("    ████████╗██╗  ██╗███████╗     ██████╗ ██████╗ ██████╗ ███████╗     ██████╗██████╗ ██╗   ██╗███████╗ █████╗ ██████╗ ███████╗\n");
        sb.append("██╗ ╚══██╔══╝██║  ██║██╔════╝    ██╔════╝██╔═══██╗██╔══██╗██╔════╝    ██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗██╔══██╗██╔════╝\n");
        sb.append("╚═╝    ██║   ███████║█████╗      ██║     ██║   ██║██║  ██║█████╗      ██║     ██████╔╝██║   ██║███████╗███████║██║  ██║█████╗\n");
        sb.append("██╗    ██║   ██╔══██║██╔══╝      ██║     ██║   ██║██║  ██║██╔══╝      ██║     ██╔══██╗██║   ██║╚════██║██╔══██║██║  ██║██╔══╝\n");
        sb.append("╚═╝    ██║   ██║  ██║███████╗    ╚██████╗╚██████╔╝██████╔╝███████╗    ╚██████╗██║  ██║╚██████╔╝███████║██║  ██║██████╔╝███████╗\n");
        sb.append("       ╚═╝   ╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝     ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═════╝ ╚══════╝\n");

        return getResult();
    }

    private static void clearStringBuilder() {
        sb.delete(0, sb.length());
    }

    public static String saveGame() {
        sb.append("저장하시겠습니까? (최대 10개가 저장되고, 이후에는 저장되지 않습니다.)");

        return getResult();
    }

    public static String gameOver() {
        sb.append("학습에 실패했습니다.\n");
        sb.append("다시 도전하시기 바랍니다.");

        return getResult();
    }
}
