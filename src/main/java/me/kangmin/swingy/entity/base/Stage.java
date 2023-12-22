package me.kangmin.swingy.entity.base;

import java.io.Serializable;
import java.util.Map;

public class Stage implements Serializable {
    private static final long serialVersionUID = -6412260135855416326L;
    private int stageLevel;
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
        this.stageLevel++;
    }
    // ========== constructor ==========
    public Stage() {
        this.stageLevel = 0;
    }

    // ========== getter ==========
    public int getStageLevel() {
        return this.stageLevel;
    }

    public String getBossName() {
        return STAGE_BOSS_MAP.get(this.stageLevel);
    }

    public int getTotalStage() {
        return STAGE_BOSS_MAP.size();
    }

    public int getTotalSubSubjectCnt() {
        return this.stageLevel;
    }
}
