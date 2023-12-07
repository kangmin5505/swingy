package me.kangmin.swingy.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.kangmin.swingy.enums.ResponseCode;

public class SetNewGamePlayerRequest {
    public static int MIN_PLAYER_NAME_LENGTH = 1;
    public static int MAX_PLAYER_NAME_LENGTH = 10;
    @NotNull
    @Size(min = 1, max = 10)
    private final String playerName;
    private final ResponseCode input;

    public SetNewGamePlayerRequest(String playerName, ResponseCode input) {
        this.playerName = playerName;
        this.input = input;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public ResponseCode getInput() {
        return input;
    }
}
