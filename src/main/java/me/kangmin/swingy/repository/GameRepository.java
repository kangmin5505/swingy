package me.kangmin.swingy.repository;

import me.kangmin.swingy.enums.PlayerType;

import java.util.Arrays;
import java.util.List;

public interface GameRepository {
    List<PlayerType> allNewPlayer = Arrays.asList(PlayerType.values());

    default List<PlayerType> findAllNewPlayer() {
        return this.allNewPlayer;
    }
}
