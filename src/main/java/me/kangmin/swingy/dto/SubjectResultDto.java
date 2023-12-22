package me.kangmin.swingy.dto;

import me.kangmin.swingy.enums.SubjectType;

public class SubjectResultDto {
    private final boolean isSuccess;
    private final SubjectType subjectType;
    private final int stageLevel;

    public SubjectResultDto(boolean isSuccess, SubjectType subjectType, int stageLevel) {
        this.isSuccess = isSuccess;
        this.subjectType = subjectType;
        this.stageLevel = stageLevel;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public int getStageLevel() {
        return stageLevel;
    }
}
