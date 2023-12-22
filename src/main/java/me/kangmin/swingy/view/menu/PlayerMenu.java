package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.dto.PlayerDto;
import me.kangmin.swingy.entity.base.Level;
import me.kangmin.swingy.entity.base.Stat;
import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.PlayerElement;

import java.util.List;

public class PlayerMenu implements Menu {
    private final MenuElement[] menuElements;

    public PlayerMenu(List<PlayerDto> players) {
        this.menuElements = new MenuElement[players.size() + 1];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            PlayerDto player = players.get(i);
            sb.append(String.format("%s\n", player.getType()));

            if (player.getName() != null) {
                sb.append(String.format("\t - 이름(%s)\n", player.getName()));
            }

            if (player.getNeededExperience() != 0) {
                sb.append(String.format("\t - 레벨(%d/%d)\n", player.getLevel(), Level.MAX_LEVEL));
                sb.append(String.format("\t - 경험치(%d/%d)\n", player.getExperience(), player.getNeededExperience()));
            }

            sb.append(String.format("\t - 코딩 실력(%d/%d)\n", player.getCodingSkill(), Stat.MAX_STAT_LIMIT));
            sb.append(String.format("\t - 정신력(%d/%d)\n", player.getMentalStrength(), Stat.MAX_STAT_LIMIT));
            sb.append(String.format("\t - 체력(%d/%d)\n", player.getHealth(), Stat.MAX_STAT_LIMIT));

            this.menuElements[i] = new PlayerElement(sb.toString());
            sb.setLength(0);
        }

        this.menuElements[players.size()] = new PlayerElement("뒤로가기\n");
    }


    @Override
    public MenuElement[] getElements() {
        return this.menuElements;
    }
}
