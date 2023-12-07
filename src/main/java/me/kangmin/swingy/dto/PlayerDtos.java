package me.kangmin.swingy.dto;

import me.kangmin.swingy.enums.PlayerType;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerDtos {
    private final List<PlayerDto> players;

    public PlayerDtos(List<PlayerDto> players) {
        this.players = players;
    }

    public static PlayerDtos of(List<PlayerType> playerTypes) {
        return new PlayerDtos(
                playerTypes.stream()
                           .map(playerType ->
                               PlayerDto.builder()
                                        .type(playerType.getType())
                                        .codingSkill(playerType.getCodingSkill())
                                        .mentalStrength(playerType.getMentalStrength())
                                        .health(playerType.getHealth())
                                        .level(0)
                                        .build()
                           )
                           .collect(Collectors.toList())
        );
    }

    // ========== getter ==========
    public List<PlayerDto> getPlayers() {
        return this.players;
    }
}
