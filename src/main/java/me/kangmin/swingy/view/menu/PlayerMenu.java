package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.dto.PlayerDto;
import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.PlayerElement;

import java.util.List;

public class PlayerMenu implements Menu {
    private final MenuElement[] menuElements;

    public PlayerMenu(List<PlayerDto> players) {
        this.menuElements = new MenuElement[players.size() + 1];

        for (int i = 0; i < players.size(); i++) {
            PlayerDto player = players.get(i);
            String description = String.format("%s\n\t - 코딩 실력(%d)\n\t - 정신력(%d)\n\t - 체력(%d)\n\t - 레벨(%d)\n",
                    player.getType(),
                    player.getCodingSkill(),
                    player.getMentalStrength(),
                    player.getHealth(),
                    player.getLevel());
            this.menuElements[i] = new PlayerElement(description);
        }

        this.menuElements[players.size()] = new PlayerElement("뒤로가기\n");
    }


    @Override
    public MenuElement[] getElements() {
        return this.menuElements;
    }
}
