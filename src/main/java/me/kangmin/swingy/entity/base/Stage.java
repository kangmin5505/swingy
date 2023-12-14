package me.kangmin.swingy.entity.base;

import java.io.Serializable;
import java.util.Map;

public class Stage implements Serializable {
    private int stage;
    public final static int FINAL_STAGE = 7;
    private static final Map<Integer, String> STAGE_BOSS_MAP = Map.of(
            0, "Piscine",
            1, "Libft",
            2, "Born2beroot",
            3, "Fract-ol",
            4, "Minishell",
            5, "MiniRT",
            6, "Webserv",
            7, "Transcendence"
    );

    public void nextStage() {
        this.stage++;
    }
    // ========== constructor ==========
    public Stage() {
        this.stage = 0;
    }

    // ========== getter ==========
    public int getStage() {
        return this.stage;
    }

    public String getBossName() {
        return STAGE_BOSS_MAP.get(this.stage);
    }

    public int getTotalStage() {
        return STAGE_BOSS_MAP.size();
    }

    public int getTotalSubSubjectCnt() {
        return (this.stage - 1) * 5 + 10 - (this.stage % 2);

    }

    public boolean isFinalStage() {
        return this.stage == FINAL_STAGE;
    }
}
