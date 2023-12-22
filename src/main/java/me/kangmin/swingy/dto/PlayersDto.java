package me.kangmin.swingy.dto;

import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.enums.PlayerType;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersDto {
    private final List<PlayerDto> players;
    private final int size;

    public PlayersDto(List<PlayerDto> players, int size) {
        this.players = players;
        this.size = size;
    }

    public static PlayersDto ofGames(List<Game> games) {
        return new PlayersDto(
                games.stream()
                     .map(game ->
                             PlayerDto.builder()
                                      .name(game.getPlayer().getName())
                                      .experience(game.getPlayer().getExperience())
                                      .totalNeededExperience(game.getPlayer().getTotalNeededExperience())
                                      .type(game.getPlayer().getType())
                                      .codingSkill(game.getPlayer().getStat().getCodingSkill())
                                      .mentalStrength(game.getPlayer().getStat().getMentalStrength())
                                      .health(game.getPlayer().getStat().getHealth())
                                      .level(game.getPlayer().getLevel())
                                      .build()
                     )
                     .collect(Collectors.toList()),
                games.size()
        );
    }

    public static PlayersDto ofPlayerTypes(List<PlayerType> playerTypes) {
        return new PlayersDto(
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
                           .collect(Collectors.toList()),
                playerTypes.size()
        );
    }

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public int getSize() {
        return size;
    }
}
