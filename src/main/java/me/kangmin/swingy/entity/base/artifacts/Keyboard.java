package me.kangmin.swingy.entity.base.artifacts;

import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.enums.ArtifactType;

public class Keyboard extends Artifact {
    public static final int CODING_SKILL_DEGREE = 5;
    public static final int MENTAL_STRENGTH_DEGREE = 0;
    public static final int HEALTH_DEGREE = 0;
    public Keyboard(ArtifactRank rank) {
        super(ArtifactType.KEYBOARD,
                rank,
                calcDegree(rank, CODING_SKILL_DEGREE),
                calcDegree(rank, MENTAL_STRENGTH_DEGREE),
                calcDegree(rank, HEALTH_DEGREE)
        );
    }
}
